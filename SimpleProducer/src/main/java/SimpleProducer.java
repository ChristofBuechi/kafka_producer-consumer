//import util.properties packages
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

//Create java class named "SimpleProducer"
public class SimpleProducer {

    public static void main(String[] args) throws Exception{

        //Assign topicName to string variable
        String topicName = "myTopic";

        // create instance for properties to access producer configs
        Properties props = new Properties();

        //Assign localhost id
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");

        //Set acknowledgements for producer requests.
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        //If the request fails, the producer can automatically retry,
        props.put(ProducerConfig.RETRIES_CONFIG, 0);

        //Specify buffer size in config
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        //Reduce the no of requests less than 0
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 16384);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for(int i = 0; i < 10_000; i++)
            producer.send(new ProducerRecord<>(topicName,
                    Integer.toString(i), Integer.toString(i))).get();
        System.out.println("Message sent successfully");
        producer.close();
    }
}