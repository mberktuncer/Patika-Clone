package com.patikadev.model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int userId;
    private int patikaId;
    private String name;
    private String language;

    private Patika patika;
    private User educator;

    public Course(int id, int userId, int patikaId, String name, String language) {
        this.id = id;
        this.userId = userId;
        this.patikaId = patikaId;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patikaId);
        this.educator = User.getFetch(userId);
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courses = new ArrayList<>();
        Course course;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"course\"");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int patikaId = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id, userId, patikaId, name, language);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public static ArrayList<Course> getListByUserId(int uid) {
        ArrayList<Course> courses = new ArrayList<>();
        Course course;
        try {
            String query = "SELECT * FROM public.\"course\" WHERE user_id = ?";
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, uid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int patikaId = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id, userId, patikaId, name, language);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public static ArrayList<Course> getListByPatikaId(int pId){
        ArrayList<Course> courses = new ArrayList<>();
        Course course;
        try{
            String query = "SELECT * FROM public.\"course\" WHERE patika_id = ?";
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int patikaId = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id, userId, patikaId, name, language);
                courses.add(course);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return courses;
    }

    public static boolean add(int userId, int patikaId, String name, String language){
        String query = "INSERT INTO public.course (user_id, patika_id, \"name\", \"language\") VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, patikaId);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, language);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int id){
        String query = "DELETE FROM public.\"course\" WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);


            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPatikaId() {
        return patikaId;
    }

    public void setPatikaId(int patikaId) {
        this.patikaId = patikaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }
}
