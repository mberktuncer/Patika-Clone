package com.patikadev.model;

import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisteredCourse {

    private int id;
    private int courseId;
    private int studentId;

    public RegisteredCourse(int id, int courseId, int studentId) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public static boolean add(int courseId, int studentId){
        String query = "INSERT INTO public.\"registered_course\" (course_id, student_id) VALUES (?, ?)";
        if (isAlreadyEnrolled(courseId, studentId)){
            Helper.showMessage("You are already enrolled for this course");
            return false;
        }
        try {
            PreparedStatement preparedStatement= DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,courseId);
            preparedStatement.setInt(2,studentId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean isAlreadyEnrolled(int courseId, int studentId){
        String query = "SELECT * FROM public.\"registered_course\" WHERE course_id = ? AND student_id = ?";

        try (PreparedStatement statement = DBConnector.getInstance().prepareStatement(query)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
