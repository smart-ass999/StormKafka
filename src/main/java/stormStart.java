import myKafka.jsonProducer;
import myStorm.ParserBolt;
import myStorm.analyseBolt;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.*;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.Config;

import java.util.Collections;

import static myKafka.setKafka.setConfig;
import static myKafka.setKafka.setSpoutConfig;

//主类
public class stormStart {
    //主方法
    public static void main(String[] args) throws Exception {
        //以下为kafka设置
        SpoutConfig spoutConfig = setSpoutConfig();
        Config conf = setConfig();
        //以下为storm拓扑构造
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //构造spout，myStorm.kafkaSpout()为spout具体实现，请打开kafkaSpout.java查看
        topologyBuilder.setSpout("kafkaSpout",new KafkaSpout(spoutConfig),1);
        topologyBuilder.setBolt("ParserBolt", new ParserBolt()).shuffleGrouping("kafkaSpout");
        topologyBuilder.setBolt("analyseBolt", new analyseBolt()).shuffleGrouping("ParserBolt");
        topologyBuilder.setBolt("dataBaseBolt", new myStorm.dataBaseBolt()).shuffleGrouping("analyseBolt");
        StormTopology topology = topologyBuilder.createTopology();
        //在本地提交
        localSubmit(topology, conf);
        jsonProducer.producerStart();
        //remoteSubmit(topology,"Test");


    }
    //本地提交入口，可以运行
    public static void localSubmit(StormTopology topology, Config conf)
    {
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("numberTopology",conf,topology);
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
