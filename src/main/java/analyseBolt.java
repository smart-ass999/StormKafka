import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class analyseBolt extends BaseBasicBolt {
    private BasicOutputCollector basicOutputCollector;
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        System.out.println("获得数据" + tuple.getStringByField("result"));
        this.basicOutputCollector = basicOutputCollector;
        this.basicOutputCollector.emit(new Values(tuple.getStringByField("result")));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("result","entrust"));

    }
}
