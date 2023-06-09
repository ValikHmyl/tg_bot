package com.khmyl.telegram.currency.bot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.Delegate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber {

   @Id
   private Long id;
   private Long chatId;
   private String name;

   @OneToMany(fetch = FetchType.LAZY)
   @Fetch(FetchMode.JOIN)
   @JoinColumn(name = "student_id")
   private List<SettingsValue> settings;

}
