package com.studying.itsditbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class ITSDBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

  private final TelegramClient telegramClient;
  //Замінити звичайний JiraApi на ConcurrentHashMap
  private JiraApi api;
  private Map<Long, AuthStatus> authStatusHashMap = new ConcurrentHashMap<>();
  private Map<Long, String> userLoginsHashMap = new ConcurrentHashMap<>();
  private final String BOT_TOKEN;

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
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      // Set variables
      String message_text = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();

      if (message_text.equals("/start")) {
        SendMessage message = SendMessage // Create a message object
            .builder()
            .chatId(chatId)
            .text("Будь ласка, увійдіть до Jira.\nДля цього використайте команду '/login' з меню")
            .build();
        try {
          telegramClient.execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }
      }
      if (update.getMessage().getText().equals("/login") && (
          authStatusHashMap == null || !authStatusHashMap.containsKey(
              update.getMessage().getChatId())
              || authStatusHashMap.get(update.getMessage().getChatId())
              == AuthStatus.NON_AUTHORIZED)) {

        chatId = update.getMessage().getChatId();

        SendMessage message = SendMessage // Create a message object
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть ім'я користувача")
            .build();
        authStatusHashMap.put(chatId, AuthStatus.WAITING_LOGIN);
        try {
          telegramClient.execute(message);
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }

      } else if (authStatusHashMap.get(update.getMessage().getChatId())
          == AuthStatus.WAITING_LOGIN) {

        chatId = update.getMessage().getChatId();
        userLoginsHashMap.put(chatId, update.getMessage().getText().trim());
        SendMessage message = SendMessage // Create a message object
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть пароль")
            .build();
        authStatusHashMap.replace(chatId, AuthStatus.WAITING_PASSWORD);
        try {
          telegramClient.execute(message);
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }
      } else if (authStatusHashMap.get(update.getMessage().getChatId())
          == AuthStatus.WAITING_PASSWORD) {

        chatId = update.getMessage().getChatId();
        api = new JiraApi(userLoginsHashMap.get(chatId), update.getMessage().getText().trim());
        SendMessage message;
        if (api.isAuth()) {
          message = SendMessage // Create a message object
              .builder()
              .chatId(chatId)
              .text("✅ Успішний вхід!")
              .replyMarkup(setKeyboard())
              .build();
          authStatusHashMap.replace(chatId, AuthStatus.AUTHORIZED);
        } else {
          message = SendMessage // Create a message object
              .builder()
              .chatId(chatId)
              .text("❌ Невірні дані")
              .build();
          authStatusHashMap.replace(chatId, AuthStatus.NON_AUTHORIZED);
        }
        try {
          telegramClient.execute(message);
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }
      } else if (authStatusHashMap.get(update.getMessage().getChatId())
          == AuthStatus.AUTHORIZED && update.getMessage().getText().equals("📋 Мої запити")) {
        chatId = update.getMessage().getChatId();
        JiraResponse jiraResponse = api.getIssues();
        Issue[] issues = jiraResponse.issues();
        StringBuffer sb = new StringBuffer();
        for (Issue issue : issues) {
          sb.append(issue.toString());
        }
        String messageText = sb.toString();
        SendMessage message = SendMessage // Create a message object
            .builder()
            .chatId(chatId)
            .text(messageText)
            .replyMarkup(setKeyboard())
            .build();
        try {
          telegramClient.execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }
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
}
