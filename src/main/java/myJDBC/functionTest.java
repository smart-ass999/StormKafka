package myJDBC;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static myJDBC.JDBCUtils.getConnection;

public class functionTest {
    @Test
    public void CounBy_Userid_stockid() throws Exception {
        String user_id = "1";
        String stock_id = "600001";
        Connection conn = getConnection();
        String sql="select count(*) from entrust where entrust_account="+"\'" + user_id + "\'" +"and stock_symbol="+"\'" +stock_id+"\'" +"and entrust_state='T'";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int num=0;
        if(resultSet.next())
            num = resultSet.getInt("count");
        conn.close();
        System.out.println(num);

    }
    @Test
    public void CounBy_Userid_stockid_time() throws Exception {
        Connection conn = getConnection();
        String user_id = "1";
        String stock_id = "600001";
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

        System.out.println(flag);
    }
    @Test
    public void selectBy_Userid_stockid_date1() throws Exception {
        Connection conn = getConnection();
        String user_id = "1";
        String stock_id = "600001";
        String date = "01/01/2015";
        int flag = 0;
        String sql = "select * from entrust where entrust_account=" +"\'" + user_id + "\'" +"and stock_symbol=" + "\'" +stock_id + "\'" +"and entrust_date=" + "\'" +date + "\'" +"and entrust_behavior='buy'";
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
            System.out.println(protate_count.toString());
        }
        else{
            System.out.println(brotate_count.toString());
        }
    }

}
