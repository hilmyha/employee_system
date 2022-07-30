/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

package view;

import dao.AdminDao;
import entity.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField tfUname;
    private JPasswordField pfPassword;
    private JButton loginButton;
    private JPanel mainPanel;

    private AdminDao adminDao;



    public LoginForm() {
        super("Login");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200, 300);
        this.setLocationRelativeTo(null);

        adminDao = new AdminDao();

        loginButton.addActionListener(e -> {
            if (
                    tfUname.getText().trim().isEmpty() || pfPassword.getText().trim().isEmpty()
            ) {
                JOptionPane.showMessageDialog(mainPanel, "Username dan Password kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Admin admin = new Admin();
                admin.setName(tfUname.getText());
                admin.setPass(pfPassword.getText());
                try {
                    if (adminDao.loginGet(admin) == true) {
                        JOptionPane.showMessageDialog(mainPanel, "Login berhasil");
                        this.dispose();
                        JFrame eMS = new EmployeeManagement();
                        eMS.setVisible(true);
                    } else {
                        resetLoginForm();
                        JOptionPane.showMessageDialog(mainPanel, "Login gagal");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void resetLoginForm() {
        tfUname.setText("");
        pfPassword.setText("");
    }
}
