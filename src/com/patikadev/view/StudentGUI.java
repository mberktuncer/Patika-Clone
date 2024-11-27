package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.helper.Item;
import com.patikadev.model.Course;
import com.patikadev.model.CourseComment;
import com.patikadev.model.Patika;
import com.patikadev.model.RegisteredCourse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JButton btn_register;
    private JComboBox cmb_patika_list;
    private JComboBox cmb_course_list;
    private JComboBox cmb_course_rev;
    private JTextArea txt_comment;
    private JButton btn_save_comment;
    private JTable tbl_patika_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private int patikaId;
    private int studentId;

    public StudentGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        Helper.setLayout();

        loadPatikaCombobox();
        loadAllCourseCombobox();

        cmb_patika_list.addActionListener(e -> {
            int selectedPatikaId = ((Item) cmb_patika_list.getSelectedItem()).getKey();
            patikaId = selectedPatikaId;
            loadCourseCombobox(selectedPatikaId);
        });
        btn_register.addActionListener(e -> {
            Item course = (Item) cmb_course_list.getSelectedItem();
            if (course == null){
                Helper.showMessage("fill");
            }
            else{
                if (Helper.confirm(course.toString() + "Are you sure that you want to register ?")){
                    if (RegisteredCourse.add(course.getKey(), studentId)){
                        Helper.showMessage("done");
                    }
                }
            }
        });
        btn_save_comment.addActionListener(e -> {
            Item course = (Item) cmb_course_rev.getSelectedItem();
            String comment = txt_comment.getText();
            if (course == null || comment.isEmpty()){
                Helper.showMessage("fill");
            }
            else {
                if (Helper.confirm("Are you sure that you want to comment ?")){
                    if (CourseComment.add(course.getKey(), comment)){
                        Helper.showMessage("done");
                    }
                }
            }
        });
    }

    public void loadPatikaCombobox(){
        cmb_patika_list.removeAllItems();
        for (Patika patika : Patika.getList()){
            cmb_patika_list.addItem(new Item(patika.getId(), patika.getName()));
        }
    }

    public void loadAllCourseCombobox(){
        cmb_course_rev.removeAllItems();
        for (Course course : Course.getList()){
            cmb_course_rev.addItem(new Item(course.getId(), course.getName()));
        }
    }

    public void loadCourseCombobox(int patikaId){
        cmb_course_list.removeAllItems();

        ArrayList<Course> courses = Course.getListByPatikaId(patikaId);

        for (Course course : courses){
            cmb_course_list.addItem(new Item(course.getId(), course.getName()));
        }
    }


    public static void main(String[] args) {
        Helper.setLayout();
        StudentGUI studentGUI = new StudentGUI();
    }
}
