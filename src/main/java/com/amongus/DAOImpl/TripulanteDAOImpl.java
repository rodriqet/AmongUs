package com.amongus.DAOImpl;

import com.amongus.DAO.TripulanteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TripulanteDAOImpl extends TripulanteDAO {

    private Connection conexion;

    public TripulanteDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar() {
        String sql = "INSERT INTO tripulante (nombre, rol, vivo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            //ps.setString(1, tripulante.getnombre());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tripulante obtener(int id) {
        return null;
    }

    @Override
    public ArrayList<Tripulante> obtenerTodos() {
        return null;
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void eliminar(int id) {

    }
}
