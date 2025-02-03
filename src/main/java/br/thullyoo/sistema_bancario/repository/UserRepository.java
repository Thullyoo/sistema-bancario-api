package br.thullyoo.sistema_bancario.repository;

import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.model.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    private JdbcClient jdbcClient;

    public void register(UserRequest userRequest){
        jdbcClient.sql("""
                INSERT INTO USERS (name, email, password, balance, document)
                VALUES (:name, :email, :password, :balance, :document)
                """)
                .param("name", userRequest.name())
                .param("email", userRequest.email())
                .param("password", userRequest.password())
                .param("balance", new BigDecimal(0))
                .param("document", userRequest.document())
                .update();
    }

    public User getUserById(UUID id) {
        try {
            Optional<User> user = jdbcClient
                    .sql("SELECT * FROM users WHERE uuid = ?")
                    .param(id)
                    .query(User.class)
                    .optional();

            if (user.isEmpty()) {
                throw new NoSuchElementException("User not found");
            }
            return user.get();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user", e);
        }
    }

    public User getUserByDocument(String document){
        try {
            Optional<User> user = jdbcClient
                    .sql("SELECT * FROM users WHERE document = ?")
                    .param(document)
                    .query(User.class)
                    .optional();

            if (user.isEmpty()) {
                throw new NoSuchElementException("User not found");
            }
            return user.get();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user", e);
        }
    }

    public void updateUser(User user) {
        jdbcClient.sql("""
            UPDATE USERS
            SET name = :name,
                email = :email,
                password = :password,
                document = :document,
                balance = :balance
            WHERE uuid = :id
            """)
                .param("uuid", user.getUuid())
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .param("document", user.getDocument())
                .param("balance", user.getBalance())
                .update();
    }


}
