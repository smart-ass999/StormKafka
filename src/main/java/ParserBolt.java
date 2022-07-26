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
        System.out.println("处理数据" + tuple.getIntegerByField("result"));
        this.basicOutputCollector = basicOutputCollector;
        this.basicOutputCollector.emit(new Values(tuple.getIntegerByField("result")));


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("result"));

    }
}
