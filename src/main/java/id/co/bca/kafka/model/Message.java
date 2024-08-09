package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents a message containing a payload.
 */
@Data
public class Message {

  /**
   * The payload of the message.
   */
  @JsonProperty("payload")
  private Payload payload;

}