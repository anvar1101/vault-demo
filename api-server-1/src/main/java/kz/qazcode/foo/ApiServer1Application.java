package kz.qazcode.foo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.vault.repository.configuration.EnableVaultRepositories;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class ApiServer1Application {

  public static void main(String[] args) {
    SpringApplication.run(ApiServer1Application.class, args);
  }

}
