package com.example.auth_service.dao;

import com.example.auth_service.models.Account;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class AccountDAO {

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private PreparedStatement preparedStatement;
    private Connection connection;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> findTopStudentsFaculty(String faculty) {
        List<String> accounts = new ArrayList<>();
        System.out.println("findTOP");
        try {
            preparedStatement = connection.prepareStatement
                    ("SELECT account.name, di_group.faculty, account.score " +
                            "FROM account " +
                            "LEFT JOIN di_group " +
                            "ON account.group_id = di_group.group_id " +
                            "WHERE di_group.faculty = ?" +
                            "ORDER BY account.score DESC LIMIT 10");
            preparedStatement.setString(1, faculty);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                accounts.add(resultSet.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }


}
