package id.co.bca.kafka.stream;

import id.co.bca.kafka.model.Alert;
import id.co.bca.kafka.model.Transaction;
import id.co.bca.kafka.model.Threshold;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.sql.Timestamp;
import java.util.Properties;

/**
 * Class for processing Kafka streams related to thresholds and transactions.
 */
public class ThresholdStream {

  /**
   * Main method to start the Kafka Streams application.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "threshold-stream");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    StreamsBuilder builder = new StreamsBuilder();

    // Create a KTable for thresholds from the "threshold-topic"
    KTable<String, Threshold> thresholds = builder.table("threshold-topic",
        Consumed.with(Serdes.String(), new JsonSerde<>(Threshold.class)),
        Materialized.<String, Threshold>as(Stores.persistentKeyValueStore("thresholds-store"))
            .withKeySerde(Serdes.String())
            .withValueSerde(new JsonSerde<>(Threshold.class)));

    // Create a KStream for transactions from the "db.public.transactions" topic
    KStream<String, Transaction> transactions = builder.stream("db.public.transactions",
        Consumed.with(Serdes.String(), Serdes.serdeFrom(new JsonSerializer<>(), new TransactionDeserializer())));

    // Select the key for transactions by account
    KStream<String, Transaction> transactionsByAccount = transactions.selectKey((key, transaction) -> transaction.getAccount());

    // Join transactions with thresholds and create alerts if the transaction amount exceeds the threshold
    transactionsByAccount.join(thresholds, (transaction, threshold) -> {
      if (threshold != null && transaction.getAmount() > threshold.getThresholdAmount()) {
        return new Alert(transaction.getAccount(), transaction.getAmount(), new Timestamp(transaction.getTime().getTime()));
      }
      return null;
    }).filter((key, alert) -> alert != null)
      .to("alerts-topic", Produced.with(Serdes.String(), new JsonSerde<>(Alert.class)));

    // Build and start the Kafka Streams application
    KafkaStreams streams = new KafkaStreams(builder.build(), props);
    streams.start();

    // Add a shutdown hook to close the streams on exit
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

}