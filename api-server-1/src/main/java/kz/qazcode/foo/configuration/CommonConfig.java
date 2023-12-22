package kz.qazcode.foo.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import kz.qazcode.foo.domain.FooData;
import kz.qazcode.foo.service.FooDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CommonConfig {

  @Value("${spring.cloud.vault.app-role.role}")
  private String appRole;

  @Value("${greeting.title}")
  private String title;

  @Value("${greeting.description}")
  private String description;

  private final FooDataService fooDataService;
  private final RabbitTemplate rabbitTemplate;

  private final ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    log.info("#### appRole #### {}", appRole);
    log.info("#### {} #### {}", title, description);
  }

  @Scheduled(fixedRate = 2000L)
  public void executeSendMessage() {
    FooData item = new FooData();
    item.setId(UUID.randomUUID());
    item.setKey(title);
    item.setValue(description);

    try {
      rabbitTemplate
          .convertAndSend("sample_queue_exchange", "*", objectMapper.writeValueAsString(item));
    } catch (JsonProcessingException e) {
      log.error("error ", e);
    }
  }

  @Scheduled(fixedRate = 2000L)
  public void executeWriteData() {
    try {
      fooDataService.createItem();
      log.info("number of found items is {}", fooDataService.countItems());
    } catch (Exception ex) {
      log.error("executeWriteData", ex);
    }
  }


}
