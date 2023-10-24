package com.example.messenger_service.dao;

import com.example.messenger_service.models.Chat;
import com.example.messenger_service.models.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class ChatDAO {

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private PreparedStatement preparedStatement;
    private Connection connection;

    @PostConstruct
    private void init() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> getChatsIdByAccountId(int accountId) {
        List<Long> received = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement
                    ("SELECT * FROM account_chat WHERE account_id=?");
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                received.add(resultSet.getLong(2));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return received;
    }

    public int getIdByAccount(String nameAccount) {
        int id = -1;
        try {
            preparedStatement = connection.prepareStatement
                    ("SELECT * FROM account WHERE username=?");
            preparedStatement.setString(1, nameAccount);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                id = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    public List<Long> getIdAccountsOfChat(int id_chat) {
        List<Long> received = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement
                    ("SELECT * FROM account_chat WHERE chat_id=?");
            preparedStatement.setInt(1, id_chat);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                received.add(resultSet.getLong(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return received;
    }

    public boolean saveInAccount_chat(Chat chat) {
        try {
            for (int j = 0; j < chat.getUsers().size(); j++) {

                preparedStatement = connection.prepareStatement
                        ("INSERT INTO account_chat (account_id, chat_id) VALUES (?,?)");
                preparedStatement.setInt(1, chat.getId().intValue());
                preparedStatement.setInt(2, chat.getUsers().get(j).intValue());
                System.out.println("+ " + chat.getId() + " " + chat.getUsers().get(j));
                preparedStatement.executeQuery();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
