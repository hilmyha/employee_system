/*
 * Copyright (c) 2022. Hilmy Ahmad Haidar. All Right Reserved.
 */

import view.LoginForm;

import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        try {
            JFrame loginForm = new LoginForm();
            loginForm.setVisible(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
}
