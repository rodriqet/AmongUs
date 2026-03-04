package com.amongus.DAOImpl;

import com.amongus.DAO.SalaDAO;
import com.amongus.modelo.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalaDAOImpl implements SalaDAO {

    private Connection conexion;

    public SalaDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Sala sala){
        String sql = "INSERT INTO tarea (nombre, peso) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, sala.getNombre());
            ps.setInt(2, sala.getPeso());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }








}
