package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.shaded.com.ongres.scram.common.ScramAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);
    private static HikariDataSource dataSource;
    static {
        try{
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/library_db");
            config.setUsername("postgres");
            config.setPassword("password");
            config.setMaximumPoolSize(10);
            dataSource = new HikariDataSource(config);
            logger.info("Database Connection Pool has been initialized Successfully");
        } catch (Exception e){
            logger.error("Failed to initialize database connection pool",e);
            throw new RuntimeException("Failed to initialize Database connection pool",e);
        }
    }
    public static Connection getConnection() throws SQLException{
      return dataSource.getConnection();
    }


}
