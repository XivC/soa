package ru.ifmo.soa.killer.repository;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.killer.db.DBConnectionManager;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.model.Team;

import java.sql.*;
import java.util.Optional;

@RequestScoped
public class TeamRepository {

    @Inject
    DBConnectionManager dbConnectionManager;


    public Optional<Team> getById(Long id) throws SQLException {

        String sql = "SELECT * FROM Teams LEFT JOIN Caves on Teams.start_cave_id = Caves.id WHERE Teams.id = ?";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        Team team = null;
        if (resultSet.next()) team = fromRow(resultSet);
        return Optional.ofNullable(team);


    }

    public void create(Team team) throws SQLException {

        String sql = "INSERT INTO teams (id, start_cave_id, name, size)  VALUES (?, ?, ?, ?)";
        Connection connection = dbConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, team.getId());
        statement.setLong(2, team.getStartCaveId());
        statement.setString(3, team.getName());
        statement.setInt(4, team.getSize());
        statement.execute();


    }


    private Team fromRow(ResultSet resultSet) throws SQLException{

        Long id = resultSet.getLong("teams.id");
        String name = resultSet.getString("name");
        Integer size = resultSet.getInt("size");
        Long caveId = resultSet.getLong("start_cave_id");

         Cave cave = new Cave(caveId);

        return Team.builder().id(id).size(size).name(name).startCave(cave).build();

    }

}
