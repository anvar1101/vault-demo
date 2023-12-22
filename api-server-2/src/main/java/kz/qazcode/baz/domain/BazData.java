package kz.qazcode.baz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "baz_data")
public class BazData {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String key;

  private String value;
}
