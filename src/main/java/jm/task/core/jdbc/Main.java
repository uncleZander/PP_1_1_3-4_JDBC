package jm.task.core.jdbc;

import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 25);
        System.out.println("Пользователь с именем - Иван Иванов добавлен в базу данных");
        userService.saveUser("Петр", "Петров", (byte) 30);
        System.out.println("Пользователь с именем - Петр Петров добавлен в базу данных");
        userService.saveUser("Сидор", "Сидоров", (byte) 35);
        System.out.println("Пользователь с именем - Сидор Сидоров добавлен в базу данных");
        userService.saveUser("Василий", "Васильев", (byte) 40);
        System.out.println("Пользователь с именем - Василий Васильев добавлен в базу данных");

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
