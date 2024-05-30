package com.cqueltech.playingaround.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  // Configure the Handicap Index Calculator bean.
  @Bean
  public HandicapIndexCalculator handicapIndexCalculator() {
    return new HandicapIndexCalculator();
  }

}
