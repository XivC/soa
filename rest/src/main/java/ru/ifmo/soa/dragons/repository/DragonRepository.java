package ru.ifmo.soa.dragons.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.soa.app.db.DBConnectionManager;
import ru.ifmo.soa.app.sql.SQLBuilder;
import ru.ifmo.soa.app.sql.filter.FilterSet;
import ru.ifmo.soa.app.sql.order.OrderSet;
import ru.ifmo.soa.dragons.model.*;
import ru.ifmo.soa.persons.model.Country;
import ru.ifmo.soa.persons.model.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class DragonRepository {

    @Autowired
    DBConnectionManager dbConnectionManager;

    final String BASE_GET_SQL = "SELECT * FROM ( SELECT\n" +
            "    dragons.id as id,\n" +
            "    dragons.name as name,\n" +
            "    dragons.coordinate_x as coordinate_x,\n" +
            "    dragons.coordinate_y as coordinate_y,\n" +
            "    dragons.creation_date as creation_date,\n" +
            "    dragons.age as age,\n" +
            "    dragons.color as color,\n" +
            "    dragons.type as type,\n" +
            "    dragons.character as character,\n" +
            "    dragons.killer_id as killer_id,\n" +
            "    persons.passport_id,\n" +
            "    persons.name as person_name,\n" +
            "    persons.height,\n" +
            "    persons.weight,\n" +
            "    persons.nationality " +
            "FROM dragons LEFT JOIN persons  ON dragons.killer_id = persons.passport_id) as dt ";

    public void save(Dragon dragon) throws SQLException {
        if (dragon.getId() == null) create(dragon);
        else update(dragon);

    }

    public void delete(Dragon dragon) throws SQLException {
        if (dragon.getId() == null) return;

        String sql = "DELETE FROM Dragons WHERE id = ?;";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, dragon.getId());
        statement.execute();
        connection.close();

    }

    private void create(Dragon dragon) throws SQLException {

        String sql = "INSERT INTO " +
                "Dragons (name, coordinate_x, coordinate_y, creation_date, age, color, type, character, killer_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getX());
        statement.setLong(3, dragon.getCoordinates().getY());
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.setInt(5, dragon.getAge());
        statement.setString(6, dragon.getColor() == null ? null : dragon.getColor().toString());
        statement.setString(7, dragon.getType().toString());
        statement.setString(8, dragon.getCharacter() == null ? null : dragon.getCharacter().toString());
        statement.setString(9, dragon.getKillerId());
        statement.execute();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        long id = keys.getLong(1);
        dragon.setId(id);
        connection.close();
    }

    public List<Dragon> getList(FilterSet filterSet, OrderSet orderSet, Integer limit, Integer offset) throws SQLException {

        String sql = (new SQLBuilder()).base(BASE_GET_SQL).filter(filterSet).order(orderSet).limit(limit).offset(offset).build();
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        LinkedList<Dragon> dragons = new LinkedList<>();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            dragons.add(fromRow(resultSet));
        }
        connection.close();
        return dragons;

    }

    public Integer countByType(DragonType type) throws SQLException{

        String sql = "SELECT COUNT(*) FROM dragons WHERE type > ?";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, type.toString());
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        return resultSet.getInt(1);



    }

    public Integer sumAge() throws SQLException{

        String sql = "SELECT COUNT(age) FROM dragons";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        return resultSet.getInt(1);



    }

    private void update(Dragon dragon) throws SQLException {

        String sql = "UPDATE dragons SET name = ?, coordinate_x = ?, coordinate_y = ?, age = ?, color = ?, type = ?, character = ?, killer_id = ?" +
                "WHERE id = ?;";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getX());
        statement.setLong(3, dragon.getCoordinates().getY());
        statement.setInt(4, dragon.getAge());
        statement.setString(5, dragon.getColor() == null ? null : dragon.getColor().toString());
        statement.setString(6, dragon.getType().toString());
        statement.setString(7, dragon.getCharacter()  == null ? null : dragon.getCharacter().toString());
        statement.setString(8, dragon.getKillerId());
        statement.setLong(9, dragon.getId());
        statement.execute();
        connection.close();
    }

    public Optional<Dragon> getById(Long id) throws SQLException {

        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(BASE_GET_SQL + "WHERE id = ?");
        statement.setLong(1, id);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        Dragon dragon = null;
        if (resultSet.next()) dragon = fromRow(resultSet);
        return Optional.ofNullable(dragon);
    }

    Dragon fromRow(ResultSet resultSet) throws SQLException {
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
                .type(DragonType.valueOf(resultSet.getString("type")));

        String colorString = resultSet.getString("color");
        String characterString = resultSet.getString("character");
        if (colorString != null) dragonBuilder.color(Color.valueOf(colorString));
        if (characterString != null) dragonBuilder.character(DragonCharacter.valueOf(characterString));

        String killerId = resultSet.getString("killer_id");
        if (killerId != null) {
            dragonBuilder.killer(
                    personBuilder
                            .passportID(resultSet.getString("passport_id"))
                            .name(resultSet.getString("person_name"))
                            .height(resultSet.getLong("height"))
                            .weight(resultSet.getDouble("weight"))
                            .nationality(Country.valueOf(resultSet.getString("nationality")))
                            .build()
            );
        }
        return dragonBuilder.build();

    }


}
