package myJDBC;

import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    //插入风险结果数据
    public static void InsertResult(Result result) throws Exception {
        Connection conn = getConnection();
        String insertSQL = "insert into result values ("+"\'"+result.exception_scenarios+"\'"+","+"\'"+result.exception_date+"\'"+","+"\'"+result.exception_time+"\'"+","+"\'"+result.market_sector+"\'"+","+"\'"+result.entrust_account+"\'"+","+"\'"+result.stock_symbol+"\'"+","+"\'"+result.description+"\'"+","+"\'"+result.Risk_level+"\'"+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        conn.close();

    }
    //插入原始数据
    public static void InsertInit(Init init) throws Exception {
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
}
