package ru.ifmo.soa.persons.repository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.ifmo.soa.app.db.DBConnectionManager;
import ru.ifmo.soa.persons.model.Country;
import ru.ifmo.soa.persons.model.Person;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Optional;

@Component
public class PersonRepository {

    @Autowired
    DBConnectionManager connectionManager;

    public void delete(Person person) throws SQLException {
        if (person.getPassportID() == null) return;

        String sql = "DELETE FROM Persons WHERE passport_id = ?;";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, person.getPassportID());
        statement.execute();
        connection.close();

    }

    public void create(Person person) throws SQLException {

        String sql = "INSERT INTO " +
                "Persons (passport_id, name, height, weight, nationality)" +
                "VALUES (?, ?, ?, ?, ?);";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, person.getPassportID());
        statement.setString(2, person.getName());
        statement.setLong(3, person.getHeight());
        statement.setDouble(4, person.getWeight());
        statement.setString(5, person.getNationality().toString());
        statement.execute();
        connection.close();
    }

    public void update(Person person) throws SQLException {

        String sql = "UPDATE persons SET name = ?, height = ?, weight = ?, nationality = ?" +
                "WHERE passport_id = ?;";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, person.getName());
        statement.setLong(2, person.getHeight());
        statement.setDouble(3, person.getWeight());
        statement.setString(4, person.getNationality().toString());
        statement.setString(5, person.getPassportID());
        statement.execute();
        connection.close();
    }

    public Optional<Person> getById(String passportId) throws SQLException {
        String sql = "SELECT * FROM Persons  WHERE passport_id = ?";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, passportId);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        Person person = null;
        if (resultSet.next()) person = fromRow(resultSet);
        return Optional.ofNullable(person);
    }

    Person fromRow(ResultSet resultSet) throws SQLException {
        Person.PersonBuilder personBuilder = Person.builder();
        personBuilder
                .passportID(resultSet.getString("passport_id"))
                .name(resultSet.getString("name"))
                .height(resultSet.getLong("height"))
                .weight(resultSet.getDouble("weight"))
                .nationality(Country.valueOf(resultSet.getString("nationality")));
        return personBuilder.build();

    }


}
