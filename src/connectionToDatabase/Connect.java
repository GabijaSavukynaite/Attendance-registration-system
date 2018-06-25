package connectionToDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","");
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
