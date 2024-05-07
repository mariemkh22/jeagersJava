package services;

import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.myDatabase;

public class serviceUser implements lService<User>{

    Connection connection;

    public serviceUser(){
        connection=myDatabase.getInstance().getConnection();
    }
    @Override
    public void addUser(User user) throws SQLException {
        String req="insert into user(email,password,roles,full_name,phone_number,date_of_birth)"+
                "values('"+user.getEmail()+"','"+user.getPassword()+"','"+user.getRoles()+"','"+user.getFull_name()+"','"+user.getPhone_number()+"','"+user.getDate_of_birth()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String req ="update user set email=?,password=?,full_name=?,phone_number=?,date_of_birth=? where id=?";

        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1,user.getEmail());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setString(3,user.getFull_name());
        preparedStatement.setString(4,user.getPhone_number());
        preparedStatement.setString(5,user.getDate_of_birth());
        preparedStatement.setInt(6,user.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String req ="delete from user where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("User deleted");
    }

    @Override
    public List<User> displayUser() throws SQLException {
        List<User> users= new ArrayList<>();
        String req ="select * from user";
        Statement statement= connection.createStatement();
        ResultSet rs =statement.executeQuery(req);
        while (rs.next()){
            User user= new User();
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFull_name(rs.getString("full_name"));
            user.setPhone_number(rs.getString("phone_number"));
            user.setDate_of_birth(rs.getString("date_of_birth"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setFeedback(rs.getString("feedback"));
            user.setFeedbackType(rs.getString("feedbackType"));
            user.setFeedbackStatus(rs.getString("feedbackStatus"));
            user.setId(rs.getInt("id"));
            users.add(user);
        }
        return users;
    }

    public List<User> displayUserFeedback() throws SQLException {
        List<User> users= new ArrayList<>();
        String req ="select * from user";
        Statement statement= connection.createStatement();
        ResultSet rs =statement.executeQuery(req);
        while (rs.next()){
            User user= new User();
            user.setFull_name(rs.getString("full_name"));
            user.setFeedback(rs.getString("feedback"));
            user.setFeedbackType(rs.getString("feedbackType"));
            user.setFeedbackStatus(rs.getString("feedbackStatus"));
            user.setId(rs.getInt("id"));
            users.add(user);
        }
        return users;
    }
}
