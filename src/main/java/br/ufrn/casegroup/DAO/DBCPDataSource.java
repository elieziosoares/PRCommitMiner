package br.ufrn.casegroup.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
    
    private static BasicDataSource ds = new BasicDataSource();
    
    static {
        ds.setUrl("jdbc:postgresql:Causal_CI_Quality_v3");
        ds.setUsername("commitminer");
        ds.setPassword("commitminer");
        ds.setMinIdle(20);
        ds.setMaxIdle(30);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private DBCPDataSource(){ }
}