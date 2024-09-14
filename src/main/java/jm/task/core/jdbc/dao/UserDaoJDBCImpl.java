package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "lastName VARCHAR(50) NOT NULL," +
                    "age TINYINT NOT NULL)";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось создать таблицу", e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String dropTableQuery = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(dropTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления таблицы users", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                System.out.println("Пользователь с именем" + name + " " + lastName + " добавлен.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения пользователя", e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE id =?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления пользователя", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка пользователей", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String truncateTableQuery = "TRUNCATE TABLE users";
            statement.executeUpdate(truncateTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки таблицы", e);
        }
    }
}
