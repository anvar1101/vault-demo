package kz.qazcode.baz.repository;

import java.util.UUID;
import kz.qazcode.baz.domain.BazData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BazDataRepository extends JpaRepository<BazData, UUID> {


}
