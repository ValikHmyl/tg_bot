package com.khmyl.telegram.currency.bot.message.text.impl.template;

import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.TextMessageConstants;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(TextMessageConstants.REPORT_CAPTION_KEY)
@Scope("prototype")
@NoArgsConstructor
public class ReportCaptionTemplate implements MessageTemplate {

   @Override
   public Map<String, Object> getArgs() {
      return Collections.emptyMap();
   }

   @Override
   public String getMessage() {
      return "Weekly report of currencies rates";
   }
}
