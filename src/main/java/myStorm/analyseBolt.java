package myStorm;

import com.google.gson.Gson;
import myJDBC.Entrust;
import myJDBC.Result;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static myAnalyse.Analyse.*;

public class analyseBolt extends BaseBasicBolt {
    private BasicOutputCollector basicOutputCollector;
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        System.out.println("获得数据" + tuple.getStringByField("result"));
        String entrustString = tuple.getStringByField("result");
        this.basicOutputCollector = basicOutputCollector;
        String resultString  = null;
        try {
            resultString = getResultString(entrustString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //最终发送给databasebolt的json串
        System.out.println("得到结果数据" + resultString);
        this.basicOutputCollector.emit(new Values(resultString));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("result"));

    }
}
