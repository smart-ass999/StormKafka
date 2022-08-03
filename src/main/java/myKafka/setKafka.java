package myKafka;

import org.apache.storm.Config;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;

import java.util.HashMap;
import java.util.Map;

public class setKafka {
    public static SpoutConfig setSpoutConfig()
    {
        BrokerHosts brokerHosts =new ZkHosts("VM-4-13-centos:2181");
        String zkRoot="/offset";
        String topic="Test4";
        String spoutId="myStorm.kafkaSpout";
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,topic,zkRoot,spoutId);
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        spoutConfig.ignoreZkOffsets = false;
        return  spoutConfig;
    }
    public static Config setConfig()
    {
        Config conf=new Config();
        //不输出调试信息
        conf.setDebug(false);
        //设置一个spout task中处于pending状态的最大的tuples数量
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        Map<String, String> map=new HashMap<String,String>();
        // 配置Kafka broker地址
        map.put("metadata.broker.list", "VM-4-13-centos:9092");
        // serializer.class为消息的序列化类
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        // 配置KafkaBolt生成的topic
        conf.put("topic", "receive");
        return conf;
    }
}
