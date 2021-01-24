package ru.itis.homework;

import ru.itis.Component;

import javax.jws.soap.SOAPBinding;
import javax.sql.*;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Filter;


public class EntityManager {
    private SimpleDataSource simpleDataSource;
    private Connection connection;
    private Statement statement;


    public EntityManager(SimpleDataSource simpleDataSource, Connection connection, Statement statement) {
        this.simpleDataSource = simpleDataSource;
        this.connection = connection;
        this.statement = statement;
    }

    private String getType(String fieldType) {
        String type = "";
        switch (fieldType) {
            case "Long":
                type = "bigint";
                break;
            case "String":
                type = "varchar(255)";
                break;
            case "boolean":
                type = "boolean";
                break;
        }
        return type;
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) throws SQLException {
        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))
        StringBuilder sqlCreate = new StringBuilder("create table " + tableName + "(");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field :
                fields
        ) {
            sqlCreate.append(field.getName()).append(" ")
                    .append(getType(field.getType().getSimpleName()))
                    .append(",");
        }
        sqlCreate.deleteCharAt(sqlCreate.length() - 1);
        sqlCreate.append(");");
        System.out.println(sqlCreate.toString());
        statement.execute(sqlCreate.toString());
    }


    public void save(String tableName, Object entity) throws IllegalAccessException {
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into
        Class<?> entityClass = entity.getClass();
        StringBuilder sqlInsert = new StringBuilder("insert into " + tableName + "(");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields
        ) {
            sqlInsert.append(field.getName()).append(",");
        }
        sqlInsert.deleteCharAt(sqlInsert.length() - 1);

        sqlInsert.append(") values (");

        for (Field field : fields
        ) {
            field.setAccessible(true);
            if (field.getType().getSimpleName().equals("String")) {
                sqlInsert.append("'").append(field.get(entity)).append("'");
            } else
                sqlInsert.append(field.get(entity));
            sqlInsert.append(",");
        }
        sqlInsert.replace(sqlInsert.length() - 1, sqlInsert.length(), ");");
        // System.out.println(sqlInsert);
        try {
            statement.execute(sqlInsert.toString());
        } catch (SQLException e) {
            //ignore
        }
    }


    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        // сгенеририровать select
        String sqlSelect = "select * from " + tableName + " where id = " + idValue + ";";
        System.out.println(sqlSelect);
        try {
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            resultSet.next();
            Object constructor = resultType.newInstance();
            Field[] fields = resultType.getDeclaredFields();
            for (Field field:
                 fields
            ) {
                field.setAccessible(true);
                field.set(constructor,resultSet.getObject(field.getName()));
            }
            return (T) constructor;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            //ignore
        }
        return null;
    }

}
