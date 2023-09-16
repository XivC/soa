package ru.ifmo.soa.service1.dragons.repository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.service1.app.db.DBConnectionManager;
import ru.ifmo.soa.service1.dragons.model.*;
import ru.ifmo.soa.service1.app.sql.SQLBuilder;
import ru.ifmo.soa.service1.app.sql.filter.FilterSet;
import ru.ifmo.soa.service1.app.sql.order.OrderSet;
import ru.ifmo.soa.service1.persons.model.Country;
import ru.ifmo.soa.service1.persons.model.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class DragonRepository {

    @Inject
    DBConnectionManager connectionManager;
    public void save(Dragon dragon) throws SQLException{
        if (dragon.getId() == null) create(dragon);
        else update(dragon);

    }
public void delete(Dragon dragon) throws SQLException {
        if (dragon.getId() == null) return;

        String sql = "DELETE FROM Dragons WHERE id = ?;";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, dragon.getId());
        statement.execute();
        connection.close();

}

    private void create(Dragon dragon) throws SQLException {

        String sql = "INSERT INTO " +
                "Dragons (name, coordinate_x, coordinate_y, creation_date, age, color, type, character, killer_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getX());
        statement.setLong(3, dragon.getCoordinates().getY());
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.setInt(5, dragon.getAge());
        statement.setString(6, dragon.getColor().toString());
        statement.setString(7, dragon.getType().toString());
        statement.setString(8, dragon.getCharacter().toString());
        statement.setString(9, dragon.getKillerId());
        statement.execute();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        long id = keys.getLong(1);
        dragon.setId(id);
        connection.close();
    }

    public List<Dragon> getList(FilterSet filterSet, OrderSet orderSet, Integer limit, Integer offset) throws SQLException{

        String sql = (new SQLBuilder()).base( "SELECT * FROM dragons").filter(filterSet).order(orderSet).build();
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        LinkedList<Dragon> dragons = new LinkedList<>();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()){
            dragons.add(fromRow(resultSet));
        }
        connection.close();
        return dragons;

    }

    private void update(Dragon dragon) throws SQLException {

        String sql = "UPDATE dragons SET name = ?, coordinate_x = ?, coordinate_y = ?, age = ?, color = ?, type = ?, character = ?, killer_id = ?" +
                "WHERE id = ?;";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getX());
        statement.setLong(3, dragon.getCoordinates().getY());
        statement.setInt(4, dragon.getAge());
        statement.setString(5, dragon.getColor().toString());
        statement.setString(6, dragon.getType().toString());
        statement.setString(7, dragon.getCharacter().toString());
        statement.setString(8, dragon.getKillerId());
        statement.setLong(9, dragon.getId());
        statement.execute();
        connection.close();
    }

    public Optional<Dragon> getById(Long id) throws SQLException{
        String sql = "SELECT * FROM Dragons JOIN Persons ON killer_id = passport_id WHERE id = ?";
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        Dragon dragon = null;
        if (resultSet.next()) dragon = fromRow(resultSet);
        return Optional.ofNullable(dragon);
    }

    Dragon fromRow(ResultSet resultSet) throws SQLException{
        Dragon.DragonBuilder dragonBuilder = Dragon.builder();
        Person.PersonBuilder personBuilder = Person.builder();
        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();

        dragonBuilder
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .coordinates(
                        coordinatesBuilder
                                .x(resultSet.getInt("coordinate_x"))
                                .y(resultSet.getLong("coordinate_y"))
                                .build()
                )
                .creationDate(resultSet.getDate("creation_date").toLocalDate())
                .age(resultSet.getInt("age"))
                .color(Color.valueOf(resultSet.getString("color")))
                .type(DragonType.valueOf(resultSet.getString("type")))
                .character(DragonCharacter.valueOf(resultSet.getString("character")));

        String killerId = resultSet.getString("killer_id");
        if (killerId != null) {
            dragonBuilder.killer(
                    personBuilder
                            .passportID(resultSet.getString("passport_id"))
                            .name(resultSet.getString("persons.name"))
                            .height(resultSet.getLong("height"))
                            .weight(resultSet.getDouble("weight"))
                            .nationality(Country.valueOf(resultSet.getString("nationality")))
                            .build()
            );
        }
        return dragonBuilder.build();

    }


}
