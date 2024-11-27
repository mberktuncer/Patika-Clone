package com.patikadev.model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseComment {
    private int id;
    private String comment;


    public CourseComment(int id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public static boolean add(int courseId, String comment){
        String query = "INSERT INTO public.\"course_comment\" (course_id, comment) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, courseId);
            preparedStatement.setString(2, comment);
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
