package ru.itis.javalab.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 05.10.2020
 * 08. Web Application
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface RowMapper<T> {
    T mapRow(ResultSet row) throws SQLException;
}
