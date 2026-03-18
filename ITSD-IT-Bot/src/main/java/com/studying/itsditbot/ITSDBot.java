package com.studying.itsditbot;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
  private  JiraApi api;

  public ITSDBot() {
    telegramClient = new OkHttpTelegramClient(getBotToken());
  }

  @Override
  public String getBotToken() {
    return "-";
  }

  @Override
  public LongPollingUpdateConsumer getUpdatesConsumer() {
    return this;
  }

  @Override
  public void consume(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
      // Set variables
      String message_text = update.getMessage().getText();
      long chat_id = update.getMessage().getChatId();

      SendMessage message = SendMessage // Create a message object
          .builder()
          .chatId(chat_id)
          .text(message_text)
          .replyMarkup(setKeyboard())
          .build();
      try {
        telegramClient.execute(message); // Sending our message object to user
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    } else if(update.getMessage().getText().equals("/login")) {

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
