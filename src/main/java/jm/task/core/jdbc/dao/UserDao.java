package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTableQuery();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
