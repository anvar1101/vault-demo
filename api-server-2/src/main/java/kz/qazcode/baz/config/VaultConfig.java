package kz.qazcode.baz.config;

import static org.springframework.vault.core.lease.domain.RequestedSecret.Mode.RENEW;
import static org.springframework.vault.core.lease.domain.RequestedSecret.Mode.ROTATE;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.core.lease.SecretLeaseContainer;
import org.springframework.vault.core.lease.event.SecretLeaseCreatedEvent;
import org.springframework.vault.core.lease.event.SecretLeaseExpiredEvent;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class VaultConfig {

  private final SecretLeaseContainer leaseContainer;

  private final HikariDataSource hikariDataSource;

  @Value("${spring.cloud.vault.database.role}")
  private String databaseRole;

  @PostConstruct
  private void postConstruct() {
    String path = "database/creds/" + databaseRole;
    leaseContainer.addLeaseListener(event -> {
      if (!path.equals(event.getSource().getPath())) {
        return;
      }

      log.info("Lease event {}, lease Id {}:", event, event.getLease().getLeaseId());

      if (event instanceof SecretLeaseExpiredEvent && RENEW == event.getSource().getMode()) {
        log.info("Replace RENEW for expired credential with ROTATE");
        leaseContainer.requestRotatingSecret(path);
      }

      if (event instanceof SecretLeaseCreatedEvent && ROTATE == event.getSource().getMode()) {
        Map<String, Object> secrets = ((SecretLeaseCreatedEvent) event).getSecrets();
        String username = (String) secrets.get("username");
        String password = (String) secrets.get("password");
        log.info("XXXXX New username = {}", username);
        hikariDataSource.getHikariConfigMXBean().setUsername(username);
        hikariDataSource.getHikariConfigMXBean().setPassword(password);
        log.info("Soft evicting db connections...");
        hikariDataSource.getHikariPoolMXBean().softEvictConnections();
      }
    });
  }
}
