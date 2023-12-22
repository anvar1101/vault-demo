package kz.qazcode.foo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AmpqConfig {

  @Bean
  public Exchange exchange() {
    return new TopicExchange("sample_queue_exchange");
  }

  @Bean
  public Binding binding(Exchange exchange, Queue sampleQueue) {
    return BindingBuilder.bind(sampleQueue).to(exchange).with("*").noargs();
  }

  @Bean
  public Queue sampleQueue() {
    return new Queue("sample_queue", true);
  }
}
