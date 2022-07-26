import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.Config;

import java.util.Collections;

public class remoteTest {

    public static void main(String[] args) throws Exception {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafkaSpout",new kafkaBolt());
        topologyBuilder.setBolt("ParserBolt", new ParserBolt()).shuffleGrouping("kafkaSpout");
        topologyBuilder.setBolt("analyseBolt", new analyseBolt()).shuffleGrouping("ParserBolt");
        topologyBuilder.setBolt("dataBaseBolt", new dataBaseBolt()).shuffleGrouping("analyseBolt");
        StormTopology topology = topologyBuilder.createTopology();
        localSubmit(topology);


    }
    public static void localSubmit(StormTopology topology)
    {
        Config conf = new Config();
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("numberTopology",conf,topology);
    }
    public static void remoteSubmit(StormTopology topology, String Name) throws Exception{
        Config conf = new Config();
        conf.put(Config.NIMBUS_SEEDS, Collections.singletonList("124.221.196.162"));
        conf.put(Config.NIMBUS_THRIFT_PORT, 6627);
        conf.put(Config.STORM_ZOOKEEPER_SERVERS,Collections.singletonList("124.221.196.162"));
        conf.put(Config.STORM_ZOOKEEPER_PORT,2181);
        conf.put(Config.TASK_HEARTBEAT_FREQUENCY_SECS,10000);
        conf.setDebug(false);
        conf.setNumAckers(1);
        conf.setMaxTaskParallelism(10);
        StormSubmitter.submitTopology(Name, conf, topology);



    }







}
