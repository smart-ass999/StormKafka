package myJDBC;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;
import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//数据库工具类，所有的类均已经过测试
public class myUtils {
    // 建立数据库连接
    public static Connection getConnection() throws Exception
    {
        Properties pro = new Properties();
        InputStream is  = myUtils.class.getClassLoader().getResourceAsStream("dataBase.properties");
        System.out.println(is);
        pro.load(is);
        String url = pro.getProperty("jdbc.url");
        String Driver = pro.getProperty("jdbc.driver");
        String username = pro.getProperty("jdbc.username");
        String password = pro.getProperty("jdbc.password");
        Class.forName(Driver);
        Connection conn = DriverManager.getConnection(url,username,password);
        return conn;
    }
    //插入一条风险结果数据
    public static void InsertResult(Result result) throws Exception {

        Connection conn = getConnection();
        String insertSQL = "insert into result values ("+"\'"+result.exception_scenarios+"\'"+","+"\'"+result.exception_date+"\'"+","+"\'"+result.exception_time+"\'"+","+"\'"+result.market_sector+"\'"+","+"\'"+result.entrust_account+"\'"+","+"\'"+result.stock_symbol+"\'"+","+"\'"+result.description+"\'"+","+"\'"+result.risk_level+"\'"+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        conn.close();

    }
    //插入一条原始数据
    public static void InsertEntrust(Entrust init) throws Exception {
        Connection conn = getConnection();
        String insertSQL = "insert into entrust values ("+"\'"+init.entrust_date+"\'"+","+"\'"+init.entrust_time+"\'"+","+"\'"+init.market_sector+"\'"+","+"\'"+init.entrust_account+"\'"+","+"\'"+init.stock_symbol+"\'"+","+"\'"+init.entrust_id+"\'"+","+"\'"+init.entrust_behavior+"\'"+","+"\'"+init.entrust_state+"\'"+","+"\'"+init.entrust_count+"\'"+","+"\'"+init.entrust_prise+"\'"+","+"\'"+init.entrust_amount+"\'"+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        conn.close();
    }
    //查询数据，功能还需要完善
    public static void Select(String tableName) throws Exception {
        Connection conn = getConnection();
        String selectSQL = "select * from " + tableName;
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(selectSQL);
        if(resultSet.next())
            System.out.println(resultSet.getString("stock_symbol"));
        conn.close();
    }
   //插入委托数据集合
    public static void InsertEntrustList(String entrustString) throws Exception {
        Gson gson = new Gson();
        Type entrustType = new TypeToken<List<Entrust>>(){}.getType();
        List<Entrust> entrustList = gson.fromJson(entrustString, entrustType);
        for(int i = 0;i < entrustList.size();i++)
        {
            InsertEntrust(entrustList.get(i));
        }
    }
    //插入结果数据集合
    public static void InsertReultList(String resultString) throws Exception{
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Result>>(){}.getType();
        List<Result> resultList = gson.fromJson(resultString, resultType);
        for(int i = 0;i < resultList.size();i++)
        {
            InsertResult(resultList.get(i));
        }
    }

}
