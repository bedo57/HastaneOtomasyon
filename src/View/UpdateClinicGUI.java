package View;

import Helper.Helper;
import Model.Clinic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateClinicGUI extends JFrame {
    private JPanel w_pane;
    private JTextField fld_clinicName;
    private static Clinic clinic;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
                    frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public UpdateClinicGUI(Clinic clinic) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,225,150);
        w_pane = new JPanel();
        w_pane.setBackground(Color.white);
        w_pane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel label_1 = new JLabel("Polikilinik Adı");
        label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,16));
        label_1.setBounds(10,11,100,20);
        w_pane.add(label_1);

        fld_clinicName = new JTextField();
        fld_clinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,14));
        //fld_dName.setEnabled(false);
        fld_clinicName.setColumns(10);
        fld_clinicName.setBounds(10,34,189,29);
        fld_clinicName.setText(clinic.getName());
        w_pane.add(fld_clinicName);

        JButton btn_updateClinic = new JButton("Düzenle");
        btn_updateClinic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")){
                    try {
                        clinic.updateClinic(clinic.getId(),fld_clinicName.getText());
                        Helper.showMsg("success");
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btn_updateClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN,15));
        btn_updateClinic.setBounds(10,65,189,35);
        w_pane.add(btn_updateClinic);
    }
}
