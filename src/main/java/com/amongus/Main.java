package com.amongus;

import com.amongus.DAO.SalaDAO;
import com.amongus.DAO.TareaDAO;
import com.amongus.DAO.TripulanteDAO;
import com.amongus.DAOImpl.SalaDAOImpl;
import com.amongus.DAOImpl.TareaDAOImpl;
import com.amongus.DAOImpl.TripulanteDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=".repeat(40));
        System.out.println("   🚀  AMONG US TERMINAL - NAVE ESPACIAL  🚀");
        System.out.println("=".repeat(40));

        try (Connection conexion = DBUtil.getInstance().getConexion()) {
            TripulanteDAO tripulanteDAO = new TripulanteDAOImpl(conexion);
            TareaDAO tareaDAO = new TareaDAOImpl(conexion);
            SalaDAO salaDAO = new SalaDAOImpl(conexion);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}