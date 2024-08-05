package id.co.bca.kafka.beginner;

import id.co.mini.project.Transaction;
import id.co.mini.project.transaction_type;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class FraudDetectionApp {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(FraudDetectionApp.class.getName());

        String thresholdSource = "threshold-topic";
        String fraudSink = "fraud-topic";
        String streamAppID = "fraud-detection-app";
        String serverConfig = "172.31.188.241:9092";

//        Set properties
        Properties props = new Properties();
//        props.setProperty("bootstrap.servers", "localhost:9092");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, streamAppID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        StreamsBuilder streamsBuilder = new StreamsBuilder();

//        Get data from threshold topic
        KTable<String, String> thresholdData =
                streamsBuilder.table(thresholdSource,
                        Materialized.<String, String, KeyValueStore <Bytes, byte[]>> as("transaction-store")
                        .withKeySerde(Serdes.String()).withValueSerde(Serdes.String()));

//        Get all transaction data
        List <Transaction> transactionList = new ArrayList<>();

        final File file = new File("transaction-data.avro");
        final DatumReader<Transaction> datumReader = new SpecificDatumReader<>(Transaction.class);
        try {
            System.out.println("Reading from file");
            final DataFileReader<Transaction> dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                Transaction readTransaction = dataFileReader.next();
                System.out.println(readTransaction.toString());
                transactionList.add(readTransaction);
            }
            dataFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Check total amount to find fraud per keys,
//        create fraud data & push to fraud topic if total amount > threshold
//        Expected threshold data format
//        Key: account_number
//        Value: transaction_type;threshold

        KStream<Object, Object> fraudStream = thresholdData
                .toStream()
                .map((key, value) -> {
                    if (value.contains(";")) {
                        String transactionType = value.split(";")[0];
                        Long threshold = Long.valueOf(value.split(";")[1]);

                        Long totalAmount = 0L;
                        for (Transaction transaction : transactionList) {
                            if (transaction.getAccountNumber().equals(key) &&
                                    transaction.getTransactionType().equals(transaction_type.valueOf(transactionType))) {
                                totalAmount += transaction.getAmount();
                            }
                        }
                        if (totalAmount > threshold) {
                            String fraud = "Fraud detected! Account number: " + key + ", Transaction type: " + transactionType +
                                    ", Total amount: " + totalAmount + ", Threshold: " + threshold;
                            return new KeyValue<>(key, fraud);
                        }
                        else {
                            return null;
                        }
                    }
                    return null;
                });

        fraudStream.to(fraudSink);

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.cleanUp();
        streams.start();

//        Notes: snippet dibawah salah, hanya untuk ctt. perlu diihapus setelah project selesai
//        try {
//            streams.start();
//            while (true) {
////                Check total amount to find fraud per keys,
////                create fraud data & push to fraud topic if total amount > threshold
////                Expected threshold data format
////                Key: account_number
////                Value: transaction_type;threshold
//                thresholdData.toStream().foreach((key, value) -> {
//                    if (value.contains(";")) {
//                        String transactionType = value.split(";")[0];
//                        Long threshold = Long.valueOf(value.split(";")[1]);
//
//                        Long totalAmount = 0L;
//                        for (Transaction transaction : transactionList) {
//                            if (transaction.getAccountNumber().equals(key) &&
//                                    transaction.getTransactionType().equals(transaction_type.valueOf(transactionType))) {
//                                totalAmount += transaction.getAmount();
//                            }
//                        }
//
//                        if (totalAmount > threshold) {
//                        }
//                    }
//                });
//            }
//        } catch (Exception e) {}

//        Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
