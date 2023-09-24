package ru.ifmo.soa.killer.repository;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.killer.db.DBConnectionManager;
import ru.ifmo.soa.killer.model.Cave;

import java.sql.*;
import java.util.Optional;

@RequestScoped
public class CaveRepository {

    @Inject
    DBConnectionManager dbConnectionManager;


    public Optional<Cave> getById(Long id) throws SQLException {

        String sql = "SELECT * FROM Caves WHERE id = ?";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        Cave cave = null;
        if (resultSet.next()) cave = fromRow(resultSet);
        return Optional.ofNullable(cave);


    }

    public void create(Cave cave) throws SQLException {

        String sql = "INSERT INTO Caves (id) VALUES (?)";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, cave.getId());
        statement.execute();


    }


    private Cave fromRow(ResultSet resultSet) throws SQLException{

        Long caveId = resultSet.getLong("id");
        return new Cave(caveId);

    }

}
