package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDaoHibernateImpl userDaoHibernate;

    public UserServiceImpl() {
        userDaoHibernate = new UserDaoHibernateImpl();
    }

    public void createUsersTableQuery() {
        userDaoHibernate.createUsersTableQuery();
        System.out.println("Таблица успешно создана!");
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
        System.out.println("Таблица была удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name, lastName, age);
        System.out.println("Пользователь " + name + " был добавлен в базу данных");
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
        System.out.println("Пользователь с id - " + id + " был удалён из таблицы");
    }

    public List<User> getAllUsers() {
        List<User> users =  userDaoHibernate.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
        System.out.println("Таблица была очищена!");
    }
}
