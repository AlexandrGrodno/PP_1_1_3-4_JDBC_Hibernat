package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try{
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (`id` INT NOT NULL  AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NOT NULL," +
                    "`lastName` VARCHAR(45) NOT NULL," +
                    "`age` INT NOT NULL,PRIMARY KEY (`id`))");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement(String.format(("INSERT INTO user (name, lastName, age) VALUES ('%s', '%s', %d)"), name, lastName, age));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DELETE FROM user WHERE (`id` = '1')");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("SELECT * FROM user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userList.add(
                        new User(
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getByte(4))
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {

            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DELETE FROM user");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
