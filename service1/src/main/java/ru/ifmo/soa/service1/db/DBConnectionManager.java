package ru.ifmo.soa.service1.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@ApplicationScoped
public class DBConnectionManager {

    private final String dbConnectionUrl;
    private final Integer maxConnections;

    private BasicDataSource dataSource;

    @Inject
    Logger logger;

    private static final String propertiesPath = "db.properties";

    public DBConnectionManager(){
        Properties props = this.readProperties();
        this.maxConnections = Integer.valueOf(props.getProperty("db.max-connections", "10"));
        this.dbConnectionUrl = props.getProperty("db.url");
        this.setupDB();
    }

    private Properties readProperties(){

        Properties props = new Properties();
        try {
            InputStream resourceStream = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(propertiesPath);
            props.load(resourceStream);
        }
        catch (IOException ex){
            logger.error("Db configuration failed");
            throw new RuntimeException(ex);
        }
        return props;
    }

    private void setupDB(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(this.dbConnectionUrl);
        dataSource.setInitialSize(this.maxConnections);
        dataSource.setMaxActive(this.maxConnections);
        this.dataSource = dataSource;
    }


    public Connection getConnection(){
        try {
            return this.dataSource.getConnection();
        }
        catch (SQLException ex){
            logger.error("Cant init db connection");
            throw new RuntimeException(ex);
        }
    }

}
