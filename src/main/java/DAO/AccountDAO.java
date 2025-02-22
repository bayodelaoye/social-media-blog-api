package DAO;

import Model.Account;
import java.sql.*;
import Util.ConnectionUtil;

public class AccountDAO {
    Connection connection = ConnectionUtil.getConnection();

    public Account createAccount(Account account) {
        try {
            String sql = "insert into account (username, password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

            return account;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account getAccount(String username) {
        try {

            String sql = "select * from account where username = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, username);
            // preparedStatement.setString(2, account.getPassword());

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

    public Account login(Account account) {
        try {

            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account userAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return userAccount;
            }
     
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
