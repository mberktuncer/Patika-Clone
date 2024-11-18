package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Operator;
import com.patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scroll_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_search_name;
    private JTextField fld_search_user_name;
    private JComboBox cmb_search_user_type;
    private JButton btn_user_search;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private final Operator operator;

    public OperatorGUI(Operator operator){
        this.operator = operator;
        Helper.setLayout();
        add(wrapper);
        setSize(1000, 500);
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome " + operator.getName());

        // ModelUserList
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //ID column can not changeable
                if (column == 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] column_user_list = {"ID", "Full Name", "User Name", "Password", "User Type"};
        mdl_user_list.setColumnIdentifiers(column_user_list);
        row_user_list = new Object[column_user_list.length];
        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selectedRowUserId = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(selectedRowUserId);
            }
            catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int userId = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String uName = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String userPassword = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String userType = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (User.update(userId, name, uName, userPassword, userType)){
                    Helper.showMessage("done");
                }
                loadUserModel();

            }
        });

        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_password)){
                Helper.showMessage("fill");
            }
            else{
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String password = fld_user_password.getText();
                String userType = cmb_user_type.getSelectedItem().toString();
                if (User.add(name, uname, password, userType)){
                    Helper.showMessage("done");
                    loadUserModel();
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_password.setText(null);
                }

            }
        });

        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)){
                Helper.showMessage("fill");
            }
            else{
                int userId = Integer.parseInt(fld_user_id.getText());
                if (User.delete(userId)){
                    Helper.showMessage("done");
                    loadUserModel();
                }
                else{
                    Helper.showMessage("error");
                }
            }
        });

        btn_user_search.addActionListener(e -> {
            String name = fld_search_name.getText();
            String uName = fld_search_user_name.getText();
            String userType = cmb_search_user_type.getSelectedItem().toString();

            String query = User.searchQuery(name, uName, userType);
            ArrayList<User> searchingUser = User.searchUserList(query);
            loadUserModel(searchingUser);
        });
    }

    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User user : User.getList()){
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUname();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getUserType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User user : list){
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUname();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getUserType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator operator1 = new Operator();
        operator1.setId(1);
        operator1.setName("Berk");
        operator1.setUname("mberktuncer");
        operator1.setPassword("1234");
        operator1.setUserType("operator");
        OperatorGUI operatorGUI = new OperatorGUI(operator1);
    }
}
