package ru.ifmo.soa.killer.db;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.ejb.Local;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {

    private final String dbConnectionUrl;
    private final Integer maxConnections;

    private BasicDataSource dataSource;

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
            throw new RuntimeException(ex);
        }
        return props;
    }

    private void setupDB(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(this.dbConnectionUrl);
        dataSource.setInitialSize(this.maxConnections);
        dataSource.setMaxTotal(this.maxConnections);
        this.dataSource = dataSource;
    }


    public Connection getConnection(){
        try {
            return this.dataSource.getConnection();
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

}
