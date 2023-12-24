package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("test1", "test1", (byte) 1);
        userService.saveUser("test2", "test2", (byte) 2);
        userService.saveUser("test3", "test3", (byte) 3);
        userService.saveUser("test4", "test4", (byte) 4);

        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
