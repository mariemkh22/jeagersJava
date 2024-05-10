package services;

import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface lService <T>{

    public void addUser(T t) throws SQLException;
    public void updateUser(T t) throws SQLException;
    public void deleteUser(int id) throws SQLException;
    public List<T> displayUser() throws SQLException;

}
