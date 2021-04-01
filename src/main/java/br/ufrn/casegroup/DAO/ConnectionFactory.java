package br.ufrn.casegroup.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection getConnection() throws RuntimeException{
        try {
            return DriverManager.getConnection("jdbc:postgresql:Causal_CI_Quality", "commitminer", "commitminer");
        } catch (SQLException e) {
            throw new RuntimeException(e);        
        }    
    }
}

        
