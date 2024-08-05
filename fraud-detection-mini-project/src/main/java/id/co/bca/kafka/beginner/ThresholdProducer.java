package id.co.bca.kafka.beginner;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

//This producer will produce data from file to threshold topic
//The expected data structure (temporary): key;value
//Notes: Producer hanya untuk keperluan testing
public class ThresholdProducer {
    private static final Logger log = Logger.getLogger(ThresholdProducer.class.getSimpleName());
    private static final File file = new File("src/main/resources/user-threshold.txt");
    private static final String thresholdTopic = "threshold-topic";

    public static void main(String[] args) {
        Properties props = new Properties();
//        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("bootstrap.servers", "172.31.188.241:9092");
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());
        props.setProperty("batch.size", String.valueOf(3));
        props.setProperty("acks", "1");

        try {
            System.out.println("Reading from file");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String rawThreshold = sc.nextLine();
                String key = rawThreshold.split(";")[0];
                String value = rawThreshold.split(";", 2)[1];

                System.out.println("Key: " + key + " Value: " + value);

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(thresholdTopic, key, value);

                try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
                    producer.send(producerRecord, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if (e == null) {
                                log.info("Partition: " + recordMetadata.partition() + "\n" +
                                        "Offset   : " + recordMetadata.offset() + "\n" +
                                        "Timestamp: " + recordMetadata.timestamp() + "\n"
                                );
                            } else {
                                log.severe("Error while producing message: " + e);
                            }
                        }
                    });
                    producer.flush();
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
