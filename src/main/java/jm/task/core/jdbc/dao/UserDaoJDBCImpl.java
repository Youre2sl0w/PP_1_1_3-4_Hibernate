package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getInstance().getConnection();

    private static final String createUsersTable = "create table if not exists users (id bigint primary key auto_increment, name varchar(255), lastname varchar(255), age int)";
    private static final String dropUsersTable = "drop table if exists users";
    private static final String saveUser = "insert into users(name, lastname, age) values(?, ?, ?)";
    private static final String removeUserById = "delete from users where id = ?";
    private static final String getAllUsers = "select * from users";
    private static final String cleanUsersTable = "truncate table users";


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(removeUserById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(ResultSet resultSet = connection.createStatement().executeQuery(getAllUsers)) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                                     resultSet.getString("lastname"),
                                     resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
