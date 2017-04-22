package KafkaStreams;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

/**
 * A timestamp extractor implementation that tries to extract event time from
 * the "timestamp" field in the Json formatted message.
 */
public class JsonTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record) {
    	if (record.value() instanceof Join.DATAEXAM) {
            return ((Join.DATAEXAM) record.value()).timestamp;
        }

        if (record.value() instanceof Join.REFEXAM) {
            return ((Join.REFEXAM) record.value()).timestamp;
        }
    	
        if (record.value() instanceof JsonNode) {
        	String sc = ((JsonNode) record.value()).get("timestamp").textValue();
            return Long.parseLong(sc);
        }

        throw new IllegalArgumentException("JsonTimestampExtractor cannot recognize the record value " + record.value());
    }
}

