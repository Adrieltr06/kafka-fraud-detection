package id.co.bca.kafka.consumer;

import id.co.bca.kafka.model.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for consuming alert messages from Kafka.
 */
@Slf4j
@Service
public class AlertConsumer {

  /**
   * Consumes alert messages from the specified Kafka topic and group.
   *
   * @param alert the alert message received from the Kafka topic
   */
  @KafkaListener(topics = "alerts-topic", groupId = "alert-group")
  public void consume(Alert alert) {
    sendAlert(alert);
  }

  /**
   * Sends an alert by logging a warning message.
   *
   * @param alert the alert message to be logged
   */
  private void sendAlert(@NonNull Alert alert) {
    log.warn("Alert: Amount {} for account {} at time {} is above the threshold!",
        alert.getAmount(), alert.getAccount(), alert.getTime());
  }

}