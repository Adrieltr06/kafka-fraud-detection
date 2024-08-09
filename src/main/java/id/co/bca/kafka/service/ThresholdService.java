package id.co.bca.kafka.service;

import id.co.bca.kafka.model.Threshold;
import id.co.bca.kafka.producer.ThresholdProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling threshold-related operations.
 */
@Service
@RequiredArgsConstructor
public class ThresholdService {

  /**
   * Producer for sending threshold messages to Kafka.
   */
  private final ThresholdProducer thresholdProducer;

  /**
   * Saves the threshold and sends it to the Kafka topic.
   *
   * @param threshold the threshold to save and send
   * @return the saved threshold
   */
  @Transactional
  public Threshold saveThreshold(Threshold threshold) {
    thresholdProducer.sendThreshold(threshold);
    return threshold;
  }

}