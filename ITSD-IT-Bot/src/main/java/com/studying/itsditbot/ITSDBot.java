package com.studying.itsditbot;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ITSDBot implements LongPollingSingleThreadUpdateConsumer {


  @Override
  public void consume(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      System.out.println(update.getMessage().getText());
    }
  }
}
