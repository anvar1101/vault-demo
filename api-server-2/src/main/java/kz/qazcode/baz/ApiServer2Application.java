package kz.qazcode.baz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.vault.repository.configuration.EnableVaultRepositories;

@EnableScheduling
@EnableJpaRepositories
@SpringBootApplication
@EnableVaultRepositories
public class ApiServer2Application {

  public static void main(String[] args) {
    SpringApplication.run(ApiServer2Application.class, args);
  }

}
