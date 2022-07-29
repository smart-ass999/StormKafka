import myJDBC.*;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;


public class dataBaseBolt extends BaseBasicBolt {

    private BasicOutputCollector basicOutputCollector;
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String resultString = tuple.getStringByField("result");
        String entrustString = tuple.getStringByField("entrust");
        Result result = myUtils.getResultObject(resultString);
        Entrust entrust = myUtils.getEntrustObject(entrustString);
        try {
            myUtils.InsertEntrust(entrust);
            myUtils.InsertResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.basicOutputCollector = basicOutputCollector;
        this.basicOutputCollector.emit(new Values(tuple.getStringByField("result")));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
