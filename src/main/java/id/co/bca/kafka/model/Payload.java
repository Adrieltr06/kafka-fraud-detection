package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Payload {
  @JsonProperty("after")
  private Transaction transaction;
}
