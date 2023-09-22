package View;

import Helper.Helper;
import Model.Appointment;
import Model.Doctor;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class DoctorGUI extends JFrame{
    private JPanel w_pane;
    private JPanel w_whour;
    private JPanel w_appointment;
    private JScrollPane w_scrollAppoint;
    private JComboBox selecet_time;
    private JTable table_whour;
    private JTable table_appoint;
    private DefaultTableModel whourModel;
    private DefaultTableModel appointModel;
    private Object[] whourData = null;
    private Object[] appointData = null;
    private static Doctor doctor = new Doctor();
    private Appointment appointment = new Appointment();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    DoctorGUI frame = new DoctorGUI(doctor);
                    frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public DoctorGUI(Doctor doctor) throws SQLException {
        // Whour Model
        whourModel = new DefaultTableModel();
        Object[] colWhour = new Object[2];
        colWhour[0] = "ID";
        colWhour[1] = "Tarih";
        whourModel.setColumnIdentifiers(colWhour);
        whourData = new Object[2];
        for (int i = 0; i<doctor.getWhourList(doctor.getId()).size(); i++){
            whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
            whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
            whourModel.addRow(whourData);
        }
/*
        //appointment Model
        appointModel = new DefaultTableModel();
        Object[] colAppoint = new Object[3];
        colAppoint[0] = "ID";
        colAppoint[1] = "Doktor";
        colAppoint[2] = "Tarih";
        appointModel.setColumnIdentifiers(colAppoint);
        appointData = new Object[3];
        for (int i=0; i<appointment.getHastaList(hasta.getId()).size(); i++){
            appointData[0] = appointment.getHastaList(hasta.getId()).get(i).getId();
            appointData[1] = appointment.getHastaList(hasta.getId()).get(i).getDoctorName();
            appointData[2] = appointment.getHastaList(hasta.getId()).get(i).getApp_date();
            selectDoctorID = appointment.getHastaList(hasta.getId()).get(i).getDoctorID();
            appointModel.addRow(appointData);
        }
*/
        setTitle("Hastane Yönetim Sistemi");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,750,500);
        w_pane = new JPanel();
        w_pane.setBackground(Color.white);
        w_pane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Ho\u015Fgeldiniz, Say\u0131n " + doctor.getName());
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        lblNewLabel.setBounds(10,11,291,24);
        w_pane.add(lblNewLabel);

        JButton cikisYap = new JButton("\u00C7\u0131k\u0131\u015F Yap");
        cikisYap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                dispose();
            }
        });
        cikisYap.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,11));
        cikisYap.setBounds(616,15,108,24);
        w_pane.add(cikisYap);

        JTabbedPane w_tabPane = new JTabbedPane(JTabbedPane.TOP);
        w_tabPane.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        w_tabPane.setBounds(10,44,714,404);
        w_pane.add(w_tabPane);

        w_whour = new JPanel();
        w_whour.setBackground(Color.white);
        w_tabPane.addTab("Çalışma Saatleri", null, w_whour, null);
        w_whour.setLayout(null);

        JDateChooser select_date = new JDateChooser();
        select_date.setBounds(10,11,130,20);
        w_whour.add(select_date);

        selecet_time = new JComboBox();
        selecet_time.setModel(new DefaultComboBoxModel(new String[] {"10:00","10:30","11:00","11:30","12:00","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00",}));
        selecet_time.setBounds(150,11,65,20);
        w_whour.add(selecet_time);

        JButton addWhour = new JButton("Ekle");
        addWhour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                String date = "";
                try {
                    date = sdf.format(select_date.getDate());
                } catch (Exception e1){
                    e1.printStackTrace();
                }
                if (date.length() == 0){
                    Helper.showMsg("Lütfen geçerli bir tarih girin");
                } else {
                    String time = " " + selecet_time.getSelectedItem().toString() + ":00";
                    String selectDate = date + time;
                    try {
                        boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
                        if (control){
                            Helper.showMsg("success");
                            updateWhourModel(doctor);
                        } else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        addWhour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,11));
        addWhour.setBounds(225,11,70,20);
        w_whour.add(addWhour);

        JButton delWhour = new JButton("Sil");
        delWhour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = table_whour.getSelectedRow();
                if (selRow >= 0){
                    String selectRow = table_whour.getModel().getValueAt(selRow,0).toString();
                    int selID = Integer.parseInt(selectRow);
                    boolean control;
                    try {
                        control = doctor.deleteWhour(selID);
                        if (control){
                            Helper.showMsg("success");
                            updateWhourModel(doctor);
                        }else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }else {
                    Helper.showMsg("Lütfen bir tarih seçiniz !");
                }
            }
        });
        delWhour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,11));
        delWhour.setBounds(619,11,70,20);
        w_whour.add(delWhour);

        JScrollPane w_scrollWhour = new JScrollPane();
        w_scrollWhour.setBounds(5,44,700,322);
        w_whour.add(w_scrollWhour);

        table_whour = new JTable(whourModel);
        w_scrollWhour.setViewportView(table_whour);
/*
        w_appointment = new JPanel();
        w_appointment.setBackground(Color.WHITE);
        w_tabPane.addTab("Randevular",null,w_appointment,null);
        w_appointment.setLayout(null);

        w_scrollAppoint = new JScrollPane();
        w_scrollAppoint.setBounds(10,11,689,336);
        w_appointment.add(w_scrollAppoint);

        table_appoint = new JTable();
        w_scrollAppoint.setViewportView(table_appoint);
 */
    }

    public void updateWhourModel (Doctor doctor) throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
        clearModel.setRowCount(0);
        for (int i=0; i<doctor.getWhourList(doctor.getId()).size(); i++){
            whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
            whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
            whourModel.addRow(whourData);
        }
    }

}
