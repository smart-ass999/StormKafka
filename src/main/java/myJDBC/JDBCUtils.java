package myJDBC;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.gson.Gson;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;

import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
//数据库工具类，所有的类均已经过测试
public class JDBCUtils {
    // 建立数据库连接
    static DataSource dataSource;

    static {
        try {
            dataSource = getDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() throws Exception {
        Properties properties = new Properties();

        // 获取类的类加载器
        InputStream resourceAsStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");

        // 获取druid-1.0.9.properties配置文件资源输入流

        // 加载配置文件
        properties.load(resourceAsStream);

        // 获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        return conn;
    }

    //插入一条风险结果数据
    public static void InsertResult(Result result) throws Exception {

        Connection conn = getConnection();
        String insertSQL = "insert into result values ("+"\'"+ result.getException_scenarios() +"\'"+","+"\'"+ result.getException_date() +"\'"+","+"\'"+ result.getException_time() +"\'"+","+"\'"+ result.getMarket_sector() +"\'"+","+"\'"+ result.getEntrust_account() +"\'"+","+"\'"+ result.getStock_symbol() +"\'"+","+"\'"+ result.getDescription() +"\'"+","+"\'"+ result.getRisk_level() +"\'"+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        st.close();
        conn.close();

    }
    //插入一条原始数据
    public static void InsertEntrust(Entrust init) throws Exception {
        Connection conn = getConnection();
        String insertSQL = "insert into entrust values ("+"\'"+ init.getEntrust_date() +"\'"+","+"\'"+ init.getEntrust_time() +"\'"+","+"\'"+ init.getMarket_sector() +"\'"+","+"\'"+ init.getEntrust_account() +"\'"+","+"\'"+ init.getStock_symbol() +"\'"+","+"\'"+ init.getEntrust_id() +"\'"+","+"\'"+ init.getEntrust_behavior() +"\'"+","+"\'"+ init.getEntrust_state() +"\'"+","+"\'"+ init.getEntrust_count() +"\'"+","+"\'"+ init.getEntrust_price() +"\'"+","+"\'"+ init.getEntrust_amount() +"\'"+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        st.close();
        conn.close();
    }
    //查询数据，功能还需要完善
    public static void Select(String tableName) throws Exception {
        Connection conn = getConnection();
        String selectSQL = "select * from " + tableName;
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(selectSQL);
        while(resultSet.next())
            System.out.println(resultSet.getString("stock_symbol"));
        st.close();
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
            if (!resultList.get(i).getRisk_level().equals("null"))
            InsertResult(resultList.get(i));
        }
    }

    public static int CountAll(String tableName) throws Exception {
        Connection conn = getConnection();
        String selectSQL = "select * from " + tableName;
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(selectSQL);
        int num=0;
        while (resultSet.next())
            num++;
        resultSet.close();
        st.close();
        conn.close();
        return num;
    }
    public static int CounAllBy_Userid_stocki(String user_id,String stock_id) throws Exception {
        Connection conn = getConnection();
        String sql="select count(*) from entrust where entrust_account="+"\'" + user_id + "\'" +"and stock_symbol="+"\'" +stock_id+"\'" ;
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int num=0;
        while(resultSet.next())
            num = resultSet.getInt("count");
        resultSet.close();
        st.close();
        conn.close();
        return num;

    }

    //通过客户代码和股票代码查询数量
    public static int CounBy_Userid_stockid(String user_id,String stock_id) throws Exception {
        Connection conn = getConnection();
        String sql="select count(*) from entrust where entrust_account="+"\'" + user_id + "\'" +"and stock_symbol="+"\'" +stock_id+"\'" +"and entrust_state='T'";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int num=0;
        while(resultSet.next())
            num = resultSet.getInt("count");
        resultSet.close();
        st.close();
        conn.close();
        return num;

    }

    public static int CounBy_Userid_stockid_time(String user_id,String stock_id,String date ,String time) throws Exception {
        Connection conn = getConnection();
        String sql="select * from entrust where entrust_account=" +"\'" + user_id + "\'" +"and stock_symbol=" + "\'" +stock_id + "\'" +"and entrust_date=" + "\'" +date + "\'" +"and entrust_time="+"\'"+time+"\'";
        Statement st = conn.createStatement();
        ResultSet r = st.executeQuery(sql);
        int flag=0;
        int num=0;
        while (r.next()) {
            num++;
        }
        if(num>=5){
            flag=1;
        }
        r.close();
        st.close();
        conn.close();

        return flag;
    }
    public static rotation_info selectBy_Userid_stockid_date1(String user_id, String stock_id, String date, int flag) throws Exception {
        Connection conn = getConnection();

        String sql = "select * from entrust where entrust_account=" + "\'" + user_id + "\'" + "and stock_symbol=" + "\'" + stock_id + "\'" + "and entrust_date=" + "\'" + date + "\'" + "and entrust_behavior=\'buy\'";
        Statement st = conn.createStatement();
        ResultSet presultSet = st.executeQuery(sql);

        rotation_info protate_count = new rotation_info();
        while (presultSet.next()) {
            protate_count.setQuantity_in(presultSet.getDouble("entrust_count"));
            System.out.println(presultSet.getString("entrust_count"));

            protate_count.setPrice_in(presultSet.getDouble("entrust_price"));
            protate_count.setTime(presultSet.getString("entrust_time"));
        }
        String sql2 = "select * from entrust where entrust_account=" +"\'" + user_id + "\'" +"and stock_symbol=" + "\'" +stock_id + "\'" +"and entrust_date=" + "\'" +date + "\'" +"and entrust_behavior=\'sell\'";
        Statement st2 = conn.createStatement();
        ResultSet bresultSet = st2.executeQuery(sql2);
        rotation_info brotate_count = new rotation_info();
        while (bresultSet.next()) {
            brotate_count.setQuantity_out(bresultSet.getDouble("entrust_count"));

            brotate_count.setPrice_out(bresultSet.getDouble("entrust_price"));
            brotate_count.setTime(bresultSet.getString("entrust_time"));
        }
        presultSet.close();
        bresultSet.close();
        st.close();
        st2.close();

        conn.close();
        if (flag == 1) {
            return protate_count;
        }
        else{
            return  brotate_count;
        }
    }
    //通过客户代码、股票代码和日期，查询当日此客户在同一股票的交易记录
    public static rotation_info selectBy_Userid_stockid_date(String user_id,String stock_id,String date) throws Exception {
        Connection conn = getConnection();
        String sql = "select * from entrust where entrust_account=" +"\'" + user_id + "\'" +"and stock_symbol=" + "\'" +stock_id + "\'" +"and entrust_date=" + "\'" +date + "\'" +"and entrust_behavior=\'buy\'";

        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        rotation_info rotate_count = new rotation_info();
        while (resultSet.next()) {
            rotate_count.setQuantity_in(rotate_count.getQuantity_in()+resultSet.getDouble("entrust_count"));

            rotate_count.setAmount_in(resultSet.getDouble("entrust_amount"));
            rotate_count.setTime(resultSet.getString("entrust_time"));
        }

        resultSet.close();
        st.close();
        conn.close();

        return  rotate_count;

    }


    //通过客户代码、股票代码查询撤销单量
    public static int select_revokeBy_Userid_stockid(String user_id,String stock_id) throws Exception {
        Connection conn = getConnection();
        String sql="select * from entrust where entrust_account="+"\'"+user_id+"\'"+"and stock_symbol="+"\'"+stock_id+"\'"+"and entrust_state='F'";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int num=0;
        while(resultSet.next())
            num++;
        resultSet.close();
        st.close();
        conn.close();
        return num;
    }

}