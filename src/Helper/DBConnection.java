package Helper;
import java.sql.*;
public class DBConnection {
    Connection c = null;

    public DBConnection(){
    }

    public Connection connDB(){
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?user=root&password=bedo15995123");
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
