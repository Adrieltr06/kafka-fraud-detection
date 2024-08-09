package id.co.bca.kafka.stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bca.kafka.model.Transaction;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TransactionDeserializer implements Deserializer<Transaction> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(TransactionDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // not currently used
    }

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

    @Override
    public void close() {
        // not currently used
    }
}