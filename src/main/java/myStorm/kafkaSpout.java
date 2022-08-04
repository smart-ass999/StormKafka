package myStorm;

import myJDBC.Result;
import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static myJDBC.JDBCUtils.InsertEntrustList;

public class kafkaSpout extends BaseRichSpout {
    private SpoutOutputCollector spoutOutputCollector;
    private int number;
    @Override
    //open方法了，spout的入口，必须要定义
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;
    }

    @Override
    //nextTuple()为主体
    public void nextTuple() {
        int number = 1;
        Result result = new Result();

        File file = new File(kafkaSpout.class.getClassLoader().getResource("data.json").getFile());
        String data = "";
        try {
            data = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送数据：1
        this.spoutOutputCollector.emit(new Values(data));

        try {
            InsertEntrustList(data);
            System.out.println("生成数据" + data);
            //五秒发送一次
            Thread.sleep(20000);
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    //定义数据域，用于为后面的Bolt读取数据
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("result"));
    }

    public void ack(Object msgId) {
        super.ack(msgId);
    }
}
