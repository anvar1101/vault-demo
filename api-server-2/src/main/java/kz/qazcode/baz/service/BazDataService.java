package kz.qazcode.baz.service;

import java.util.List;
import kz.qazcode.baz.domain.BazData;
import kz.qazcode.baz.repository.BazDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BazDataService {

  private final BazDataRepository bazDataRepository;

  public void createItem(String key, String value) {
    BazData bazData = new BazData();
    bazData.setKey(key);
    bazData.setValue(value);
    bazDataRepository.save(bazData);
  }

  public Long countItems() {
    return bazDataRepository.count();
  }

}
