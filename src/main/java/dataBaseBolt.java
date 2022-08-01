import com.google.gson.Gson;
import myJDBC.*;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.lang.reflect.Type;
import java.util.List;


public class dataBaseBolt extends BaseBasicBolt {

    private BasicOutputCollector basicOutputCollector;
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String resultString = tuple.getStringByField("result");
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Entrust>>(){}.getType();
            List<Entrust> entrustsList = gson.fromJson(resultString, type);
            for(int i = 0;i < entrustsList.size();i++)
            {
            myUtils.InsertEntrust(entrustsList.get(i));
            }
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
