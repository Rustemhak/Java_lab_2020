package ru.itis.homework;

import java.sql.*;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "384953";

    public static void main(String[] args) throws SQLException {
        SimpleDataSource dataSource = new SimpleDataSource();
        Connection connection = dataSource.openConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        EntityManager entityManager = new EntityManager(dataSource, connection, statement);
        entityManager.createTable("account", User.class);
        User user = new User(10L, "Rustem", "X", true);
        try {
            entityManager.save("account",user);
        } catch (IllegalAccessException e) {
            //ignore
        }
        User user1 = entityManager.findById("account",User.class,Long.class,10L);
        System.out.println(user1.toString());
    }
}
