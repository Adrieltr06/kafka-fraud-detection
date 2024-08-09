package id.co.bca.kafka.producer;

import id.co.bca.kafka.model.Threshold;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThresholdProducer {

  private final KafkaTemplate<String, Threshold> kafkaTemplate;

  public void sendThreshold(Threshold threshold) {
    kafkaTemplate.send("threshold-topic", threshold.getAccountNumber(), threshold);
  }
}