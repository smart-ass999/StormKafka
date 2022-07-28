package myKafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

public class CustomConsumer {
    @Test
    public void consumerStart(){

        //配置
        Properties properties = new Properties();

        //连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"124.221.196.162:9092");

        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

        //配置消费者组ID
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"remoteTest");

        //1 创建消费者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        //2订阅主题
        ArrayList<String> topics = new ArrayList<>();
        topics.add("remoteTest");
        kafkaConsumer.subscribe(topics);
        //3消费数据
        while(true){
            boolean flag = false;
            if(flag ){
                break;
            }
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(5);

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                //System.out.println(consumerRecord);
                System.out.println(consumerRecord.value());
            }
        }
    }
}
