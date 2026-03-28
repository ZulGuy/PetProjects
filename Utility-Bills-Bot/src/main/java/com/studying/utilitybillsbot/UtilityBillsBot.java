package com.studying.utilitybillsbot;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
public class UtilityBillsBot implements SpringLongPollingBot,
    LongPollingSingleThreadUpdateConsumer {

  private final String BOT_TOKEN;
  private final TelegramClient telegramClient;
  private final UsersService usersService;
  private final BillsService billsService;
  private final RatesService ratesService;

  Map<Long, User> previousUsers = new HashMap<>();
  Map<Long, Bill> previousBills = new HashMap<>();
  Map<Long, Rate> previousRates = new HashMap<>();

  private BillsStatus previousBillsStatus = BillsStatus.NONE;
  private RatesStatus previousRatesStatus = RatesStatus.NONE;
  private Status previousStatus = Status.NONE;

  @Autowired
  public UtilityBillsBot(@Value("${telegram.bot.token}") String botToken, UsersService usersService,
      BillsService billsService,
      RatesService ratesService) {
    this.BOT_TOKEN = botToken;
    this.telegramClient = new OkHttpTelegramClient(getBotToken());
    this.usersService = usersService;
    this.billsService = billsService;
    this.ratesService = ratesService;
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
      User currentUser;
      Bill currentBill;
      Rate currentRate;
      try {
        currentUser = usersService.findByChatId(chatId);
      } catch (Exception e) {
        currentUser = new User(chatId);
        usersService.save(currentUser);
      }
      try {
        currentBill = billsService.findByMonthAndYearAndChatId(LocalDate.now().getMonthValue(),
            LocalDate.now().getYear(), chatId);
      } catch (EntityNotFoundException e) {
        currentBill = new Bill(chatId);
        billsService.save(currentBill);
      }
      try {
        currentRate = ratesService.findByChatId(chatId);
      } catch (EntityNotFoundException e) {
        currentRate = new Rate(chatId);
        ratesService.save(currentRate);
      }
      previousUsers.putIfAbsent(chatId, currentUser);
      previousUsers.replace(chatId, currentUser);
      previousBills.putIfAbsent(chatId, currentBill);
      previousBills.replace(chatId, currentBill);
      previousRates.putIfAbsent(chatId, currentRate);
      previousRates.replace(chatId, currentRate);

      SendMessage message = SendMessage
          .builder()
          .chatId(chatId)
          .replyMarkup(setKeyboard())
          .text("Будь ласка, використовуйте кнопки з клавіатури бота!")
          .build();

      if (messageText.equals("/start")) {
        currentUser.setStatus(Status.WAITING_FIRST_DAY_OF_MONTH);
        currentUser.setBillsStatus(BillsStatus.WAITING_ELECTRICITY);
        currentUser.setRatesStatus(RatesStatus.NONE);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setKeyboard())
            .text("Будь ласка, натисніть 'Внести показники на початку місяця'")
            .build();

      } else if (messageText.equals("Розрахувати показники")) {
        if (ratesService.findByChatId(chatId) != null
            && ratesService.findByChatId(chatId).getGasRate() != 0) {
          billsService.calculateTotalCost(chatId);
          currentBill = billsService.findByMonthAndYearAndChatId(LocalDate.now().getMonthValue(), LocalDate.now().getYear(), chatId);

          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Рахунок за комунальні послуги за " + LocalDate.now() + ":" + "\n"
                  + "Електроенергія: " + currentBill.getElectricityCost() + "грн" + "\n"
                  + "Холодна вода: " + currentBill.getColdWaterCost() + "грн" + "\n"
                  + "Гаряча вода: " + currentBill.getHotWaterCost() + "грн" + "\n"
                  + "Газ: " + currentBill.getGasCost() + "грн" + "\n"
                  + "Загальна сума: " + currentBill.getTotalCost() + "грн")
              .build();
        }

      } else if (messageText.equals("Внести показники на початку місяця")
          && currentUser.getStatus() == Status.NONE) {

        currentUser.setStatus(Status.WAITING_FIRST_DAY_OF_MONTH);
        currentUser.setBillsStatus(BillsStatus.WAITING_ELECTRICITY);
        currentUser.setRatesStatus(RatesStatus.NONE);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, впишіть показник електроенергії на початку місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_FIRST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_ELECTRICITY) {
        try {
          currentBill.setElectricity(Integer.parseInt(messageText.trim()));
        } catch (Exception e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, впишіть показник електроенергії на початку місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.WAITING_COLD_WATER);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, впишіть показник холодної води на початку місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_FIRST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_COLD_WATER) {
        try {
          currentBill.setColdWater(Integer.parseInt(messageText.trim()));
        } catch (Exception e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, впишіть показник холодної води на початку місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.WAITING_HOT_WATER);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, впишіть показник гарячої води на початку місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_FIRST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_HOT_WATER) {
        try {
          currentBill.setHotWater(Integer.parseInt(messageText.trim()));
        } catch (Exception e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, впишіть показник гарячої води на початку місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.WAITING_GAS);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, впишіть показник газу на початку місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_FIRST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_GAS) {
        try {
          currentBill.setGas(Integer.parseInt(messageText.trim()));
        } catch (Exception e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, впишіть показник газу на початку місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.NONE);
        currentUser.setStatus(Status.COMPLETED_FIRST_DAY_OF_MONTH);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setKeyboard())
            .text("Будь ласка, натисніть 'Внести показники в кінці місяця'")
            .build();

      } else if ((!messageText.equals("Внести показники в кінці місяця"))
          && currentUser.getStatus() == Status.COMPLETED_FIRST_DAY_OF_MONTH) {
        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setKeyboard())
            .text("Будь ласка, натисніть 'Внести показники в кінці місяця'")
            .build();
        try {
          telegramClient.executeAsync(message);
        } catch (TelegramApiException er) {
          er.printStackTrace();
        }
        return;
      } else if (messageText.equals("Внести показники в кінці місяця")) {
        currentUser.setStatus(Status.WAITING_LAST_DAY_OF_MONTH);
        currentUser.setBillsStatus(BillsStatus.WAITING_ELECTRICITY);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, внесіть показники елетроенергії в кінці місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_LAST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_ELECTRICITY) {
        try {
          currentBill.setElectricity(
              Integer.parseInt(messageText.trim()) - currentBill.getElectricity());
        } catch (Exception e) {
          currentBill.setElectricity(previousBills.get(chatId).getElectricity());
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, внесіть показники елетроенергії в кінці місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.WAITING_COLD_WATER);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, внесіть показники холодної води в кінці місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_LAST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_COLD_WATER) {
        try {
          currentBill.setColdWater(
              Integer.parseInt(messageText.trim()) - currentBill.getColdWater());
        } catch (Exception e) {
          currentBill.setColdWater(previousBills.get(chatId).getColdWater());
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, внесіть показники холодної води в кінці місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.WAITING_HOT_WATER);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, внесіть показники гарячої води в кінці місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_LAST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_HOT_WATER) {
        try {
          currentBill.setHotWater(Integer.parseInt(messageText.trim()) - currentBill.getHotWater());
        } catch (Exception e) {
          currentBill.setHotWater(previousBills.get(chatId).getHotWater());
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, внесіть показники гарячої води в кінці місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.WAITING_GAS);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setUtilitiesKeyboard())
            .text("Будь ласка, внесіть показники газу в кінці місяця")
            .build();

      } else if (currentUser.getStatus() == Status.WAITING_LAST_DAY_OF_MONTH
          && currentUser.getBillsStatus() == BillsStatus.WAITING_GAS) {
        try {
          currentBill.setGas(Integer.parseInt(messageText.trim()) - currentBill.getGas());
        } catch (Exception e) {
          currentBill.setGas(previousBills.get(chatId).getGas());
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setUtilitiesKeyboard())
              .text("Будь ласка, внесіть показники газу в кінці місяця")
              .build();
          try {
            telegramClient.executeAsync(message);
          } catch (TelegramApiException er) {
            er.printStackTrace();
          }
          return;
        }
        currentUser.setBillsStatus(BillsStatus.NONE);
        currentUser.setStatus(Status.COMPLETED_LAST_DAY_OF_MONTH);
        billsService.save(currentBill);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setKeyboardForRate())
            .text(
                "Чи змінились тарифи на комуналку? (Оберіть на клавіатурі бота 'Так', 'Ні' або 'Ввести тарифи вперше')")
            .build();

      } else if (messageText.equals("Ні")) {
        if (ratesService.findByChatId(chatId) != null
            && ratesService.findByChatId(chatId).getGasRate() != 0) {
          try {
            currentRate.setGasRate(Double.parseDouble(messageText.trim()));
            currentUser.setRatesStatus(RatesStatus.COMPLETED);
            ratesService.save(currentRate);
            usersService.save(currentUser);
            billsService.calculateTotalCost(chatId);
            currentBill = billsService.findByMonthAndYearAndChatId(LocalDate.now().getMonthValue(),
                LocalDate.now().getYear(), chatId);
            ratesService.save(currentRate);
            usersService.save(currentUser);

            message = SendMessage
                .builder()
                .chatId(chatId)
                .replyMarkup(setKeyboard())
                .text("Рахунок за комунальні послуги за " + LocalDate.now() + ":" + "\n"
                    + "Електроенергія: " + currentBill.getElectricityCost() + "грн" + "\n"
                    + "Холодна вода: " + currentBill.getColdWaterCost() + "грн" + "\n"
                    + "Гаряча вода: " + currentBill.getHotWaterCost() + "грн" + "\n"
                    + "Газ: " + currentBill.getGasCost() + "грн" + "\n"
                    + "Загальна сума: " + currentBill.getTotalCost() + "грн")
                .build();

            currentUser.setStatus(Status.WAITING_FIRST_DAY_OF_MONTH);
            currentUser.setBillsStatus(BillsStatus.WAITING_ELECTRICITY);
            currentUser.setRatesStatus(RatesStatus.NONE);
            usersService.save(currentUser);

          } catch (NumberFormatException e) {
            message = SendMessage
                .builder()
                .chatId(chatId)
                .replyMarkup(setKeyboard())
                .text("Будь ласка, введіть число у форматі 1234.56")
                .build();
          }
          return;
        }
        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setKeyboardForRate())
            .text(
                "Тарифів не знайдено, прошу натиснути 'Ввести тарифи вперше'")
            .build();
        return;

      } else if (currentUser.getStatus() == Status.COMPLETED_LAST_DAY_OF_MONTH
          && (messageText.equals("Ввести тарифи вперше") || messageText.equals("Так"))) {
        currentUser.setRatesStatus(RatesStatus.WAITING_ELECTRICITY_RATE);
        usersService.save(currentUser);

        message = SendMessage
            .builder()
            .chatId(chatId)
            .replyMarkup(setKeyboard())
            .text("Будь ласка, введіть тариф на електроенергію")
            .build();

      } else if (currentUser.getStatus() == Status.COMPLETED_LAST_DAY_OF_MONTH
          && currentUser.getRatesStatus() == RatesStatus.WAITING_ELECTRICITY_RATE) {
//
        try {
          currentRate.setElectricityRate(Double.parseDouble(messageText.trim()));
          currentUser.setRatesStatus(RatesStatus.WAITING_COLD_WATER_RATE);
          ratesService.save(currentRate);
          usersService.save(currentUser);

          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть тариф на холодну воду")
              .build();

        } catch (NumberFormatException e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть число у форматі 1234.56")
              .build();
        }

      } else if (currentUser.getStatus() == Status.COMPLETED_LAST_DAY_OF_MONTH
          && currentUser.getRatesStatus() == RatesStatus.WAITING_COLD_WATER_RATE) {
        try {
          currentRate.setColdWaterRate(Double.parseDouble(messageText.trim()));
          currentUser.setRatesStatus(RatesStatus.WAITING_HOT_WATER_RATE);
          ratesService.save(currentRate);
          usersService.save(currentUser);

          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть тариф на гарячу воду")
              .build();

        } catch (NumberFormatException e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть число у форматі 1234.56")
              .build();
        }
      } else if (currentUser.getStatus() == Status.COMPLETED_LAST_DAY_OF_MONTH
          && currentUser.getRatesStatus() == RatesStatus.WAITING_HOT_WATER_RATE) {
        try {
          currentRate.setHotWaterRate(Double.parseDouble(messageText.trim()));
          currentUser.setRatesStatus(RatesStatus.WAITING_GAS_RATE);
          ratesService.save(currentRate);
          usersService.save(currentUser);

          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть тариф на газ")
              .build();

        } catch (NumberFormatException e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть число у форматі 1234.56")
              .build();
        }
      } else if (currentUser.getStatus() == Status.COMPLETED_LAST_DAY_OF_MONTH
          && currentUser.getRatesStatus() == RatesStatus.WAITING_GAS_RATE) {
        try {
          currentRate.setGasRate(Double.parseDouble(messageText.trim()));
          currentUser.setRatesStatus(RatesStatus.COMPLETED);
          ratesService.save(currentRate);
          usersService.save(currentUser);
          billsService.calculateTotalCost(chatId);
          currentBill = billsService.findByMonthAndYearAndChatId(LocalDate.now().getMonthValue(),
              LocalDate.now().getYear(), chatId);
          ratesService.save(currentRate);
          usersService.save(currentUser);

          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Рахунок за комунальні послуги за " + LocalDate.now() + ":" + "\n"
                  + "Електроенергія: " + currentBill.getElectricityCost() + "грн" + "\n"
                  + "Холодна вода: " + currentBill.getColdWaterCost() + "грн" + "\n"
                  + "Гаряча вода: " + currentBill.getHotWaterCost() + "грн" + "\n"
                  + "Газ: " + currentBill.getGasCost() + "грн" + "\n"
                  + "Загальна сума: " + currentBill.getTotalCost() + "грн")
              .build();

          currentUser.setStatus(Status.NONE);
          currentUser.setBillsStatus(BillsStatus.NONE);
          currentUser.setRatesStatus(RatesStatus.NONE);
          usersService.save(currentUser);

        } catch (NumberFormatException e) {
          message = SendMessage
              .builder()
              .chatId(chatId)
              .replyMarkup(setKeyboard())
              .text("Будь ласка, введіть число у форматі 1234.56")
              .build();
        }
      } else if (messageText.equals("Редагувати показники")) {
        previousStatus = currentUser.getStatus();
        previousBillsStatus = currentUser.getBillsStatus();
        previousRatesStatus = currentUser.getRatesStatus();
        currentUser.setBillsStatus(BillsStatus.EDITING);
        currentUser.setRatesStatus(RatesStatus.EDITING);
        currentUser.setStatus(Status.NONE);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, оберіть потрібний показник")
            .replyMarkup(setUtilitiesKeyboard())
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING
          && messageText.equals("Електроенергія")) {
        currentUser.setBillsStatus(BillsStatus.EDITING_ELECTRICITY_FIRST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники електроенергії на початку місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_ELECTRICITY_FIRST) {
        currentBill.setElectricity(Integer.parseInt(messageText));
        billsService.save(currentBill);
        currentUser.setBillsStatus(BillsStatus.EDITING_ELECTRICITY_LAST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники електроенергії в кінці місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_ELECTRICITY_LAST) {
        currentBill.setElectricity(Integer.parseInt(messageText) - currentBill.getElectricity());
        currentBill.setElectricityCost(currentBill.getElectricity() * currentRate.getElectricityRate());
        billsService.save(currentBill);
        currentUser.setBillsStatus(previousBillsStatus);
        currentUser.setStatus(previousStatus);
        currentUser.setRatesStatus(previousRatesStatus);
        usersService.save(currentUser);
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING
          && messageText.equals("Холодна вода")) {
        currentUser.setBillsStatus(BillsStatus.EDITING_COLD_WATER_FIRST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники холодної води на початку місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_COLD_WATER_FIRST) {
        currentBill.setColdWater(Integer.parseInt(messageText));
        billsService.save(currentBill);
        currentUser.setBillsStatus(BillsStatus.EDITING_COLD_WATER_LAST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники холодної води в кінці місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_COLD_WATER_LAST) {
        currentBill.setColdWater(Integer.parseInt(messageText) - currentBill.getColdWater());
        currentBill.setColdWaterCost(currentBill.getColdWater() * currentRate.getColdWaterRate());
        billsService.save(currentBill);
        currentUser.setBillsStatus(previousBillsStatus);
        currentUser.setStatus(previousStatus);
        currentUser.setRatesStatus(previousRatesStatus);
        usersService.save(currentUser);
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING
          && messageText.equals("Гаряча вода")) {
        currentUser.setBillsStatus(BillsStatus.EDITING_HOT_WATER_FIRST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники гарячої води на початку місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_HOT_WATER_FIRST) {
        currentBill.setHotWater(Integer.parseInt(messageText));
        billsService.save(currentBill);
        currentUser.setBillsStatus(BillsStatus.EDITING_HOT_WATER_LAST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники гарячої води в кінці місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_HOT_WATER_LAST) {
        currentBill.setHotWater(Integer.parseInt(messageText) - currentBill.getHotWater());
        currentBill.setHotWaterCost(currentBill.getHotWater() * currentRate.getHotWaterRate());
        billsService.save(currentBill);
        currentUser.setBillsStatus(previousBillsStatus);
        currentUser.setStatus(previousStatus);
        currentUser.setRatesStatus(previousRatesStatus);
        usersService.save(currentUser);
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING
          && messageText.equals("Газ")) {
        currentUser.setBillsStatus(BillsStatus.EDITING_GAS_FIRST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники газу на початку місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_GAS_FIRST) {
        currentBill.setGas(Integer.parseInt(messageText));
        billsService.save(currentBill);
        currentUser.setBillsStatus(BillsStatus.EDITING_GAS_LAST);
        usersService.save(currentUser);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть показники газу в кінці місяця")
            .build();
      } else if (currentUser.getBillsStatus() == BillsStatus.EDITING_GAS_LAST) {
        currentBill.setGas(Integer.parseInt(messageText) - currentBill.getGas());
        currentBill.setGasCost(currentBill.getGas() * currentRate.getGasRate());
        billsService.save(currentBill);
        currentUser.setBillsStatus(previousBillsStatus);
        currentUser.setStatus(previousStatus);
        currentUser.setRatesStatus(previousRatesStatus);
        usersService.save(currentUser);
      } else if (currentUser.getRatesStatus() == RatesStatus.EDITING
          && messageText.equals("Тарифи")) {
        currentUser.setRatesStatus(RatesStatus.EDITING_ELECTRICITY_RATE);
        usersService.save(currentUser);
        currentBill.setElectricityCost(currentBill.getElectricity() * currentRate.getElectricityRate());
        billsService.save(currentBill);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть тариф на електроенергію")
            .build();
      } else if (currentUser.getRatesStatus() == RatesStatus.EDITING_ELECTRICITY_RATE) {
        currentRate.setElectricityRate(Double.parseDouble(messageText));
        ratesService.save(currentRate);
        currentUser.setRatesStatus(RatesStatus.EDITING_COLD_WATER_RATE);
        usersService.save(currentUser);
        currentBill.setColdWaterCost(currentBill.getColdWater() * currentRate.getColdWaterRate());
        billsService.save(currentBill);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть тариф на холодну воду")
            .build();
      } else if (currentUser.getRatesStatus() == RatesStatus.EDITING_COLD_WATER_RATE) {
        currentRate.setColdWaterRate(Double.parseDouble(messageText));
        ratesService.save(currentRate);
        currentUser.setRatesStatus(RatesStatus.EDITING_HOT_WATER_RATE);
        usersService.save(currentUser);
        currentBill.setHotWaterCost(currentBill.getHotWater() * currentRate.getHotWaterRate());
        billsService.save(currentBill);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть тариф на гарячу воду")
            .build();
      } else if (currentUser.getRatesStatus() == RatesStatus.EDITING_HOT_WATER_RATE) {
        currentRate.setHotWaterRate(Double.parseDouble(messageText));
        ratesService.save(currentRate);
        currentUser.setRatesStatus(RatesStatus.EDITING_GAS_RATE);
        usersService.save(currentUser);
        currentBill.setGasCost(currentBill.getGas() * currentRate.getGasRate());
        billsService.save(currentBill);
        message = SendMessage
            .builder()
            .chatId(chatId)
            .text("Будь ласка, введіть тариф на газ")
            .build();
      } else if (currentUser.getRatesStatus() == RatesStatus.EDITING_GAS_RATE) {
        currentRate.setGasRate(Double.parseDouble(messageText));
        ratesService.save(currentRate);
        currentUser.setBillsStatus(previousBillsStatus);
        currentUser.setStatus(previousStatus);
        currentUser.setRatesStatus(previousRatesStatus);
        usersService.save(currentUser);
      }
      try {
        telegramClient.executeAsync(message);
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }

    }

  }

  @Scheduled(cron = "0 0 10 1 * *")
  public void notifyOnFirstDayOfMonth() {
    SendMessage message = SendMessage.builder().text("Внесіть показники на початку місяця").build();
    try {
      telegramClient.executeAsync(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Scheduled(cron = "0 0 10 L * *")
  public void notifyOnLastDayOfMonth() {
    SendMessage message = SendMessage.builder().text("Внесіть показники в кінці місяця").build();
    try {
      telegramClient.executeAsync(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private static ReplyKeyboardMarkup setKeyboard() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("Внести показники на початку місяця");
    row.add("Внести показники в кінці місяця");
    row.add("Розрахувати показники");
    keyboardRows.add(row);
    row = new KeyboardRow();
    row.add("Редагувати показники");
    row.add("Статистика");
    row.add("Розрахунок");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(true);
    return keyboardMarkup;
  }

  private static ReplyKeyboardMarkup setKeyboardForRate() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("Так");
    row.add("Ні");
    row.add("Ввести тарифи вперше");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  private static ReplyKeyboardMarkup setUtilitiesKeyboard() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("Електроенергія");
    row.add("Холодна вода");
    row.add("Гаряча вода");
    row.add("Газ");
    row.add("Тарифи");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  private static ReplyKeyboardMarkup setRatesKeyboard() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("Електроенергія");
    row.add("Холодна вода");
    row.add("Гаряча вода");
    row.add("Газ");
    row.add("Тарифи");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  private static ReplyKeyboardMarkup setUtilityKeyboard() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    row.add("Електроенрегія");
    row.add("Холодна вода");
    keyboardRows.add(row);
    row = new KeyboardRow();
    row.add("Гаряча вода");
    row.add("Газ");
    keyboardRows.add(row);
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  @AfterBotRegistration
  public void afterRegistration(BotSession botSession) {
    System.out.println("Registered bot running state is: " + botSession.isRunning());

    List<BotCommand> commands = List.of(
        new BotCommand("/start", "\uD83D\uDE80 Почати"));

    SetMyCommands setMyCommands = new SetMyCommands(commands);

    try {
      telegramClient.execute(setMyCommands);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }

  }
//
//  private static void setStatus(Class<?> classIdentifier, String messageText, IStatus status ) {
//    currentUser.setBillsStatus(BillsStatus.WAITING_COLD_WATER);
//    currentBill.setEletricity(Integer.getInteger(messageText.trim()));
//    billsService.save(currentBill);
//  }
}
