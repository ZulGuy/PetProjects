package com.studying.itsditbot;

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
  private String currentIssue;

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
      SendMessage message = null;

      if (messageText.equals("/start")) {

        if (authStatusHashMap.containsKey(chatId))
          authStatusHashMap.replace(chatId, AuthStatus.NON_AUTHORIZED);

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

        }

        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Обрано запит " + issueKey)
            .replyMarkup(setKeyboard(issueKey))
            .build();

        currentIssue = issueKey;

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

        boolean isResolved = apiHashMap.get(chatId).resolveIssue(currentIssue, messageText);
        if (isResolved) {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Запит " + currentIssue + " вирішено")
              .replyMarkup(setKeyboard())
              .build();

        } else {

          message = SendMessage
              .builder()
              .chatId(chatId)
              .text("Виникла помилка при вирішенні запиту " + currentIssue)
              .replyMarkup(setKeyboard())
              .build();

        }

        resolutionStatusHashMap.replace(chatId, ResolutionStatus.NONE);

      }

      try {
        telegramClient.execute(message);
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
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  public static ReplyKeyboardMarkup setKeyboard(Issue[] issues) {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();

    for (Issue issue : issues) {

      if (row.size() < 3)
        row.add(issue.key());
      else {
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
    row.add("✅ Вирішити запит");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }
}
