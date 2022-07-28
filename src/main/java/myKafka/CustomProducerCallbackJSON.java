package myKafka;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Properties;

public class CustomProducerCallbackJSON {
    @Test
    public void producerStart() throws InterruptedException {
        // 配置
        Properties properties = new Properties();

        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"124.221.196.162:9092");

        // 指定对应的key和value的序列化类型 key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 1 常见kafka生产者对象:
        KafkaProducer<Object, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2 发送数据
        for (int i = 0; i < 5; i++) {
            String order = new String("111");
            ProducerRecord<Object, String> record = new ProducerRecord<>("remoteTest", order);
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null){
                        System.out.println("主题："+recordMetadata.topic()+" 分区："+recordMetadata.partition());
                    }
                }
            });

            Thread.sleep(1);
        }
        // 3关闭资源
        kafkaProducer.close();

    }
}
