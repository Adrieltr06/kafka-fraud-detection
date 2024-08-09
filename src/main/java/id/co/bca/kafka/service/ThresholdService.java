package id.co.bca.kafka.service;

import id.co.bca.kafka.model.Threshold;
import id.co.bca.kafka.producer.ThresholdProducer;
import id.co.bca.kafka.repository.ThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThresholdService {

  private final ThresholdRepository thresholdRepository;
  private final ThresholdProducer thresholdProducer;

  @Transactional
  public Threshold createThreshold(Threshold threshold) {
    thresholdRepository.save(threshold);
    thresholdProducer.sendThreshold(threshold);
    return threshold;
  }

}