package com.studying.itsditbot;

import com.studying.itsditbot.dto.Issue;
import com.studying.itsditbot.dto.JiraResponse;
import com.studying.itsditbot.enums.AuthStatus;
import com.studying.itsditbot.enums.ResolutionStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class ITSDBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

  private final TelegramClient telegramClient;
  private Map<Long, JiraApi> apiHashMap = new ConcurrentHashMap<>();
  private Map<Long, AuthStatus> authStatusHashMap = new ConcurrentHashMap<>();
  private Map<Long, String> userLoginsHashMap = new ConcurrentHashMap<>();
  private Map<Long, ResolutionStatus> resolutionStatusHashMap = new ConcurrentHashMap<>();
  private final String BOT_TOKEN;
  private Map<Long, String> currentIssueHashMap = new ConcurrentHashMap<>();

  private static final Map<String, String> IT_SERVICE = Map.ofEntries(
      Map.entry("1C HR", "22716"),
      Map.entry("1C IFRS", "22717"),
      Map.entry("1С Агро", "22718"),
      Map.entry("1С ДВА", "18905"),
      Map.entry("1С ККУ", "22719"),
      Map.entry("1С РТК", "22720"),
      Map.entry("1С УК", "22721"),
      Map.entry("AI. Підтримка", "23808"),
      Map.entry("APS Tender", "22722"),
      Map.entry("ARCGIS", "22723"),
      Map.entry("BAS Документообіг", "22724"),
      Map.entry("DAB", "22725"),
      Map.entry("EDocs", "18906"),
      Map.entry("HR сервіси", "23810"),
      Map.entry("LandInvest", "22726"),
      Map.entry("M.E.Doc", "22727"),
      Map.entry("MS Office 365", "21700"),
      Map.entry("Navision", "22728"),
      Map.entry("RDS сервіси", "22729"),
      Map.entry("WEB-сайти", "22730"),
      Map.entry("WIALON", "22731"),
      Map.entry("АСУБЗ", "22732"),
      Map.entry("Друкуюча техніка", "21701"),
      Map.entry("Звітність Пенсійного Фонду", "22733"),
      Map.entry("Інформаційна безпека", "22734"),
      Map.entry("Клієнт-Банк", "22735"),
      Map.entry("КНО", "22736"),
      Map.entry("Корпоративна звітність", "22737"),
      Map.entry("Корпоративна мережа", "18811"),
      Map.entry("Корпоративна пошта", "22738"),
      Map.entry("Корпоративна телефонія", "22739"),
      Map.entry("Логістичні сервіси", "22740"),
      Map.entry("Підтримка ІТ Інфраструктури", "18908"),
      Map.entry("Підтримка користувачів", "18907"),
      Map.entry("Робоче місце", "22741"),
      Map.entry("Сервіси ДВА", "22742"),
      Map.entry("Система подачі бух. звітності та ЕСВ", "22743"),
      Map.entry("Інше", "23700")
  );

  public ITSDBot(@Value("${telegram.bot.token}") String botToken) {
    BOT_TOKEN = botToken;
    telegramClient = new OkHttpTelegramClient(getBotToken());
  }

  @Override
  public String getBotToken() {
    return BOT_TOKEN;
  }

  @Override
  public LongPollingUpdateConsumer getUpdatesConsumer() {
    return this;
  }

  @Override
  public void consume(Update update) {

    if (update.hasMessage() && update.getMessage().hasText()) {

      String messageText = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();
      SendMessage message = SendMessage
          .builder()
          .chatId(chatId)
          .text("Будь ласка, користуйтесь кнопками на клавіатурі бота та\\або командами з меню!")
          .replyMarkup(setKeyboard())
          .build();

      if (messageText.equals("/start")) {

        if (authStatusHashMap.containsKey(chatId)) {
          authStatusHashMap.replace(chatId, AuthStatus.NON_AUTHORIZED);
        }

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, увійдіть до Jira.\nДля цього використайте команду '/login' з меню")
            .build();

      } else if (messageText.equals("/login")
          && (authStatusHashMap == null
          || !authStatusHashMap.containsKey(chatId)
          || authStatusHashMap.get(chatId) == AuthStatus.NON_AUTHORIZED)) {

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть ім'я користувача")
            .build();

        authStatusHashMap.put(chatId, AuthStatus.WAITING_LOGIN);

      } else if (authStatusHashMap.get(chatId) == AuthStatus.WAITING_LOGIN) {

        userLoginsHashMap.put(chatId, messageText.trim());

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть пароль")
            .build();

        authStatusHashMap.replace(chatId, AuthStatus.WAITING_PASSWORD);

      } else if (authStatusHashMap.get(chatId) == AuthStatus.WAITING_PASSWORD) {

        apiHashMap.put(chatId, new JiraApi(userLoginsHashMap.get(chatId), messageText.trim()));

        if (apiHashMap.get(chatId).isAuth()) {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("✅ Успішний вхід!")
              .replyMarkup(setKeyboard())
              .build();

          authStatusHashMap.replace(chatId, AuthStatus.AUTHORIZED);

        } else {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("❌ Невірні дані")
              .build();
          authStatusHashMap.replace(chatId, AuthStatus.NON_AUTHORIZED);

        }

      } else if (authStatusHashMap.get(chatId) == AuthStatus.AUTHORIZED
          && messageText.equals("📋 Мої запити")) {

        JiraResponse jiraResponse = apiHashMap.get(chatId).getIssues();
        Issue[] issues = jiraResponse.issues();
        if (issues.length == 0) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Ваша черга запитів порожня \uD83D\uDE04")
              .replyMarkup(setKeyboard())
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException e) {
            e.printStackTrace();
          }
          return;
        }
        StringBuffer sb = new StringBuffer();

        for (Issue issue : issues) {
          sb.append(issue.toString());
        }

        String issuesText = sb.toString();

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(issuesText)
            .replyMarkup(setKeyboard(issues))
            .build();

      } else if (authStatusHashMap.get(chatId) == AuthStatus.AUTHORIZED
          && messageText.matches("^ITSD-\\d+$")) {

        String issueKey = messageText;
        JiraResponse response = apiHashMap.get(chatId).getIssues();
        List<Issue> issues = List.of(response.issues());
        List<String> keys = new ArrayList<>();
        issues.forEach(issue -> keys.add(issue.key()));
        if (!keys.contains(issueKey)) {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Такого запиту не існує. Будь ласка, оберіть інший зі списку")
              .replyMarkup(setKeyboard())
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException e) {
            e.printStackTrace();
          }
          return;

        }

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Обрано запит " + issueKey)
            .replyMarkup(setKeyboard(issueKey))
            .build();

        currentIssueHashMap.putIfAbsent(chatId, issueKey);
        currentIssueHashMap.replace(chatId, issueKey);

      } else if (authStatusHashMap.get(chatId) == AuthStatus.AUTHORIZED
          && messageText.equals("✅ Вирішити запит")) {

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, напишіть коментар до запиту")
            .build();

        resolutionStatusHashMap.put(chatId, ResolutionStatus.WAITING_COMMENT);

      } else if (authStatusHashMap.get(chatId) == AuthStatus.AUTHORIZED
          && resolutionStatusHashMap.get(chatId) == ResolutionStatus.WAITING_COMMENT) {

        if (messageText.isBlank() || messageText.isEmpty() || messageText.equals("")
            || messageText.length() < 10) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Будь ласка, напишіть коментар що містить хоча б 10 символів")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException e) {
            e.printStackTrace();
          }
          return;
        }
        Issue[] currentIssues = apiHashMap.get(chatId).getIssues().issues();
        Issue currentIssue = null;
        for (Issue issue : currentIssues) {
          if (issue.key().equals(currentIssueHashMap.get(chatId))) {
            currentIssue = issue;
            break;
          }
        }
        String response = apiHashMap.get(chatId).resolveIssue(currentIssue, messageText);
        if (response == null) {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Запит " + currentIssueHashMap.get(chatId) + " вирішено")
              .replyMarkup(setKeyboard())
              .build();

        } else if (response.equals("Поле \"ІТ сервіс\" обов'язкове для заповенння!")) {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Виникла помилка при вирішенні запиту " + currentIssueHashMap.get(chatId) + "\n"
              + response)
              .replyMarkup(setKeyboard())
              .build();

        } else {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Виникла помилка при вирішенні запиту " + currentIssueHashMap.get(chatId))
              .replyMarkup(setKeyboard())
              .build();
        }

        resolutionStatusHashMap.replace(chatId, ResolutionStatus.NONE);

      } else if(currentIssueHashMap.containsKey(chatId) && messageText.equals("Заповнити ІТ сервіс")) {
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, оберіть ІТ сервіс з запропонованих")
            .replyMarkup(itServiceKeyboard())
            .build();

      } else if(IT_SERVICE.containsKey(messageText) && currentIssueHashMap.containsKey(chatId)) {
        boolean isChanged = apiHashMap.get(chatId).setItService(IT_SERVICE.get(messageText), currentIssueHashMap.get(chatId));

        if(isChanged) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("ІТ сервіс встановлено успішно")
              .replyMarkup(setKeyboard())
              .build();
        } else {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Введено некоректний ІТ сервіс. Повторіть спробу.")
              .replyMarkup(setKeyboard())
              .build();
        }

      } else if (authStatusHashMap.get(chatId) == AuthStatus.AUTHORIZED
          && messageText.equals("\uD83D\uDE04 Отримати оновлення")) {
        String issuesText = sendUpdates(apiHashMap.get(chatId), chatId, message);
        if (issuesText.equals("") || issuesText.isBlank() || issuesText.isEmpty()) {
          issuesText = "Відсутні оновлення по запитам";
        }

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text(issuesText)
            .replyMarkup(setKeyboard())
            .build();
      }

      try {
        telegramClient.executeAsync(message);
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }

    }
  }

  @AfterBotRegistration
  public void afterRegistration(BotSession botSession) {
    System.out.println("Registered bot running state is: " + botSession.isRunning());

    List<BotCommand> commands = List.of(
        new BotCommand("/start", "\uD83D\uDE80 Почати"),
        new BotCommand("/login", "\uD83C\uDFAB Увійти до Jira"));

    SetMyCommands setMyCommands = new SetMyCommands(commands);

    try {
      telegramClient.execute(setMyCommands);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }

  }

  public static ReplyKeyboardMarkup setKeyboard() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("📋 Мої запити");
    row.add("\uD83D\uDE04 Отримати оновлення");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  public static ReplyKeyboardMarkup setKeyboard(Issue[] issues) {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("📋 Мої запити");
    row.add("\uD83D\uDE04 Отримати оновлення");
    keyboardRows.add(row);
    row = new KeyboardRow();

    for (Issue issue : issues) {

      if (row.size() < 3) {
        row.add(issue.key());
      } else {
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(issue.key());
      }

    }

    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  public static ReplyKeyboardMarkup setKeyboard(String issueKey) {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("📋 Мої запити");
    row.add("\uD83D\uDE04 Отримати оновлення");
    keyboardRows.add(row);
    row = new KeyboardRow();
    row.add("Заповнити ІТ сервіс");
    row.add("✅ Вирішити запит");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  public String sendUpdates(JiraApi api, long chatId, SendMessage message) {

    Issue[] issues = api.getIssuesScheduled();
    StringBuffer sb = new StringBuffer();

    for (Issue issue : issues) {
      sb.append(issue.toString());
    }

    return sb.toString();
  }

  public static ReplyKeyboardMarkup itServiceKeyboard() {

    List<KeyboardRow> rows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();

    for (String service : IT_SERVICE.keySet()) {
      if (row.size() == 2) { // по 2 кнопки в ряд
        rows.add(row);
        row = new KeyboardRow();
      }
      row.add(service);
    }

    if (!row.isEmpty()) {
      rows.add(row);
    }

    ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(rows);
    keyboard.setResizeKeyboard(true);

    return keyboard;
  }
}
