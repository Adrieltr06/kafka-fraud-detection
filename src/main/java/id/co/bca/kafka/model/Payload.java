package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents the payload of a message, containing a transaction.
 */
@Data
public class Payload {

  /**
   * The transaction details contained in the payload.
   */
  @JsonProperty("after")
  private Transaction transaction;

}