package com.amongus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static String DB_URL;

    private static String USER;

    private static String PASS;

    private static DBUtil instanciaDB;

    private Connection conexion;

    private DBUtil() {
        try {
            String url = "jdbc:mysql://localhost:3306/nave_espacial"; //Rodri: 3307
            String usuario = "root";
            String password = "";

            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión establecida correctamente");

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
