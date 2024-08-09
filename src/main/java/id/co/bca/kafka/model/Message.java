package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {
  @JsonProperty("payload")
  private Payload payload;
}