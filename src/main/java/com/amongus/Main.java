package com.amongus;

import com.amongus.DAO.SalaDAO;
import com.amongus.DAO.TareaDAO;
import com.amongus.DAO.TripulanteDAO;
import com.amongus.DAOImpl.SalaDAOImpl;
import com.amongus.DAOImpl.TareaDAOImpl;
import com.amongus.DAOImpl.TripulanteDAOImpl;
import com.amongus.modelo.Nave;
import com.amongus.modelo.Sala;
import com.amongus.modelo.tripulante.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
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

        int numTripulantes = 0;
        while (numTripulantes >= 4 && numTripulantes <= 10) {
            System.out.print("Cuantos jugadores van a jugar? (mínimo 4, máximo 10): ");
            numTripulantes = sc.nextInt();
            sc.nextLine();
        }

        ArrayList<String> jugadores = new ArrayList<>();
        for (int i = 0; i < numTripulantes; i++) {
            System.out.print("El " + i + " jugador como se llama: ");
            jugadores.add(sc.nextLine());
        }

        ArrayList<Tripulante> tripulantes = new ArrayList<>();

        Collections.shuffle(jugadores);
        Capitan capitan = new Capitan(jugadores.removeFirst());
        tripulantes.add(capitan);
        Collections.shuffle(jugadores);
        Impostor impostor = new Impostor(jugadores.removeFirst());
        tripulantes.add(impostor);

        for (int i = 0; i < tripulantes.size(); i++) {
            if (i%2 == 0) {
                Medico medico = new Medico(jugadores.removeFirst());
                tripulantes.add(medico);
            } else {
                Ingeniero ingeniero = new Ingeniero(jugadores.removeFirst());
                tripulantes.add(ingeniero);
            }
        }

        ArrayList<Sala> salas = new ArrayList<>();
        ArrayList<String> NomSalas = new ArrayList<>();
        Collections.addAll(NomSalas, "Reactor", "Cafeteria", "Navegación", "Electricidad", "Armamento", "Comunicaciones");

        for (int i = 0; i < NomSalas.size(); i++) {
            Sala sala = new Sala(NomSalas.get(i));
            salas.add(sala);
        }

        //TODO Persistencia inicial: Insertar tripulantes y salas en BBDD

        //TODO Generación de tareas: Crear y asignar tareas. Mínimo 2 por tripulante. Persistir.

        Nave nave = new Nave(tripulantes, salas);
        while (nave.verificarVictoriaImpostor() || nave.verificarVictoriaTripulantes()) {
            nave.turno();
        }

        //TODO Al finalizar, actualizar estado en BBDD y mostrar resumen.
    }
}