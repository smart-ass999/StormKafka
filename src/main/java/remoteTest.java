import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.*;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.Config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import myKafka.*;
//主类
public class remoteTest {
    //主方法
    public static void main(String[] args) throws Exception {
        //以下为kafka设置
        BrokerHosts brokerHosts =new ZkHosts("VM-4-13-centos:2181");
        String zkRoot="";
        String topic="Test";
        String spoutId="kafkaSpout";
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,topic,zkRoot,spoutId);
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
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
        //以下为storm拓扑构造

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //构造spout，kafkaSpout()为spout具体实现，请打开kafkaSpout.java查看
        topologyBuilder.setSpout("kafkaSpout",new KafkaSpout(spoutConfig),1);
        topologyBuilder.setBolt("ParserBolt", new ParserBolt()).shuffleGrouping("kafkaSpout");
        topologyBuilder.setBolt("analyseBolt", new analyseBolt()).shuffleGrouping("ParserBolt");
        //topologyBuilder.setBolt("dataBaseBolt", new dataBaseBolt()).shuffleGrouping("analyseBolt");
        StormTopology topology = topologyBuilder.createTopology();
        //在本地提交
        localSubmit(topology, conf);
        //jsonProducer.producerStart();
        //remoteSubmit(topology,"Test");


    }
    //本地提交入口，可以运行
    public static void localSubmit(StormTopology topology, Config conf)
    {
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("numberTopology",conf,topology);
    }
    //jar包提交
    public static void jarSubmit(StormTopology topology,String topoName) throws AuthorizationException, InvalidTopologyException, AlreadyAliveException {
        Config conf = new Config();
        StormSubmitter.submitTopologyAs(topoName,conf,topology,null,null,"root");

    }
    //远程提交入口，目前仍在开发中
    public static void remoteSubmit(StormTopology topology, String Name) throws Exception{
        Config conf = new Config();
        conf.put(Config.NIMBUS_SEEDS, Collections.singletonList("VM-4-13-centos"));
        conf.put(Config.NIMBUS_THRIFT_PORT, 6627);
        conf.put(Config.STORM_ZOOKEEPER_SERVERS,Collections.singletonList("VM-4-13-centos"));
        conf.put(Config.STORM_ZOOKEEPER_PORT,2181);
        conf.put(Config.TASK_HEARTBEAT_FREQUENCY_SECS,10000);
        conf.setDebug(false);
        conf.setNumAckers(1);
        conf.setMaxTaskParallelism(10);
        String jarPath = "E:\\StormKafka\\out\\StormKafka_jar\\StormKafka.jar";
        System.setProperty("storm.jar",jarPath);
        StormSubmitter.submitTopologyAs(Name, conf, topology, null, null, "root");

    }







}
