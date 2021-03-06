package com.khmyl.telegram.currency.bot.command.impl.response.decorator;

import com.khmyl.telegram.currency.bot.model.dto.Currency;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import static com.khmyl.telegram.currency.bot.command.Command.REPORT_COMMAND;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageWithDefaultKeyboard extends SendMessage {

   @Builder(builderMethodName = "toBuilder")
   public MessageWithDefaultKeyboard(String chatId, String text) {
      super(chatId, text);
   }

   @Override
   public ReplyKeyboard getReplyMarkup() {
      KeyboardRow row = new KeyboardRow();
      Currency.getCurrenciesForRate().forEach(currency -> row.add(KeyboardButton.builder().text(currency.getCode()).build()));
      row.add(KeyboardButton.builder().text(REPORT_COMMAND).build());
      return ReplyKeyboardMarkup.builder().keyboardRow(row).resizeKeyboard(true).build();
   }

   @Override
   public String getParseMode() {
      return ParseMode.HTML;
   }
}
