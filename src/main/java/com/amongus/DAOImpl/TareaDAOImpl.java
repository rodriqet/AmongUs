package com.amongus.DAOImpl;

import com.amongus.DAO.TareaDAO;
import com.amongus.modelo.Nave;
import com.amongus.modelo.Sala;
import com.amongus.modelo.Tarea;
import com.amongus.modelo.tripulante.Tripulante;

import java.sql.*;
import java.util.ArrayList;

public class TareaDAOImpl implements TareaDAO {

    private Connection conexion;

    public TareaDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Tarea tarea){
        String sql = "INSERT INTO tarea (descripcion, completada, id_tripulante, id_sala) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tarea.getDescripcion());
            ps.setBoolean(2, tarea.isCompletada());
            ps.setInt(3, tarea.getTripulanteAsignado().getId());
            ps.setInt(4, tarea.getSala().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tarea obtener(int id){
        String sql = "SELECT * FROM tarea WHERE id = ? ";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tarea t = new Tarea("", null, null);
                    t.setId(rs.getInt("id"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setCompletada(rs.getBoolean("completada"));

                    TripulanteDAOImpl tripulanteDAO = new TripulanteDAOImpl(conexion);
                    t.setTripulanteAsignado(tripulanteDAO.obtener(rs.getInt("id_tripulante")));

                    SalaDAOImpl salaDAO = new SalaDAOImpl(conexion);
                    t.setSala(salaDAO.obtener(rs.getInt("id_sala")));

                    return t;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Tarea> obtenerTodos(){
        ArrayList<Tarea> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarea";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tarea t = new Tarea("", null, null);
                t.setId(rs.getInt("id"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setCompletada(rs.getBoolean("completada"));

                TripulanteDAOImpl tripulanteDAO = new TripulanteDAOImpl(conexion);
                t.setTripulanteAsignado(tripulanteDAO.obtener(rs.getInt("id_tripulante")));

                SalaDAOImpl salaDAO = new SalaDAOImpl(conexion);
                t.setSala(salaDAO.obtener(rs.getInt("id_sala")));

                lista.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public void actualizar(Tarea tarea){
        String sql = "UPDATE tarea SET descripcion = ?, completada = ?, id_tripulante = ?, id_sala = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(5, tarea.getId());
            ps.setString(1, tarea.getDescripcion());
            ps.setBoolean(2, tarea.isCompletada());
            ps.setInt(3, tarea.getTripulanteAsignado().getId());
            ps.setInt(4, tarea.getSala().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int id){
        String sql = "DELETE FROM tarea WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }








}
