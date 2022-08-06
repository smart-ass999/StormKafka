package myKafka;
import com.google.gson.Gson;
import myJDBC.Entrust;
import myJDBC.Result;
import myStorm.kafkaSpout;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;
import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class jsonProducer{
    public static void producerStart() throws InterruptedException {
        // 配置
        Properties properties = new Properties();

        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"VM-4-13-centos:9092");

        // 指定对应的key和value的序列化类型 key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 1 常见kafka生产者对象:
        KafkaProducer<Object, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2 发送数据
        File file = new File(jsonProducer.class.getClassLoader().getResource("data.json").getFile());
        String data = "";
        try {
            data = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type entrustType = new TypeToken<List<Entrust>>(){}.getType();
        List<Entrust> entrustList = gson.fromJson(data, entrustType);
        List<Entrust> entrustData = new ArrayList<Entrust>();

        for (int i = 0 ; i < entrustList.size();i+=3){
            entrustData.add(entrustList.get(i));
            entrustData.add(entrustList.get(i+1));
            entrustData.add(entrustList.get(i+2));
            String s = gson.toJson(entrustData);
            entrustData.clear();
            ProducerRecord<Object, String> record = new ProducerRecord<>("Test7", s);
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null){
                        System.out.println("主题："+recordMetadata.topic()+" 分区："+recordMetadata.partition());
                    }
                }
            });

            Thread.sleep(5000);
        }
        // 3关闭资源
        kafkaProducer.close();

    }
}
