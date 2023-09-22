package Model;

import Helper.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class Appointment {
    private int id, doctorID, hastaID;
    private String doctorName, hastaName, app_date;

    DBConnection conn = new DBConnection();
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement =null;

    public Appointment() {
    }

    public Appointment(int id, int doctorID, int hastaID, String doctorName, String hastaName, String app_date) {
        this.id = id;
        this.doctorID = doctorID;
        this.hastaID = hastaID;
        this.doctorName = doctorName;
        this.hastaName = hastaName;
        this.app_date = app_date;
    }

    public ArrayList<Appointment> getHastaList(int hasta_id) throws SQLException {
        ArrayList<Appointment> list = new ArrayList<>();
        Appointment obj;
        Connection con = conn.connDB();
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM appointment WHERE hasta_id = " + hasta_id);
            while (rs.next()){
                obj = new Appointment();
                obj.setId(rs.getInt("id"));
                obj.setDoctorID(rs.getInt("doctor_id"));
                obj.setDoctorName(rs.getString("doctor_name"));
                obj.setHastaID(rs.getInt("hasta_id"));
                obj.setHastaName(rs.getString("hasta_name"));
                obj.setApp_date(rs.getString("app_date"));
                list.add(obj);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            st.close();
            rs.close();
            con.close();
        }
        return list;
    }

    public ArrayList<Appointment> getDoctorList(int doctor_id) throws SQLException {
        ArrayList<Appointment> list = new ArrayList<>();
        Appointment obj;
        Connection con = conn.connDB();
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = " + doctor_id);
            while (rs.next()){
                obj = new Appointment();
                obj.setId(rs.getInt("id"));
                obj.setDoctorID(rs.getInt("doctor_id"));
                obj.setDoctorName(rs.getString("doctor_name"));
                obj.setHastaID(rs.getInt("hasta_id"));
                obj.setHastaName(rs.getString("hasta_name"));
                obj.setApp_date(rs.getString("app_data"));
                list.add(obj);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            st.close();
            rs.close();
            con.close();
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getHastaID() {
        return hastaID;
    }

    public void setHastaID(int hastaID) {
        this.hastaID = hastaID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHastaName() {
        return hastaName;
    }

    public void setHastaName(String hastaName) {
        this.hastaName = hastaName;
    }

    public String getApp_date() {
        return app_date;
    }

    public void setApp_date(String app_date) {
        this.app_date = app_date;
    }
}
