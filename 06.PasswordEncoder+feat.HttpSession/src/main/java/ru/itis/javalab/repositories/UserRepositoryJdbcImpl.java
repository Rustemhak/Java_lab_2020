package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
    private DataSource dataSource;
    //language=PostgreSQL
    private static final String SQL_FIND_ALL = "select * from student";

    //language=PostgreSQL
    private static final String SQL_SAVE_ALL = "insert into student (id, first_name, last_name, age, token) values (?, ?, ?, ?, ?)";

    //language=PostgreSQL
    private static final String SQL_UPDATE = "update student set first_name = ?, last_name = ?, age = ?, token = ? WHERE id = ?";

    //language=PostgreSQL
    private static final String SQL_FIND_ALL_BY_AGE = "select * from student where age=?";

    //language=PostgreSQL
    private static final String SQL_FIND_BY_FIRST_NAME = "select * from student where first_name=?";

    //language=PostgreSQL
    private static final String SQL_FIND_BY_TOKEN = "select * from student where token=?";

    private SimpleJdbcTemplate template;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .hashPassword(row.getString("passwordhash"))
            .lastName(row.getString("last_name"))
            .token(row.getString("token"))
            .age(row.getInt("age"))
            .build();

    @Override
    public void save(User entity) {
        Long id = template.save(SQL_SAVE_ALL);
        entity.setId(id);
    }

    @Override
    public void update(User entity) {
        template.update(SQL_UPDATE,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getToken(),
                entity.getId());
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public List<User> findAllByAge(int age) {
        return template.query(SQL_FIND_ALL_BY_AGE, userRowMapper, age);
    }

    @Override
    public User findUserByFirstName(String firstName) {
        return template.queryOne(SQL_FIND_BY_FIRST_NAME, userRowMapper, firstName);
    }

    @Override
    public User findUserByToken(String token) {
        return template.queryOne(SQL_FIND_BY_TOKEN, userRowMapper, token);
    }
}
