package kz.qazcode.foo.service;

import java.util.List;
import kz.qazcode.foo.domain.FooData;
import kz.qazcode.foo.repository.FooDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FooDataService {

  private final FooDataRepository fooDataRepository;

  public void createItem() {
    FooData fooData = new FooData();
    fooData.setKey("key_" + System.currentTimeMillis());
    fooData.setValue("value_" + System.currentTimeMillis());
    fooDataRepository.save(fooData);
  }

  public Long countItems() {
    return fooDataRepository.count();
  }
}
