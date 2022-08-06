package myJDBC;

import myKafka.jsonProducer;
import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static myJDBC.JDBCUtils.InsertEntrustList;
import static myJDBC.JDBCUtils.getConnection;

public class functionTest {
    @Test
    public void insert() throws Exception {
        File file = new File(jsonProducer.class.getClassLoader().getResource("data.json").getFile());
        String data = "";
        try {
            data = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InsertEntrustList(data);
    }
    @Test
    public void delete() throws Exception {
        Connection conn = getConnection();
        String selectSQL = "delete from result";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(selectSQL);
        int num=0;
        while (resultSet.next())
            num++;
        conn.close();
        System.out.println(num);
    }
}

