package com.amongus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;

public class DBUtil {

    private static String DB_URL;

    private static String USER;

    private static String PASS;

    private static DBUtil instanciaDB;

    private Connection conexion;

    private DBUtil() {
        try {
            DB_URL = "jdbc:mysql://localhost:3306/nave_espacial"; //Rodri: 3307
            USER = "root";
            PASS = "";

            conexion = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexión establecida correctamente");

            try (Statement stmt = conexion.createStatement()) {
                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

                stmt.executeUpdate("TRUNCATE TABLE sala");
                stmt.executeUpdate("TRUNCATE TABLE tarea");
                stmt.executeUpdate("TRUNCATE TABLE tripulante");

                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

                System.out.println("Base de datos limpia y lista para el juego.");
            } catch (SQLException e) {
                System.out.println("Base de datos no se ha limpiado.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    public static DBUtil getInstance() {
        if (instanciaDB == null) {
            instanciaDB = new DBUtil();
        }
        return instanciaDB;
    }

    public Connection getConexion() {
        return conexion;
    }


}
