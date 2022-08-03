package myAnalyse;

import com.google.gson.Gson;
import myJDBC.Entrust;
import myJDBC.Result;
import myJDBC.rotation_info;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static myJDBC.JDBCUtils.*;

public class Analyse {
    public static Result Entrust_limit(Entrust data) {
        Result result_info=new Result();//返回的异常结果信息表
        String users_id=data.getEntrust_count();
        String stock_symbol=data.getStock_symbol();
        int theshold=1000000;//单用户单股票的交易委托上限阈值
        int num = 0;
        try {
            num=CounBy_Userid_stockid(users_id,stock_symbol);
        } catch (Exception e) {
            System.out.println("交易委托数量统计失败");
            e.printStackTrace();
        }
        if(num>theshold){
            result_info.setException_scenarios("委托超过限额");
            result_info.setStock_symbol(stock_symbol);
            result_info.setEntrust_account(users_id);
            result_info.setMarket_sector(data.getMarket_sector());
            result_info.setException_date(data.getEntrust_date());
            result_info.setException_time(data.getEntrust_time());
            result_info.setRisk_level("低");
        }
        return result_info;
    }
    //撤销委托超过阈值监控
    public static Result Entrust_revoke(Entrust data){
        Result result_info=new Result();//返回的异常结果信息表
        boolean is_excepetion=false;//标注是否存在异常
        String users_id=data.getEntrust_account();
        String stock_symbol=data.getStock_symbol();

        try {
            int num=select_revokeBy_Userid_stockid(users_id,stock_symbol);
            int total=CountAll("entrust");
            if(num/total>0.3||num/total<0.6){
                result_info.setException_scenarios("Entrust_revoke");
                result_info.setRisk_level("低");
            }
            if(num/total>0.6){
                result_info.setException_scenarios("Entrust_revoke");
                result_info.setRisk_level("中");
            }
        } catch (Exception e) {
            System.out.println("撤单量查询失败");
            e.printStackTrace();
        }
        result_info.setStock_symbol(stock_symbol);
        result_info.setEntrust_account(users_id);
        result_info.setMarket_sector(data.getMarket_sector());
        result_info.setException_date(data.getEntrust_date());
        result_info.setException_time(data.getEntrust_time());
        return result_info;
    }
    //日内回转监控
    public static Result Entrust_rotation(Entrust data)  {
        Result result_info=new Result();//返回的异常结果信息表
        String users_id=data.getEntrust_account();
        String stock_symbol=data.getStock_symbol();
        String date=data.getEntrust_date();
        double thehold=1000000;
        rotation_info info=null;
        try {
            info=selectBy_Userid_stockid_date(users_id,stock_symbol,date);
            if(info.getAmount_in() >=thehold){
                result_info.setRisk_level("中");
                result_info.setException_scenarios(result_info.getException_scenarios()+"Entrust_rotation");
            }
        } catch (Exception e) {
            System.out.println("日内回转交易查询失败");
            e.printStackTrace();
        }
        result_info.setStock_symbol(stock_symbol);
        result_info.setEntrust_account(users_id);
        result_info.setMarket_sector(data.getMarket_sector());
        result_info.setException_date(data.getEntrust_date());
        result_info.setException_time(data.getEntrust_time());
        return result_info;
    }
    //虚假申报

    public static Result Entrust_cheat(Entrust data){
        Result result_info=new Result();//返回的异常结果信息表
        boolean is_excepetion=false;//标注是否存在异常
        String users_id=data.getEntrust_id();
        String stock_symbol=data.getStock_symbol();
        String date=data.getEntrust_date();

        rotation_info in=null;
        rotation_info out=null;
        int flag1=0;


        try {
            int num=select_revokeBy_Userid_stockid(users_id,stock_symbol);
            int total=CountAll("entrust");
            //撤销单量阈值
            float quash_r=num/total;
            //是否存在反向交易 以低于申报买入价格反向申报卖出或者以高于申报卖出价格反向申报买入

            in =selectBy_Userid_stockid_date1(users_id,stock_symbol,date,1);
            out=selectBy_Userid_stockid_date1(users_id,stock_symbol,date,0);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date it=format.parse(in.getTime());
            Date ou=format.parse(out.getTime());
            if((in.getAmount_in() > out.getAmount_out() & it.before(ou))|(in.getAmount_in() < out.getAmount_out() & it.after(ou))){
                flag1=1;
            }

            if(num/total>=0.5 & flag1==1){
                result_info.setRisk_level("高");
                result_info.setException_scenarios(result_info.getException_scenarios()+"Entrust_rotation");

            }
        } catch (Exception e) {
            System.out.println("查询失败");
            e.printStackTrace();
        }
        result_info.setStock_symbol(stock_symbol);
        result_info.setEntrust_account(users_id);
        result_info.setMarket_sector(data.getMarket_sector());
        result_info.setException_date(data.getEntrust_date());
        result_info.setException_time(data.getEntrust_time());
        return result_info;

    }
    //频繁申报
    public static Result Entrust_frequent(Entrust data) throws Exception {
        Result result_info=new Result();//返回的异常结果信息表
        String users_id=data.getEntrust_account();
        String stock_symbol=data.getStock_symbol();
        String time=data.getEntrust_time();

        rotation_info info=null;
        try {
            int flag = CounBy_Userid_stockid_time(users_id, stock_symbol);
            if (flag == 1) {
                result_info.setRisk_level("中");
                result_info.setException_scenarios(result_info.getException_scenarios()+"Entrust_rotation");
            }
        }
        catch (Exception e){
            System.out.println("查询失败");
            e.printStackTrace();
        }

        result_info.setStock_symbol(stock_symbol);
        result_info.setEntrust_account(users_id);
        result_info.setMarket_sector(data.getMarket_sector());
        result_info.setException_date(data.getEntrust_date());
        result_info.setException_time(data.getEntrust_time());
        return result_info;

    }
    public static String getResultString(String entrustString)
    {
        Gson gson = new Gson();
        Type entrustType = new TypeToken<List<Entrust>>(){}.getType();
        List<Entrust> entrustList = gson.fromJson(entrustString, entrustType);
        List<Result> result=new ArrayList<Result>();
        for(int i = 0;i < entrustList.size();i++)
        {
            Entrust data = entrustList.get(i);
            result.add(Entrust_limit(data));
            if(Entrust_revoke(data).getRisk_level().equals("未知"))
            result.add(Entrust_revoke(data));
            if(Entrust_rotation(data).getRisk_level().equals("未知"))
            result.add(Entrust_rotation(data));
            //result.add(Entrust_cheat(data));
            try {
                //result.add(Entrust_frequent(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //负责接受每一个异常场景表的json格式串

        String resultString = null;
        if(!result.isEmpty())
        {
            resultString = gson.toJson(result);
        }
        return resultString;
    }
}
