package com.patikadev.helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static int screenCenter(String axis, Dimension size){
        int point = 0;
        switch (axis){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField jTextField){
        return jTextField.getText().trim().isEmpty();
    }

    public static void showMessage(String s){
        String message;
        String title;
        switch (s){
            case "fill":
                message = "Please fill in all fields";
                title = "Error";
                break;
            case "done":
                message = "Transaction successful";
                title = "Result";
                break;
            case "error":
                message = "An error occurred";
                title = "Error";
                break;
            default:
                message = s;
                title = "";
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        String msg;
        switch (str){
            case "sure":
                msg = "Are you sure ?";
                break;
            default:
                msg = str;
                break;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Last Decision?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPaneTR(){
        UIManager.put("OptionPane.okButtonText", "OK");
    }
}
