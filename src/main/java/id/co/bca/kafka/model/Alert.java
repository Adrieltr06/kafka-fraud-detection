package id.co.bca.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Represents an alert with account information, amount, and timestamp.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

  /**
   * The account that performed the transaction.
   */
  private String account;

  /**
   * The amount of the transaction.
   */
  private double amount;

  /**
   * The timestamp when the transaction happened.
   */
  private Timestamp time;

}