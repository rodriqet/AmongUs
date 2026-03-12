package com.amongus.DAOImpl;

import com.amongus.DAO.TripulanteDAO;
import com.amongus.modelo.tripulante.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TripulanteDAOImpl implements TripulanteDAO {

    private Connection conexion;

    public TripulanteDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Tripulante tripulante) {
        String sql = "INSERT INTO tripulante (nombre, rol, vivo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tripulante.getNombre());
            ps.setString(2, tripulante.getRol());
            ps.setBoolean(3, tripulante.isVivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tripulante obtener(int id) {
        String sql = " SELECT * FROM tripulante WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String rol = rs.getString("rol");

                    Tripulante tripulante = null;

                    if (rol.equals("Impostor")) {
                        Tripulante triulante = new Impostor(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    } else if (rol.equals("Capitan")) {
                        Tripulante triulante = new Capitan(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    } else if (rol.equals("Ingeniero")) {
                        Tripulante triulante = new Ingeniero(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    } else if (rol.equals("Medico")) {
                        Tripulante triulante = new Medico(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    }
                    return tripulante;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Tripulante> obtenerTodos() {
        ArrayList<Tripulante> tripulantes = new ArrayList<>();
        String sql = " SELECT * FROM tripulante";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String rol = rs.getString("rol");

                    Tripulante tripulante = null;

                    if (rol.equals("Impostor")) {
                        Tripulante triulante = new Impostor(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    } else if (rol.equals("Capitan")) {
                        Tripulante triulante = new Capitan(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    } else if (rol.equals("Ingeniero")) {
                        Tripulante triulante = new Ingeniero(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    } else if (rol.equals("Medico")) {
                        Tripulante triulante = new Medico(nombre);
                        triulante.setVivo(rs.getBoolean("vivo"));
                    }

                    tripulantes.add(tripulante);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tripulantes;
    }

    @Override
    public void actualizar(Tripulante tripulante) {
        String sql = "UPDATE tripulante SET nombre=?, rol=?, vivo=? WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tripulante.getNombre());
            ps.setString(2, tripulante.getRol());
            ps.setBoolean(3, tripulante.isVivo());
            ps.setInt(4, tripulante.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE tripulante WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
