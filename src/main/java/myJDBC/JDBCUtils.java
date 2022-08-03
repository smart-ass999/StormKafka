package myJDBC;

import com.google.gson.Gson;
import org.apache.storm.shade.com.google.common.reflect.TypeToken;

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
    public static Connection getConnection() throws Exception
    {
        Properties pro = new Properties();
        InputStream is  = JDBCUtils.class.getClassLoader().getResourceAsStream("dataBase.properties");
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
        String insertSQL = "insert into result values ("+"\'"+ result.getException_scenarios() +"\'"+","+"\'"+ result.getException_date() +"\'"+","+"\'"+ result.getException_time() +"\'"+","+"\'"+ result.getMarket_sector() +"\'"+","+"\'"+ result.getEntrust_account() +"\'"+","+"\'"+ result.getStock_symbol() +"\'"+","+"\'"+ result.getDescription() +"\'"+","+"\'"+ result.getRisk_level() +"\'"+")";
        Statement st = conn.createStatement();
        st.executeUpdate(insertSQL);
        System.out.println("插入一条数据");
        conn.close();

    }
    //插入一条原始数据
    public static void InsertEntrust(Entrust init) throws Exception {
        Connection conn = getConnection();
        String insertSQL = "insert into entrust values ("+"\'"+ init.getEntrust_date() +"\'"+","+"\'"+ init.getEntrust_time() +"\'"+","+"\'"+ init.getMarket_sector() +"\'"+","+"\'"+ init.getEntrust_account() +"\'"+","+"\'"+ init.getStock_symbol() +"\'"+","+"\'"+ init.getEntrust_id() +"\'"+","+"\'"+ init.getEntrust_behavior() +"\'"+","+"\'"+ init.getEntrust_state() +"\'"+","+"\'"+ init.getEntrust_count() +"\'"+","+"\'"+ init.getEntrust_prise() +"\'"+","+"\'"+ init.getEntrust_amount() +"\'"+")";
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

    public static int CountAll(String tableName) throws Exception {
        Connection conn = getConnection();
        String selectSQL = "select * from " + tableName;
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(selectSQL);
        int num=0;
        if (resultSet.next())
            num++;
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
        if(resultSet.next())
            num = resultSet.getInt("count");
        conn.close();
        return num;

    }

    public static int CounBy_Userid_stockid_time(String user_id,String stock_id) throws Exception {
        Connection conn = getConnection();
        String sortsql = "select * from entrust order by entrust_time";
        String sql="select * from entrust where entrust_account="+"\'" + user_id + "\'" +"and stock_symbol="+"\'" +stock_id+"\'" +"and entrust_state='T'";
        Statement st = conn.createStatement();
        ResultSet r = st.executeQuery(sortsql);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        int flag=0;
        if (r.next()) {
            int num=0;

            String t_from = r.getString("entrust_time");
            java.util.Date fromM = format.parse(t_from.substring(0, 8));
            long tt1 = fromM.getTime();
            ResultSet resultSet = st.executeQuery(sql);
            String t_com = null;
            if (resultSet.next()) {
                t_com = resultSet.getString("entrust_time");
                Date t2 = format.parse(t_com);
                long tt2 = t2.getTime();
                float com_1 = ((tt2 - tt1) / (1000 * 60));
                if (com_1 < 1) {
                    num++;
                }
                if(num>=5){
                    flag=1;
                }
            }

        }
        conn.close();

        return flag;
    }
    public static rotation_info selectBy_Userid_stockid_date1(String user_id,String stock_id,String date,int flag) throws Exception {
        Connection conn = getConnection();
        String sql = "select * from entrust where entrust_account=" +"\'" + user_id + "\'" +"and stock_symbol=" + "\'" +stock_id + "\'" +"and entrust_date=" + "\'" +date + "\'" +"and entrust_behavior=\'pay\'";
        Statement st = conn.createStatement();
        ResultSet presultSet = st.executeQuery(sql);

        rotation_info protate_count = new rotation_info();
        if (presultSet.next()) {
            protate_count.setQuantity_in(protate_count.getQuantity_in()+presultSet.getDouble("entrust_count"));

            protate_count.setAmount_in(presultSet.getDouble("entrust_amount"));
            protate_count.setTime(presultSet.getString("entrust_time"));
        }
        ResultSet bresultSet = st.executeQuery(sql);
        rotation_info brotate_count = new rotation_info();
        if (bresultSet.next()) {
            brotate_count.setQuantity_out(brotate_count.getQuantity_out()+presultSet.getDouble("entrust_count"));

            brotate_count.setAmount_out(presultSet.getDouble("entrust_amount"));
            brotate_count.setTime(presultSet.getString("entrust_time"));
        }


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
        String sql = "select * from entrust where entrust_account=" +"\'" + user_id + "\'" +"and stock_symbol=" + "\'" +stock_id + "\'" +"and entrust_date=" + "\'" +date + "\'" +"and entrust_behavior=\'pay\'";

        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);

        rotation_info rotate_count = new rotation_info();
        if (resultSet.next()) {
            rotate_count.setQuantity_in(rotate_count.getQuantity_in()+resultSet.getDouble("entrust_count"));

            rotate_count.setAmount_in(resultSet.getDouble("entrust_amount"));
            rotate_count.setTime(resultSet.getString("entrust_time"));
        }


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
        if(resultSet.next())
            num++;
        conn.close();
        return num;
    }

}
