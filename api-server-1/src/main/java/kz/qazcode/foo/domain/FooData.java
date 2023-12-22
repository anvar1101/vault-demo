package kz.qazcode.foo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "foo_data")
public class FooData {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String key;

  private String value;
}
