package kz.qazcode.foo.repository;

import java.util.UUID;
import kz.qazcode.foo.domain.FooData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooDataRepository extends JpaRepository<FooData, UUID> {

}
