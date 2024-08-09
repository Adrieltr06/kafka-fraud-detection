package id.co.bca.kafka.producer;

import id.co.bca.kafka.model.Threshold;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for producing threshold messages to a Kafka topic.
 */
@Service
@RequiredArgsConstructor
public class ThresholdProducer {

  /**
   * Kafka template for sending messages.
   */
  private final KafkaTemplate<String, Threshold> kafkaTemplate;

  /**
   * Sends a threshold message to the Kafka topic.
   *
   * @param threshold the threshold message to send
   */
  public void sendThreshold(Threshold threshold) {
    kafkaTemplate.send("threshold-topic", threshold.getAccountNumber(), threshold);
  }

}