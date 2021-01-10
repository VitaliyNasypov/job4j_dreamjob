package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {
    private static final Logger LOGGER = LoggerFactory.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (InputStream io = PsqlStore.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            cfg.load(io);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("driver"));
        pool.setUrl(cfg.getProperty("url"));
        pool.setUsername(cfg.getProperty("username"));
        pool.setPassword(cfg.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT * FROM posts")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            while (filteredRowSet.next()) {
                posts.add(new Post(filteredRowSet.getInt("id"),
                        filteredRowSet.getString("name"),
                        filteredRowSet.getString("description"),
                        filteredRowSet.getTimestamp("created").toLocalDateTime()));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT * FROM candidates")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            while (filteredRowSet.next()) {
                Candidate candidate = new Candidate(filteredRowSet.getInt("id"),
                        filteredRowSet.getString("firstName"),
                        filteredRowSet.getString("lastName"),
                        filteredRowSet.getInt("age"));
                candidate.setIdPhoto(filteredRowSet.getString("ID_PHOTO"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return candidates;
    }

    @Override
    public void save(Post post, User user) {
        if (post.getId() == 0) {
            create(post, user);
        } else {
            update(post);
        }
    }

    private void create(Post post, User user) {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO posts(name, description, created,user_email) VALUES (?,?,?,?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.execute();
            try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void update(Post post) {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE posts SET name = ?, description = ?  WHERE id=?")) {
            preparedStatement.setInt(3, post.getId());
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void save(Candidate candidate, User user) {
        candidate.setCityId(getIdCity(candidate));
        if (candidate.getId() == 0) {
            create(candidate, user);
        } else {
            update(candidate);
        }
    }

    private int getIdCity(Candidate candidate) {
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from CITIES where city = ?")) {
            preparedStatement.setString(1, candidate.getCity());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            if (filteredRowSet.next()) {
                return filteredRowSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return 0;
    }

    private void create(Candidate candidate, User user) {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO candidates(firstName, lastName, age, user_email, city_id) VALUES (?,?,?,?,?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, candidate.getFirstName());
            preparedStatement.setString(2, candidate.getLastName());
            preparedStatement.setInt(3, candidate.getAge());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, candidate.getCityId());
            preparedStatement.execute();
            try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
            updatePhoto(candidate);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void update(Candidate candidate) {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE candidates SET firstName = ?, "
                             + "lastName = ?, age = ?, user_email = ?, city_id = ? WHERE id=?")) {
            preparedStatement.setInt(5, candidate.getId());
            preparedStatement.setString(1, candidate.getFirstName());
            preparedStatement.setString(2, candidate.getLastName());
            preparedStatement.setInt(3, candidate.getAge());
            preparedStatement.setInt(4, candidate.getCityId());
            preparedStatement.execute();
            updatePhoto(candidate);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void updatePhoto(Candidate candidate) {
        if (candidate.getIdPhoto() != null) {
            Path oldFileName = Path.of(File.separator + "bin" + File.separator
                    + "images" + File.separator + "photo_id" + File.separator + candidate.getIdPhoto());
            candidate.setIdPhoto(candidate.getId() + "_" + candidate.getIdPhoto().split("_", 2)[1]);
            Path newFileName = Path.of(File.separator + "bin" + File.separator
                    + "images" + File.separator + "photo_id" + File.separator
                    + candidate.getIdPhoto());
            try (Connection connection = pool.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement("UPDATE candidates SET ID_PHOTO = ? WHERE id=?")) {
                Files.move(oldFileName, newFileName);
                preparedStatement.setInt(2, candidate.getId());
                preparedStatement.setString(1, candidate.getIdPhoto());
                preparedStatement.execute();
            } catch (IOException | SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public Post findByIdPost(int id) {
        Post post = new Post(0, "", "", LocalDateTime.now());
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from posts where id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            if (filteredRowSet.next()) {
                post = new Post(filteredRowSet.getInt("id"),
                        filteredRowSet.getString("name"),
                        filteredRowSet.getString("description"),
                        filteredRowSet.getTimestamp("created").toLocalDateTime());
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return post;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = new Candidate(0, "", "", 0);
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from candidates where id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            if (filteredRowSet.next()) {
                candidate = new Candidate(filteredRowSet.getInt("id"),
                        filteredRowSet.getString("firstName"),
                        filteredRowSet.getString("lastName"),
                        filteredRowSet.getInt("age"));
                candidate.setIdPhoto(filteredRowSet.getString("id_photo"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return candidate;
    }

    @Override
    public User findByUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from users where email = ?")) {
            preparedStatement.setString(1, user.getEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                filteredRowSet.populate(resultSet);
            }
            if (filteredRowSet.next()) {
                if (new PasswordHash().validatePassword(password, filteredRowSet.getString("password"))) {
                    user.setId(filteredRowSet.getInt("id"));
                    user.setName(filteredRowSet.getString("name"));
                    user.setGroup(filteredRowSet.getString("group_user"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public void save(User user) {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO users(name, email, password, group_user) VALUES (?,?,?,?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            user.setPassword(new PasswordHash().generatePasswordHash(user.getPassword(),
                    user.getEmail(), 10000, 512, "PBKDF2WithHmacSHA512"));
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getGroup());
            preparedStatement.execute();
            try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> findByCity(String findCity) {
        String sql = "select (city) from CITIES WHERE city LIKE '" + findCity + "%'";
        List<String> city = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                filteredRowSet.populate(resultSet);
            }
            while (filteredRowSet.next()) {
                city.add(filteredRowSet.getString("city"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return city;
    }
}
