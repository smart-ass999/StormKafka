package myStorm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class ParserBolt extends BaseBasicBolt {
    private BasicOutputCollector basicOutputCollector;
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        //根据之前Spout定义的数据域读取数据
        System.out.println("处理数据" + tuple.getStringByField("result"));
        this.basicOutputCollector = basicOutputCollector;
        //发送给下一个Bolt
        this.basicOutputCollector.emit(new Values(tuple.getStringByField("result")));

    }

    @Override
    //定义数据域
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("result"));

    }
}
