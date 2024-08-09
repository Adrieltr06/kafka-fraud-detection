package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents a threshold with account number and threshold amount.
 */
@Data
public class Threshold {

  /**
   * The account number associated with the threshold.
   */
  @JsonProperty("account_number")
  private String accountNumber;

  /**
   * The threshold amount for the account.
   */
  @JsonProperty("threshold_amount")
  private Double thresholdAmount;

}