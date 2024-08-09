package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
  @Id
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("account")
  private String account;

  @JsonProperty("amount")
  private Double amount;

  @JsonProperty("type")
  private String type;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("time")
  private Timestamp time;

  @JsonProperty("sender")
  private String sender;

  @JsonProperty("receiver")
  private String receiver;
}