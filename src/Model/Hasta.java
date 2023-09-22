package Model;

import Helper.Helper;

import java.sql.*;

public class Hasta extends User {
    Connection con = conn.connDB();
    PreparedStatement preparedStatement = null;
    Statement st = null;
    ResultSet rs = null;

    public Hasta() {
    }

    public Hasta(int id, String tcno, String name, String password, String type) {
        super(id, tcno, name, password, type);
    }

    public boolean register(String tcno, String password, String name) throws SQLException {
        int key = 0;
        boolean duplicate = false;
        String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM user WHERE tcno= '" + tcno + "'");
            while (rs.next()) {
                duplicate = true;
                Helper.showMsg("Bu TC Numarasına ait kayıt bulunmaktadır");
                break;
            }
            if (!duplicate) {
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, tcno);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, "hasta");
                preparedStatement.executeUpdate();
                key = 1;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addApointment(int doctorID, int hastaID, String doctorName, String hastaName, String app_date) throws SQLException {
        int key = 0;
        String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES" + "(?,?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, doctorName);
            preparedStatement.setInt(3, hastaID);
            preparedStatement.setString(4, hastaName);
            preparedStatement.setString(5,app_date);
            preparedStatement.executeUpdate();
            key = 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean deleteAppoint(int id, String app_date) throws SQLException {
        String query = "DELETE FROM appointment WHERE id = ? AND app_date = ?";
        boolean key = false;
        try {
            st = con.createStatement();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,app_date);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        if (key)
            return true;
        else
            return false;
    }
    public boolean updateWhourStatus_p(int doctorID,String wdate) throws SQLException {
        int key = 0;
        String query = "UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,"p");
            preparedStatement.setInt(2, doctorID);
            preparedStatement.setString(3,wdate);
            preparedStatement.executeUpdate();
            key = 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updateWhourStatus_a(int doctorID,String wdate) throws SQLException {
        int key = 0;
        String query = "UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,"a");
            preparedStatement.setInt(2, doctorID);
            preparedStatement.setString(3,wdate);
            preparedStatement.executeUpdate();
            key = 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }
    }
}
