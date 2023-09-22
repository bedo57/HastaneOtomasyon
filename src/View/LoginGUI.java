package View;

import Helper.*;
import Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginGUI extends JFrame {

    private JPanel w_pane;
    private JTabbedPane w_tabPane;
    private JPanel w_hastaLogin;
    private JTextField fld_hastaTc;
    private JPasswordField fld_hastaPass;
    private JTextField fld_doctorTc;
    private JPasswordField fld_doctorPass;

    private DBConnection conn = new DBConnection();

    private JLabel lbl_logo;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI frame = new LoginGUI();
                    frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginGUI(){
        setResizable(false);
        setTitle("Hastane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,500,400);
        w_pane = new JPanel();
        w_pane.setBackground(Color.white);
        w_pane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        lbl_logo = new JLabel(new ImageIcon(getClass().getResource("ilac.png")));
        lbl_logo.setBounds(206,11,63,55);
        w_pane.add(lbl_logo);

        JLabel lblNewLabel = new JLabel("Hastane Y\u00F6netim Sistemine Ho\u015Fgeldiniz");
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,17));
        lblNewLabel.setBounds(81,70,320,30);
        w_pane.add(lblNewLabel);

        JTabbedPane w_tabPane = new JTabbedPane(JTabbedPane.TOP);
        w_tabPane.setBounds(10,128,464,222);
        w_pane.add(w_tabPane);

        JPanel w_hastaLogin = new JPanel();
        w_hastaLogin.setBackground(Color.white);
        w_tabPane.addTab("Hasta Girişi", null, w_hastaLogin, null);
        w_hastaLogin.setLayout(null);

        fld_hastaTc = new JTextField();
        fld_hastaTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN,18));
        fld_hastaTc.setBounds(157,27,270,30);
        fld_hastaTc.setColumns(10);
        w_hastaLogin.add(fld_hastaTc);

        fld_hastaPass = new JPasswordField();
        fld_hastaPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN,18));
        fld_hastaPass.setBounds(157,77,270,30);
        fld_hastaPass.setColumns(10);
        w_hastaLogin.add(fld_hastaPass);

        JLabel label_1 = new JLabel("TC Numaranız:");
        label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,17));
        label_1.setBounds(22,27,160,30);
        w_hastaLogin.add(label_1);

        JLabel label_2 = new JLabel("\u015Eifre:");
        label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,17));
        label_2.setBounds(22,77,160,30);
        w_hastaLogin.add(label_2);

        JButton btn_kayitOL = new JButton("Kay\u0131t OL");
        btn_kayitOL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterGUI registerGUI = new RegisterGUI();
                registerGUI.setVisible(true);
                dispose();
            }
        });
        btn_kayitOL.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        btn_kayitOL.setBounds(24,136,195,47);
        w_hastaLogin.add(btn_kayitOL);

        JButton btnGirisYap = new JButton("Giri\u015f Yap");
        btnGirisYap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0){
                    Helper.showMsg("fill");
                }else {
                    boolean key = true;
                    try {
                        Connection connection = conn.connDB();
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery("Select * from hospital.user");
                        while (rs.next()){
                            if (fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
                                if (rs.getString("type").equals("hasta")){
                                    Hasta hasta = new Hasta();
                                    hasta.setId(rs.getInt("id"));
                                    hasta.setPassword("password");
                                    hasta.setName(rs.getString("name"));
                                    hasta.setTcno(rs.getString("tcno"));
                                    hasta.setType(rs.getString("type"));
                                    HastaGUI hastaGUI = new HastaGUI(hasta);
                                    hastaGUI.setVisible(true);
                                    dispose();
                                    key = false;
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (key){
                        Helper.showMsg("Böyle bir hasta bulunamadı");
                    }
                }
            }
        });
        btnGirisYap.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        btnGirisYap.setBounds(237,136,195,47);
        w_hastaLogin.add(btnGirisYap);

        JPanel w_doctorLogin = new JPanel();
        w_doctorLogin.setBackground(Color.WHITE);
        w_tabPane.addTab("Doktor Girişi", null, w_doctorLogin,null);
        w_doctorLogin.setLayout(null);

        fld_doctorTc = new JTextField();
        fld_doctorTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN,18));
        fld_doctorTc.setColumns(10);
        fld_doctorTc.setBounds(157,27,270,30);
        w_doctorLogin.add(fld_doctorTc);

        fld_doctorPass =new JPasswordField();
        fld_doctorPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN,18));
        fld_doctorPass.setColumns(10);
        fld_doctorPass.setBounds(157,77,270,30);
        w_doctorLogin.add(fld_doctorPass);

        JLabel label_3 = new JLabel("TC Numaranız:");
        label_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,17));
        label_3.setBounds(22,27,160,30);
        w_doctorLogin.add(label_3);

        JLabel label_4 = new JLabel("\u015Eifre:");
        label_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,17));
        label_4.setBounds(22,77,160,30);
        w_doctorLogin.add(label_4);


        JButton btn_doctorLogin = new JButton("Giri\u015f Yap");
        btn_doctorLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_doctorTc.getText().length()==0  || fld_doctorPass.getText().length()==0){
                    Helper.showMsg("fill");
                }else {
                    try {
                        Connection connection = conn.connDB();
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery("Select * from hospital.user");
                        while (rs.next()){
                            if (fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password"))) {
                                if (rs.getString("type").equals("bashekim")){
                                    Bashekim bashekim = new Bashekim();
                                    bashekim.setId(rs.getInt("id"));
                                    bashekim.setPassword("password");
                                    bashekim.setName(rs.getString("name"));
                                    bashekim.setTcno(rs.getString("tcno"));
                                    bashekim.setType(rs.getString("type"));
                                    BashekimGUI bGUI = new BashekimGUI(bashekim);
                                    bGUI.setVisible(true);
                                    dispose();
                                }
                                if (rs.getString("type").equals("doktor")){
                                    Doctor doctor = new Doctor();
                                    doctor.setId(rs.getInt("id"));
                                    doctor.setPassword("password");
                                    doctor.setName(rs.getString("name"));
                                    doctor.setTcno(rs.getString("tcno"));
                                    doctor.setType(rs.getString("type"));
                                    DoctorGUI dGUI = new DoctorGUI(doctor);
                                    dGUI.setVisible(true);
                                    dispose();
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        btn_doctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        btn_doctorLogin.setBounds(22,136,408,47);
        w_doctorLogin.add(btn_doctorLogin);

    }

}
