package myJDBC;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class myUtils {

    public static Connection getConnection() throws Exception
    {
        Properties pro = new Properties();
        InputStream is  = myUtils.class.getResourceAsStream("dataBase.properties");
        pro.load(is);
        String url = pro.getProperty("jdbc.url");
        String Driver = pro.getProperty("jdbc.Driver");
        String username = pro.getProperty("jdbc.username");
        String password = pro.getProperty("jdbc.password");
        Class.forName(Driver);
        Connection conn = DriverManager.getConnection(url,username,password);
        return conn;
    }
    public static void InsertResult(Result result) throws Exception {
        Connection conn = getConnection();
        String insertSQL = "insert into Result values ("+result.exception_scenarios+","+result.exception_date+","+result.exception_time+","+result.market_sector+","+result.entrust_account+","+result.stock_symbol+","+result.description+","+result.Risk_level+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        conn.close();

    }
}
