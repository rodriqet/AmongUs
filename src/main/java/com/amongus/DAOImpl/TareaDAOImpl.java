package com.amongus.DAOImpl;

import com.amongus.DAO.TareaDAO;
import com.amongus.modelo.Tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TareaDAOImpl implements TareaDAO {

    private Connection conexion;

    public TareaDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Tarea tarea){
        String sql = "INSERT INTO tarea (nombre, peso) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tarea.getNombre());
            ps.setInt(2, tarea.getPeso());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }










}
