package View;

import Helper.*;
import Model.Bashekim;
import Model.Clinic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;


public class BashekimGUI extends JFrame {

    static Bashekim bashekim = new Bashekim();
    static Clinic clinic =new Clinic();
    private JPanel w_pane;
    private JTextField fld_dName;
    private JTextField fld_dTcno;
    private JTextField fld_dPass;
    private JTextField fld_docID;
    private JTextField fld_clinicName;
    private JTable table_doctor;
    private JTable table_clinic;
    private JTable table_worker;
    private JPopupMenu clinicMenu;
    private JComboBox select_doctor;
    private DefaultTableModel doctorModel = null;
    private DefaultTableModel clinicModel = null;
    private DefaultTableModel workerModel = null;
    private Object[] doctorData = null;
    private Object[] clinicData = null;
    private Object[] workerData = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BashekimGUI frame = new BashekimGUI(bashekim);
                    frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public BashekimGUI(Bashekim bashekim) throws SQLException {
        //Doctor Model
        doctorModel = new DefaultTableModel();
        Object[] colDoctorName = new Object[4];
        colDoctorName[0] = "ID";
        colDoctorName[1] = "Ad Soyad";
        colDoctorName[2] = "TC No";
        colDoctorName[3] = "Şifre";
        doctorModel.setColumnIdentifiers(colDoctorName);
        doctorData = new Object[4];
        for (int i=0; i<bashekim.getDoctorList().size(); i++){
            doctorData[0] = bashekim.getDoctorList().get(i).getId();
            doctorData[1] = bashekim.getDoctorList().get(i).getName();
            doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
            doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
            doctorModel.addRow(doctorData);
        }
        //Clinic Model
        clinicModel = new DefaultTableModel();
        Object[] colClinicName = new Object[2];
        colClinicName[0] = "ID";
        colClinicName[1] = "Polikinilik Adı";
        clinicModel.setColumnIdentifiers(colClinicName);
        clinicData = new Object[2];
        for (int i=0; i<clinic.getList().size(); i++){
            clinicData[0] = clinic.getList().get(i).getId();
            clinicData[1] = clinic.getList().get(i).getName();
            clinicModel.addRow(clinicData);
        }
        //Worker Model
        workerModel = new DefaultTableModel();
        Object[] colWorker = new Object[2];
        colWorker[0] = "ID";
        colWorker[1] = "Ad Soyad";
        workerModel.setColumnIdentifiers(colWorker);
        workerData = new Object[2];


        setTitle("Hastane Y\u00F6netim Sistemi");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,750,500);
        w_pane = new JPanel();
        w_pane.setBackground(Color.white);
        w_pane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Ho\u015Fgeldiniz, Say\u0131n " + bashekim.getName());
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
        w_tabPane.setBounds(10,44,711,399);
        w_pane.add(w_tabPane);

        JPanel w_doctor = new JPanel();
        w_doctor.setBackground(Color.white);
        w_tabPane.addTab("Doktor Yönetimi", null, w_doctor, null);
        w_doctor.setLayout(null);

        JLabel label_1 = new JLabel("Ad Soyad");
        label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_1.setBounds(500,10,120,20);
        w_doctor.add(label_1);

        fld_dName = new JTextField();
        fld_dName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_dName.setColumns(10);
        fld_dName.setBounds(500,35,185,27);
        w_doctor.add(fld_dName);

        JLabel label_2 = new JLabel("TC No");
        label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_2.setBounds(500,80,120,20);
        w_doctor.add(label_2);

        fld_dTcno = new JTextField();
        fld_dTcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_dTcno.setColumns(10);
        fld_dTcno.setBounds(500,105,185,27);
        w_doctor.add(fld_dTcno);

        JLabel label_3 = new JLabel("Şifre");
        label_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_3.setBounds(500,150,120,20);
        w_doctor.add(label_3);

        fld_dPass = new JTextField();
        fld_dPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_dPass.setColumns(10);
        fld_dPass.setBounds(500,175,185,27);
        w_doctor.add(fld_dPass);

        JButton addDoctor = new JButton("Ekle");
        addDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fld_dName.getText().length()==0 || fld_dPass.getText().length()==0 || fld_dTcno.getText().length()==0){
                    Helper.showMsg("fill");
                } else {
                    try {
                        boolean control = bashekim.addDoctor(fld_dTcno.getText(), fld_dName.getText(), fld_dPass.getText());
                        if (control){
                            Helper.showMsg("success");
                            fld_dTcno.setText(null);
                            fld_dName.setText(null);
                            fld_dPass.setText(null);
                            updateDoctorModel();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        addDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        addDoctor.setBounds(500,210,185,33);
        w_doctor.add(addDoctor);

        JLabel label_4 = new JLabel("Kullanıcı ID");
        label_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_4.setBounds(500,265,120,20);
        w_doctor.add(label_4);

        fld_docID = new JTextField();
        fld_docID.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_docID.setColumns(10);
        fld_docID.setBounds(500,290,185,27);
        w_doctor.add(fld_docID);

        JButton delDoctor = new JButton("Sil");
        delDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_docID.getText().length() == 0){
                    Helper.showMsg("Lütfen bir doktor seçiniz");
                } else {
                    if (Helper.confirm("sure")){
                        int selectID = Integer.parseInt(fld_docID.getText());
                        try {
                            boolean control = bashekim.deleteDoctor(selectID);
                            if (control){
                                Helper.showMsg("success");
                                fld_docID.setText(null);
                                updateDoctorModel();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        delDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        delDoctor.setBounds(500,325,185,33);
        w_doctor.add(delDoctor);

        JScrollPane w_scrollDoctor = new JScrollPane();
        w_scrollDoctor.setBounds(10,12,480,343);
        w_doctor.add(w_scrollDoctor);

        table_doctor = new JTable(doctorModel);
        w_scrollDoctor.setViewportView(table_doctor);
        table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    fld_docID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
                } catch (Exception e1){
                }
            }
        });
        table_doctor.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE){
                    int selectID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(),0).toString());
                    String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(),1).toString();
                    String selectTCno = table_doctor.getValueAt(table_doctor.getSelectedRow(),2).toString();
                    String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(),3).toString();

                    try {
                        bashekim.updateDoctor(selectID, selectName, selectTCno, selectPass);
                    } catch (SQLException ex) {
                            ex.printStackTrace();
                    }
                }
            }
        });

        JPanel w_clinic = new JPanel();
        w_clinic.setBackground(Color.WHITE);
        w_tabPane.addTab("Polikinikler",null, w_clinic,null);
        w_clinic.setLayout(null);

        JScrollPane w_scrollClinic = new JScrollPane();
        w_scrollClinic.setBounds(10,11,260,350);
        w_clinic.add(w_scrollClinic);

        clinicMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        clinicMenu.add(updateMenu);
        clinicMenu.add(deleteMenu);

        updateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());
                Clinic selectClinic = clinic.getFetch(selectedID);
                UpdateClinicGUI updateClinicGUI = new UpdateClinicGUI(selectClinic);
                updateClinicGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                updateClinicGUI.setVisible(true);
                updateClinicGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            updateClinicModel();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")){
                    int selectedID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());
                    try {
                        if (clinic.deleteClinic(selectedID)){
                            Helper.showMsg("success");
                            updateClinicModel();
                        } else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        table_clinic = new JTable(clinicModel);
        table_clinic.setComponentPopupMenu(clinicMenu);
        table_clinic.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = table_clinic.rowAtPoint(point);
                table_clinic.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });
        w_scrollClinic.setViewportView(table_clinic);

        JLabel label_5 = new JLabel("Polikilinik Adı");
        label_5.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_5.setBounds(280,11,100,20);
        w_clinic.add(label_5);

        fld_clinicName = new JTextField();
        fld_clinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_clinicName.setColumns(10);
        fld_clinicName.setBounds(280,34,159,27);
        w_clinic.add(fld_clinicName);

        JButton addClinic = new JButton("Ekle");
        addClinic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_clinicName.getText().length() == 0){
                    Helper.showMsg("fill");
                } else {
                    try {
                        if (clinic.addClinic(fld_clinicName.getText())){
                            Helper.showMsg("success");
                            fld_clinicName.setText(null);
                            updateClinicModel();
                        }
                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        addClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        addClinic.setBounds(280,70,159,30);
        w_clinic.add(addClinic);

        JLabel label_6 = new JLabel("Polikilinik Adı");
        label_6.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_6.setBounds(280,135,100,20);
        w_clinic.add(label_6);

        JButton workerSelect = new JButton("Seç");
        workerSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = table_clinic.getSelectedRow();
                if (selRow >= 0){
                    String selClinic = table_clinic.getModel().getValueAt(selRow,0).toString();
                    int selClinicID = Integer.parseInt(selClinic);
                    DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
                    clearModel.setRowCount(0);
                    try {
                        for (int i=0; i<bashekim.getClinicDoctorList(selClinicID).size(); i++){
                            workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
                            workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
                            workerModel.addRow(workerData);
                        }
                    } catch (SQLException e1){
                        e1.printStackTrace();
                    }
                    table_worker.setModel(workerModel);
                } else{
                    Helper.showMsg("Lütfen bir polikilinik seçiniz !");
                }
            }
        });
        workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        workerSelect.setBounds(280,160,159,30);
        w_clinic.add(workerSelect);


        select_doctor = new JComboBox();
        select_doctor.setBounds(280,263,159,35);
        for (int i =0; i<bashekim.getDoctorList().size(); i++){
            select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
        }
        select_doctor.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            Item item = (Item) c.getSelectedItem();
        });
        w_clinic.add(select_doctor);

        JButton addWorker = new JButton("Ekle");
        addWorker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = table_clinic.getSelectedRow();
                if (selRow >= 0){
                    String selClinic = table_clinic.getModel().getValueAt(selRow,0).toString();
                    int selClinicID = Integer.parseInt(selClinic);
                    Item doctorItem = (Item) select_doctor.getSelectedItem();
                    try {
                        boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
                        if (control){
                            Helper.showMsg("success");
                            DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
                            clearModel.setRowCount(0);
                            for (int i=0; i<bashekim.getClinicDoctorList(selClinicID).size(); i++){
                                workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
                                workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
                                workerModel.addRow(workerData);
                            }
                            table_worker.setModel(workerModel);
                        } else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Helper.showMsg("Lütfen bir polikilinik seçiniz !");
                }
            }
        });
        addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        addWorker.setBounds(280,309,159,30);
        w_clinic.add(addWorker);

        JScrollPane w_scrollWorker = new JScrollPane();
        w_scrollWorker.setBounds(450,11,249,350);
        w_clinic.add(w_scrollWorker);

        table_worker = new JTable();
        w_scrollWorker.setViewportView(table_worker);

    }

    public void updateDoctorModel (){
        DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
        clearModel.setRowCount(0);
        for (int i=0; i<bashekim.getDoctorList().size(); i++){
            doctorData[0] = bashekim.getDoctorList().get(i).getId();
            doctorData[1] = bashekim.getDoctorList().get(i).getName();
            doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
            doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
            doctorModel.addRow(doctorData);
        }
    }
    public void updateClinicModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
        clearModel.setRowCount(0);
        for (int i=0; i<clinic.getList().size(); i++){
            clinicData[0] = clinic.getList().get(i).getId();
            clinicData[1] = clinic.getList().get(i).getName();
            clinicModel.addRow(clinicData);
        }
    }
}
