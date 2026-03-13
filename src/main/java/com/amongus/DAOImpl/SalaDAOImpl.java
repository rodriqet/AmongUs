package com.amongus.DAOImpl;

import com.amongus.DAO.SalaDAO;
import com.amongus.modelo.Sala;

import java.sql.*;
import java.util.ArrayList;

public class SalaDAOImpl implements SalaDAO {

    private Connection conexion;

    public SalaDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Sala sala){
        String sql = "INSERT INTO sala (nombre, saboteada) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, sala.getNombre());
            ps.setBoolean(2, sala.isSaboteada());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sala obtener(int id){
        String sql = "SELECT * FROM sala WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Sala s = new Sala("");
                    s.setId(rs.getInt("id"));
                    s.setNombre(rs.getString("nombre"));
                    s.setSaboteada(rs.getBoolean("saboteada"));
                    return s;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Sala> obtenerTodos(){
        ArrayList<Sala> lista = new ArrayList<>();
        String sql = "SELECT * FROM sala";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sala s = new Sala("");
                s.setId(rs.getInt("id"));
                s.setNombre(rs.getString("nombre"));
                s.setSaboteada(rs.getBoolean("saboteada"));
                lista.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public void actualizar(Sala sala){
        String sql = "UPDATE sala SET nombre = ?, saboteada = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(3, sala.getId());
            ps.setString(1, sala.getNombre());
            ps.setBoolean(2, sala.isSaboteada());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int id){
        String sql = "DELETE FROM sala WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
