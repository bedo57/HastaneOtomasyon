package View;

import Helper.Helper;
import Model.Hasta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterGUI extends JFrame {
    private JPanel w_pane;
    private JTextField fld_name;
    private JTextField fld_tcno;
    private JPasswordField fld_password;
    private Hasta hasta =new Hasta();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    RegisterGUI frame = new RegisterGUI();
                    frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public RegisterGUI() {
        setTitle("Hastane Yönetim Sistemi");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,300,300);
        w_pane = new JPanel();
        w_pane.setBackground(Color.white);
        w_pane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel label_1 = new JLabel("Ad Soyad");
        label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_1.setBounds(10,5,264,27);
        w_pane.add(label_1);

        fld_name = new JTextField();
        fld_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_name.setColumns(10);
        fld_name.setBounds(10,30,264,27);
        w_pane.add(fld_name);

        JLabel label_2 = new JLabel("T.C Numarası");
        label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_2.setBounds(10,60,264,27);
        w_pane.add(label_2);

        fld_tcno = new JTextField();
        fld_tcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_tcno.setColumns(10);
        fld_tcno.setBounds(10,85,264,27);
        w_pane.add(fld_tcno);

        JLabel label_3 = new JLabel("Şifre");
        label_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        label_3.setBounds(10,115,264,27);
        w_pane.add(label_3);

        fld_password = new JPasswordField();
        fld_password.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        fld_password.setColumns(10);
        fld_password.setBounds(10,140,264,27);
        w_pane.add(fld_password);

        JButton btn_register = new JButton("Kayıt Ol");
        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_tcno.getText().length()==0 || fld_password.getText().length()==0 || fld_name.getText().length()==0){
                    Helper.showMsg("fill");
                }else {
                    try {
                        boolean control = hasta.register(fld_tcno.getText(),fld_password.getText(),fld_name.getText());
                        if (control){
                            Helper.showMsg("success");
                            LoginGUI login = new LoginGUI();
                            login.setVisible(true);
                            dispose();
                        }else {
                            Helper.showMsg("error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        btn_register.setBounds(10,180,264,35);
        w_pane.add(btn_register);

        JButton btn_backto = new JButton("Geri Dön");
        btn_backto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI login = new LoginGUI();
                login.setVisible(true);
                dispose();
            }
        });
        btn_backto.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        btn_backto.setBounds(10,220,264,35);
        w_pane.add(btn_backto);
    }
}
