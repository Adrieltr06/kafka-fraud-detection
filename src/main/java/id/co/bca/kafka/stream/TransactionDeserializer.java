package id.co.bca.kafka.stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bca.kafka.model.Transaction;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Deserializer for Transaction objects from Kafka messages.
 */
public class TransactionDeserializer implements Deserializer<Transaction> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(TransactionDeserializer.class);

    /**
     * Configures the deserializer.
     *
     * @param configs configuration settings
     * @param isKey whether the deserializer is for keys or values
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // not currently used
    }

    /**
     * Deserializes a byte array into a \Transaction\ object.
     *
     * @param topic the topic associated with the data
     * @param data the serialized bytes
     * @return the deserialized \Transaction\ object
     */
    @Override
    public Transaction deserialize(String topic, byte[] data) {
        try {
            JsonNode rootNode = objectMapper.readTree(data);
            JsonNode afterNode = rootNode.path("payload").path("after");
            return objectMapper.treeToValue(afterNode, Transaction.class);
        } catch (Exception e) {
            try {
                return objectMapper.readValue(data, Transaction.class);
            } catch (Exception ex) {
                logger.error("Failed to deserialize transaction", ex);
                throw new RuntimeException("Failed to deserialize transaction", ex);
            }
        }
    }

    /**
     * Closes the deserializer.
     */
    @Override
    public void close() {
        // not currently used
    }

}