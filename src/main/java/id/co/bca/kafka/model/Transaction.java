package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Represents a transaction entity with details such as account, amount, type, time, sender, and receiver.
 */
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

  /**
   * The unique identifier for the transaction.
   */
  @Id
  @JsonProperty("id")
  private Integer id;

  /**
   * The account associated with the transaction.
   */
  @JsonProperty("account")
  private String account;

  /**
   * The amount involved in the transaction.
   */
  @JsonProperty("amount")
  private Double amount;

  /**
   * The type of the transaction (e.g., debit, credit).
   */
  @JsonProperty("type")
  private String type;

  /**
   * The timestamp of when the transaction occurred.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("time")
  private Timestamp time;

  /**
   * The sender involved in the transaction.
   */
  @JsonProperty("sender")
  private String sender;

  /**
   * The receiver involved in the transaction.
   */
  @JsonProperty("receiver")
  private String receiver;

}