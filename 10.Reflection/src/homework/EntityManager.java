package homework;

import javax.sql.*;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EntityManager {
    private DataSource dataSource;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public EntityManager(DataSource dataSource, Connection connection, Statement statement, ResultSet resultSet) {
        this.dataSource = dataSource;
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) throws SQLException {
        StringBuilder sqlCreate = new StringBuilder("create table " + tableName + "(");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field :
                fields
        ) {
            sqlCreate.append(field.getName())
                    .append(getType(field))
                    .append(",");
        }
        sqlCreate.deleteCharAt(sqlCreate.length() - 1);
        sqlCreate.append(");");
        resultSet = statement.executeQuery(sqlCreate.toString());

        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))
    }

    private String getType(Field field) {
        String fieldType = field.getType().getSimpleName();
        String type = "";
        switch (fieldType) {
            case "Long":
                type = "bigint";
                break;
            case "boolean":
                type = "boolean";
                break;
            case "Integer":
                type = "int";
                break;
            default:
                type = "varchar(255)";
        }
        return type;
    }

    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();
        StringBuilder sqlInsert = new StringBuilder("insert into " + tableName + "(");
        Field[] fields = classOfEntity.getDeclaredFields();
        for (Field field : fields
        ) {
            sqlInsert.append(" ").append(field.getName()).append(",");
        }
        sqlInsert.deleteCharAt(sqlInsert.length() - 1);
        sqlInsert.append(") ");
        StringBuilder sqlValues = new StringBuilder("values (");
        for (Field field : fields
        ) {
            sqlValues.append(getValue(field, entity)).append(",");
        }
        sqlValues.deleteCharAt(sqlValues.length() - 1);
        sqlInsert.append(");");
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into
    }

    private String getValue(Field field, Object entity) {
        String fieldType = field.getType().getSimpleName();
        StringBuilder value = new StringBuilder();
        field.setAccessible(true);
        try {
            value.append(field.get(entity));
        } catch (IllegalAccessException e) {
            //ignore
        }
        switch (fieldType) {
            case "Boolean":
            case "String":
            case "Long":
                value.append("'").append(value).append("'");
                break;
        }
        return value.toString();
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        String sqlFindById = "select * from" + tableName + "where id = " + idType + "limit 1";
        // сгенеририровать select
        return null;
    }

}
