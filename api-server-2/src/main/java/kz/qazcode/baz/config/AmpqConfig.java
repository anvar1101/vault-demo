package kz.qazcode.baz.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import kz.qazcode.baz.service.BazDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AmpqConfig {

  private final ObjectMapper objectMapper;
  private final BazDataService bazDataService;

  @Bean
  public Queue sampleQueue() {
    return new Queue("sample_queue", true);
  }

  @RabbitListener(queues = "sample_queue")
  public void receiveMessage(String message) {
    log.info("message received : {}", message);
    try {
      HashMap map = objectMapper.readValue(message, HashMap.class);
      bazDataService.createItem(String.valueOf(map.get("key")), String.valueOf(map.get("value")));
    } catch (JsonProcessingException e) {
      log.error("error", e);
    }
  }
}
