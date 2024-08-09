package id.co.bca.kafka.consumer;

import id.co.bca.kafka.model.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertConsumer {

  @KafkaListener(topics = "alerts-topic", groupId = "alert-group")
  public void consume(Alert alert) {
    sendAlert(alert);
  }

  private void sendAlert(@NonNull Alert alert) {
    log.warn("Alert: Amount {} for account {} at time {} is above the threshold!",
        alert.getAmount(), alert.getAccount(), alert.getTime());
  }
}