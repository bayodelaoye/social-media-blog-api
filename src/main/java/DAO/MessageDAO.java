package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    Connection connection = ConnectionUtil.getConnection();
    
    public Message createMessage(Message message) {
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

            return message;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account getUser(Message message) {
        try {
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getPosted_by());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int messageId) {
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, messageId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deletMessage(int messageId) {
        try {
            //Write SQL logic here
            String sql = "delete * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, messageId);

            preparedStatement.executeQuery();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int messageId, Message message) {
        try {
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, messageId);

            preparedStatement.executeUpdate();

            return message;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getMessagesByUser(int accountId) {
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, accountId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
