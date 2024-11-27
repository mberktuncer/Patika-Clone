package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterStudentGUI extends JFrame{
    private JPanel panel1;
    private JTextField fld_full_name;
    private JTextField fld_user_name;
    private JPasswordField fld_student_password;
    private JPanel wrapper;
    private JButton btn_register;

    public RegisterStudentGUI(){
        add(wrapper);
        setSize(300,300);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        setResizable(false);
        Helper.setLayout();

        btn_register.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_full_name) || Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_student_password)){
                Helper.showMessage("fill");
            }
            else{
                String fullName = fld_user_name.getText();
                String userName = fld_user_name.getText();
                String password = fld_student_password.getText();
                User.add(fullName, userName, password, "student");
                Helper.showMessage("done");
                dispose();
            }
        });
    }
}
