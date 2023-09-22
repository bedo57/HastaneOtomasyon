package View;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class HastaGUI extends JFrame {
    private static Hasta hasta = new Hasta();
    private Clinic clinic = new Clinic();
    private Whour whour = new Whour();
    private Appointment appointment = new Appointment();
    private JPanel w_pane;
    private JPanel w_appointment;
    private JTabbedPane w_tab;
    private JTable table_doctor;
    private JTable table_whour;
    private JTable table_appoint;
    private DefaultTableModel doctorModel;
    private DefaultTableModel whourModel;
    private DefaultTableModel appointModel;
    private Object[] doctorData = null;
    private Object[] whourData = null;
    private Object[] appointData = null;
    private int selectDoctorID = 0;
    private String selectDoctorName = null;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HastaGUI frame = new HastaGUI(hasta);
                    frame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public HastaGUI(Hasta hasta) throws SQLException {
        //doctor model
        doctorModel = new DefaultTableModel();
        Object[] colDoctor = new Object[2];
        colDoctor[0] = "ID";
        colDoctor[1] = "Ad Soyad";
        doctorModel.setColumnIdentifiers(colDoctor);
        doctorData = new Object[2];

        //whour model
        whourModel = new DefaultTableModel();
        Object[] colWhour = new Object[2];
        colWhour[0] = "ID";
        colWhour[1] = "Tarih";
        whourModel.setColumnIdentifiers(colWhour);
        whourData = new Object[2];

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


        setTitle("Hastane Yönetim Sistemi");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,750,500);
        w_pane = new JPanel();
        w_pane.setBackground(Color.white);
        w_pane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + hasta.getName());
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        lblNewLabel.setBounds(10,11,291,24);
        w_pane.add(lblNewLabel);

        JButton cikisYap = new JButton("Çıkış Yap");
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

        w_tab = new JTabbedPane(JTabbedPane.TOP);
        w_tab.setBounds(10,44,714,408);
        w_pane.add(w_tab);

        w_appointment = new JPanel();
        w_appointment.setBackground(Color.WHITE);
        w_tab.addTab("Randevu Sistemi",null,w_appointment,null);
        w_appointment.setLayout(null);

        JLabel label_1 = new JLabel("Doktor Listesi");
        label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        label_1.setBounds(10,11,100,20);
        w_appointment.add(label_1);

        JScrollPane w_scrollDoctor = new JScrollPane();
        w_scrollDoctor.setBounds(10,35,280,330);
        w_appointment.add(w_scrollDoctor);

        table_doctor = new JTable(doctorModel);
        w_scrollDoctor.setViewportView(table_doctor);
        table_doctor.getColumnModel().getColumn(0).setPreferredWidth(5);

        JLabel label_2 = new JLabel("Polikilinik Adı");
        label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        label_2.setBounds(299,11,100,20);
        w_appointment.add(label_2);

        JComboBox select_clinic = new JComboBox();
        select_clinic.setBounds(299,40,150,30);
        select_clinic.addItem("Polikilinik Seç");
        for (int i=0; i<clinic.getList().size(); i++){
            select_clinic.addItem(new Item(clinic.getList().get(i).getId(),clinic.getList().get(i).getName()));
        }
        select_clinic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (select_clinic.getSelectedIndex() != 0){
                    JComboBox c = (JComboBox) e.getSource();
                    Item item = (Item) c.getSelectedItem();
                    DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
                    clearModel.setRowCount(0);
                    try {
                        for (int i=0; i<clinic.getClinicDoctorList(item.getKey()).size(); i++){
                            doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
                            doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
                            doctorModel.addRow(doctorData);
                        }
                    } catch (SQLException e1){
                        e1.printStackTrace();
                    }
                }else {
                    DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
                    clearModel.setRowCount(0);
                }
            }
        });
        w_appointment.add(select_clinic);

        JLabel label_3 = new JLabel("Doktor Seç");
        label_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        label_3.setBounds(300,122,100,20);
        w_appointment.add(label_3);

        JButton btn_selDoctor = new JButton("Seç");
        btn_selDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table_doctor.getSelectedRow();
                if (row >= 0){
                    String value = table_doctor.getModel().getValueAt(row,0).toString();
                    int id = Integer.parseInt(value);
                    DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
                    clearModel.setRowCount(0);
                    try {
                        for (int i=0; i<whour.getWhourList(id).size(); i++){
                            whourData[0] = whour.getWhourList(id).get(i).getId();
                            whourData[1] = whour.getWhourList(id).get(i).getWdate();
                            whourModel.addRow(whourData);
                        }
                    }catch (SQLException e1){
                        e1.printStackTrace();
                    }
                    table_whour.setModel(whourModel);
                    selectDoctorID = id;
                    selectDoctorName = table_doctor.getModel().getValueAt(row,1).toString();
                }else {
                    Helper.showMsg("Lütfen bir doktor seçiniz");
                }
            }
        });
        btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        btn_selDoctor.setBounds(300,147,150,30);
        w_appointment.add(btn_selDoctor);

        JLabel label_4 = new JLabel("Randevu");
        label_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        label_4.setBounds(300,195,100,20);
        w_appointment.add(label_4);

        JButton btn_addAppoint = new JButton("Randevu Al");
        btn_addAppoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = table_whour.getSelectedRow();
                if (selRow >= 0){
                    String date = table_whour.getModel().getValueAt(selRow,1).toString();
                    try {
                        boolean control = hasta.addApointment(selectDoctorID, hasta.getId(),selectDoctorName, hasta.getName(), date);
                        if (control){
                            Helper.showMsg("success");
                            hasta.updateWhourStatus_p(selectDoctorID,date);
                            updateWhourModel(selectDoctorID);
                            updateAppointModel(hasta.getId());
                        }else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    Helper.showMsg("Lütfen geçerli bir tarih seçiniz");
                }
            }
        });
        btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        btn_addAppoint.setBounds(300,220,150,30);
        w_appointment.add(btn_addAppoint);

        JLabel label_5 = new JLabel("Uygun Saatler");
        label_5.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        label_5.setBounds(469,11,100,20);
        w_appointment.add(label_5);

        JScrollPane w_scrollWhour = new JScrollPane();
        w_scrollWhour.setBounds(469,35,230,330);
        w_appointment.add(w_scrollWhour);

        table_whour = new JTable(whourModel);
        w_scrollWhour.setViewportView(table_whour);
        table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);

        JPanel w_appoint = new JPanel();
        w_appoint.setBackground(Color.WHITE);
        w_tab.addTab("Randevular" ,null,w_appoint,null);
        w_appoint.setLayout(null);

        JScrollPane w_scrollAppoint = new JScrollPane();
        w_scrollAppoint.setBounds(10,11,689,336);
        w_appoint.add(w_scrollAppoint);

        table_appoint = new JTable(appointModel);
        table_appoint.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = table_appoint.rowAtPoint(point);
                table_appoint.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });
        w_scrollAppoint.setViewportView(table_appoint);

        JButton btn_delAppoint = new JButton("Randevu Sil");
        btn_delAppoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = table_appoint.getSelectedRow();
                if (selRow >= 0) {
                    String value = table_appoint.getModel().getValueAt(selRow,0).toString();
                    int id = Integer.parseInt(value);
                    String date = table_appoint.getModel().getValueAt(selRow, 2).toString();
                    try {
                        boolean control = hasta.deleteAppoint(id,date);
                        if (control){
                            Helper.showMsg("success");
                            hasta.updateWhourStatus_a(selectDoctorID,date);
                            updateWhourModel(id);
                            updateAppointModel(hasta.getId());
                        } else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    Helper.showMsg("Lütfen geçerli bir tarih seçiniz");
                }
            }
        });
        btn_delAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        btn_delAppoint.setBounds(550,348,150,30);
        w_appoint.add(btn_delAppoint);
    }


    public void updateWhourModel (int doctor_id) throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
        clearModel.setRowCount(0);
        for (int i=0; i<whour.getWhourList(selectDoctorID).size(); i++){
            whourData[0] = whour.getWhourList(selectDoctorID).get(i).getId();
            whourData[1] = whour.getWhourList(selectDoctorID).get(i).getWdate();
            whourModel.addRow(whourData);
        }
    }
    public void updateAppointModel (int hasta_id) throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
        clearModel.setRowCount(0);
        for (int i=0; i<appointment.getHastaList(hasta_id).size(); i++){
            appointData[0] = appointment.getHastaList(hasta_id).get(i).getId();
            appointData[1] = appointment.getHastaList(hasta_id).get(i).getDoctorName();
            appointData[2] = appointment.getHastaList(hasta_id).get(i).getApp_date();
            appointModel.addRow(appointData);
        }
    }
}
