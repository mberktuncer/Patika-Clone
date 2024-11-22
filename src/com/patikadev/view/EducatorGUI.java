package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.helper.Item;
import com.patikadev.model.Course;
import com.patikadev.model.Educator;
import com.patikadev.model.Patika;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private JTable tbl_course_list;
    private JLabel lbl_welcome;
    private JTextField fld_new_course_name;
    private JComboBox cmb_patika;
    private JButton btn_add_course;
    private JTextField fld_course_language;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private Educator educator;
    public EducatorGUI(int educatorID){
        Helper.setLayout();
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_welcome.setText("Welcome to Educator Screen");

        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] column_course_list = {"ID", "Course Name", "Language"};
        mdl_course_list.setColumnIdentifiers(column_course_list);
        row_course_list = new Object[column_course_list.length];
        loadCourseModel(educatorID);

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaComboBox();

        btn_add_course.addActionListener(e -> {
            Item patikaItem = (Item) cmb_patika.getSelectedItem();

            if (Helper.isFieldEmpty(fld_new_course_name)){
                Helper.showMessage("fill");
            }
            else{
                if (Course.add(educatorID, patikaItem.getKey(), fld_new_course_name.getText(), fld_course_language.getText())){
                    Helper.showMessage("done");
                    loadCourseModel(educatorID);
                    fld_course_language.setText(null);
                    fld_new_course_name.setText(null);
                }
                else{
                    Helper.showMessage("error");
                }
            }
        });
    }

    private void loadCourseModel(int educatorId) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        ArrayList<Course> coursesByEducator = Course.getListByUserId(educatorId);

        for (Course course : coursesByEducator){
            int i = 0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getName();
            row_course_list[i++] = course.getLanguage();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadPatikaComboBox(){
        cmb_patika.removeAllItems();
        for (Patika patika : Patika.getList()){
            cmb_patika.addItem(new Item(patika.getId(), patika.getName()));
        }
    }

    public static void main(String[] args) {

        EducatorGUI educatorGUI = new EducatorGUI(17);
    }


}
