package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    public EducatorGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
    }
}
