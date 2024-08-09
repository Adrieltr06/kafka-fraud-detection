package id.co.bca.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "thresholds")
public class Threshold {

  @Id
  @Column(name = "account_number")
  @JsonProperty("account_number")
  private String accountNumber;

  @Column(name = "threshold_amount")
  @JsonProperty("threshold_amount")
  private Double thresholdAmount;

}