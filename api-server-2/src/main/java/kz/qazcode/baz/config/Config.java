package kz.qazcode.baz.config;

import jakarta.annotation.PostConstruct;
import kz.qazcode.baz.service.BazDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Config {

  private final BazDataService bazDataService;

  @Value("${spring.cloud.vault.app-role.role}")
  private String appRole;

  @Value("${greeting.text}")
  private String text;

  @Value("${greeting.description}")
  private String description;


  @PostConstruct
  public void init() {
    log.info("#### appRole #### {}", appRole);
    log.info("#### {} #### {}", text, description);
  }

  @Scheduled(fixedRate = 2000L)
  public void executeWriteData() {
    try {
      log.info("number of found items is {}", bazDataService.countItems());
    } catch (Exception ex) {
      log.error("executeWriteData", ex);
    }
  }
}
