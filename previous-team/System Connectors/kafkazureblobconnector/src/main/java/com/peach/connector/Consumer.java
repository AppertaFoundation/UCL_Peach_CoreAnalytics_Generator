/**
 * It has been referred to KafkaConsumer API documentation
 * Online link available at : https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
 */

package com.peach.connector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Consumer {
	private static KafkaConsumer<String, String> consumer=null;
	Map<String, String> topictocontent = new HashMap<String, String>();
	private final int batchsize=4;
	private int recordcount=0;
    private String path="/home/peach/kafka.properties";
	private InputStream input=null;
    private Properties props = new Properties();
    
        //Configure Kafka Consumer group
	    public void initialize() throws IOException {  
    	input=new FileInputStream(path);
    	props.load(input);
    	consumer = new KafkaConsumer<>(props);                
        }
	    
        //Consumer published messages
        public void consume(){
        try { 	
        consumer.subscribe(Arrays.asList(gettopics()));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(15);
            for (ConsumerRecord<String, String> record : records) {
               if(topictocontent.containsKey(record.topic())) 
            	   { topictocontent.put(record.topic(), topictocontent.get(record.topic())+"\n"+record.value());}
               else { topictocontent.put(record.topic(), "\n"+record.value());}
     
               recordcount++;
            }
            if (recordcount>=batchsize)
            {
            	    BlobConnection bc= new BlobConnection(topictocontent);
            	    bc.appendBlob();
            		consumer.commitSync();
            		topictocontent.clear();
            		recordcount=0;
                	
            }               
        
        } 
          
        }
        catch(WakeupException | URISyntaxException | IOException e)
        {
        	e.printStackTrace();
        }
        finally {
        	consumer.close();
        	
        }
		              
     }  
        public void shutdown(){
        	consumer.wakeup();
        }
        // Get a Kafka topic list
        public String[] gettopics() throws URISyntaxException, IOException
        {
        	List<String> topiclist=Files.readAllLines(Paths.get("/home/peach/topiclist.txt"),StandardCharsets.UTF_8);
        	String[] topics=new String[topiclist.size()];
        	topics=topiclist.toArray(topics);		
        	return topics;
        }
        //Get a path to kafka.properties file
        public  String getPath()
        {
        	return this.path;
        }
        //Get configuration details
        public Properties getProperties()
        {
        	return props;
        }
    


}
