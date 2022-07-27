import myJDBC.Result;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

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
        //发送数据：1
        this.spoutOutputCollector.emit(new Values(number));
        try {
            System.out.println("生成数据1");
            //五秒发送一次
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    //定义数据域，用于为后面的Bolt读取数据
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("result"));
    }
}
