package com.khmyl.telegram.currency.bot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SettingValueId implements Serializable {

   @Column(name = "subscriber_id")
   private Long subscriberId;
   @Column(name = "student_id")
   private Long settingId;

}
