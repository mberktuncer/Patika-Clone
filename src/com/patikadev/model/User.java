package com.patikadev.model;

import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String password;
    private String userType;

    public User(){}

    public User(int id, String name, String uname, String password, String userType) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<User> getList(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM public.\"user\"";
        User user;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUname(resultSet.getString("uname"));
                user.setPassword(resultSet.getString("password"));
                user.setUserType(resultSet.getString("type"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static boolean add(String name, String userName, String password, String userType){
        String query = "INSERT INTO public.\"user\" (name, uname, password, type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(userName);
        if (findUser != null){
            Helper.showMessage("This username has been used before");
            return false;
        }
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, userType);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static User getFetch(String uName){
        User user = null;
        String sql = "SELECT * FROM public.\"user\" WHERE uname = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setString(1, uName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUname(resultSet.getString("uname"));
                user.setPassword(resultSet.getString("password"));
                user.setUserType(resultSet.getString("type"));
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static User getFetch(int id){
        User user = null;
        String sql = "SELECT * FROM public.\"user\" WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUname(resultSet.getString("uname"));
                user.setPassword(resultSet.getString("password"));
                user.setUserType(resultSet.getString("type"));
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    public static boolean delete(int id){
        String query = "DELETE FROM public.\"user\" WHERE id = ?";
        ArrayList<Course> courses = Course.getListByUserId(id);
        for (Course course : courses){
            Course.delete(course.getId());
        }
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);


            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean update(int id, String name, String uName, String password, String userType){
        String query = "UPDATE public.\"user\" SET name=?,uname=?,password=?,type=? WHERE id = ?";
        User findUser = User.getFetch(uName);
        if (findUser != null && findUser.getId() != id){
            Helper.showMessage("This username has been used before");
            return false;
        }
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,uName);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,userType);
            preparedStatement.setInt(5,id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList = new ArrayList<>();
        User user;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUname(resultSet.getString("uname"));
                user.setPassword(resultSet.getString("password"));
                user.setUserType(resultSet.getString("type"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static String searchQuery(String name, String uname, String userType){
        String query = "SELECT * FROM public.\"user\" WHERE uname ILIKE '%{{uname}}%' AND name ILIKE '%{{name}}%' AND type LIKE '{{type}}'";
        query = query.replace("{{name}}", name);
        query = query.replace("{{uname}}", uname);
        query = query.replace("{{type}}", userType);

        return query;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
