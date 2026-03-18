package com.amongus;

import com.amongus.DAO.SalaDAO;
import com.amongus.DAO.TareaDAO;
import com.amongus.DAO.TripulanteDAO;
import com.amongus.DAOImpl.SalaDAOImpl;
import com.amongus.DAOImpl.TareaDAOImpl;
import com.amongus.DAOImpl.TripulanteDAOImpl;
import com.amongus.modelo.Nave;
import com.amongus.modelo.Sala;
import com.amongus.modelo.Tarea;
import com.amongus.modelo.tripulante.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=".repeat(50));
        System.out.println("   🚀  AMONG US TERMINAL - NAVE ESPACIAL  🚀");
        System.out.println("=".repeat(50));
        System.out.println("\033[32m");

        try (Connection conexion = DBUtil.getInstance().getConexion()) {
            TripulanteDAO tripulanteDAO = new TripulanteDAOImpl(conexion);
            TareaDAO tareaDAO = new TareaDAOImpl(conexion);
            SalaDAO salaDAO = new SalaDAOImpl(conexion);

            System.out.println("\033[0m");

            int numTripulantes = 0;
            while (numTripulantes < 4 || numTripulantes > 10) {
                System.out.print("Cuantos jugadores van a jugar? (mínimo 4, máximo 10): ");
                numTripulantes = sc.nextInt();
                sc.nextLine();
            }

            ArrayList<String> jugadores = new ArrayList<>();
            for (int i = 0; i < numTripulantes; i++) {
                System.out.print("El " + (i + 1) + " jugador como se llama: ");
                String nombre = sc.nextLine();
                jugadores.add(nombre);
            }

            ArrayList<Tripulante> tripulantes = new ArrayList<>();

            Collections.shuffle(jugadores);
            Capitan capitan = new Capitan(jugadores.removeFirst());
            tripulantes.add(capitan);
            Collections.shuffle(jugadores);
            Impostor impostor = new Impostor(jugadores.removeFirst());
            tripulantes.add(impostor);

            int tripul = 1;
            for (String jugador : jugadores) {
                if (tripul%2 == 0) {
                    Medico medico = new Medico(jugador);
                    tripulantes.add(medico);
                } else {
                    Ingeniero ingeniero = new Ingeniero(jugador);
                    tripulantes.add(ingeniero);
                }
                tripul ++;
            }

            ArrayList<Sala> salas = new ArrayList<>();
            ArrayList<String> NomSalas = new ArrayList<>();
            Collections.addAll(NomSalas, "Reactor", "Cafeteria", "Navegación", "Electricidad", "Armamento", "Comunicaciones");

            for (int i = 0; i < NomSalas.size(); i++) {
                Sala sala = new Sala(NomSalas.get(i));
                salas.add(sala);
            }

            for (int i = 0; i < tripulantes.size(); i++) {
                tripulanteDAO.insertar(tripulantes.get(i));
            }

            for (int i = 0; i < salas.size(); i++) {
                salaDAO.insertar(salas.get(i));
            }

            Nave nave = new Nave(tripulantes, salas);

            HashMap<String, Sala> tareaDesc = new HashMap<>();
            // 0: Reactor, 1: Cafeteria, 2: Navegación, 3: Electricidad, 4: Armamento, 5: Comunicaciones

            // --- REACTOR (Índice 0) ---
            tareaDesc.put("Arrancar reactor", salas.get(0));
            tareaDesc.put("Desbloquear colectores", salas.get(0));
            tareaDesc.put("Desviar energía al reactor", salas.get(0));

            // --- CAFETERÍA (Índice 1) ---
            tareaDesc.put("Vaciar basurero", salas.get(1));
            tareaDesc.put("Subir datos de cafetería", salas.get(1));
            tareaDesc.put("Arreglar cableado de cafetería", salas.get(1));

            // --- NAVEGACIÓN (Índice 2) ---
            tareaDesc.put("Estabilizar dirección", salas.get(2));
            tareaDesc.put("Alinear mapa", salas.get(2));
            tareaDesc.put("Trazar rumbo espacial", salas.get(2));
            tareaDesc.put("Descargar datos de navegación", salas.get(2));

            // --- ELECTRICIDAD (Índice 3) ---
            tareaDesc.put("Calibrar distribuidor", salas.get(3));
            tareaDesc.put("Desviar energía principal", salas.get(3));
            tareaDesc.put("Restablecer disyuntores", salas.get(3));
            tareaDesc.put("Arreglar cableado de electricidad", salas.get(3));
            tareaDesc.put("Descargar datos de electricidad", salas.get(3));

            // --- ARMAMENTO (Índice 4) ---
            tareaDesc.put("Destruir asteroides", salas.get(4));
            tareaDesc.put("Calibrar cañones", salas.get(4));
            tareaDesc.put("Descargar datos de armamento", salas.get(4));

            // --- COMUNICACIONES (Índice 5) ---
            tareaDesc.put("Reiniciar router WiFi", salas.get(5));
            tareaDesc.put("Descargar datos de comunicaciones", salas.get(5));

            int seleccion1 = 0;
            int seleccion2 = 1;
            ArrayList<String> keys = new ArrayList<> (tareaDesc.keySet()) ;
            for (Tripulante tripulante : tripulantes) {
                Tarea tarea1 = new Tarea(keys.get(seleccion1), tripulante, tareaDesc.get(keys.get(seleccion1)) );
                Tarea tarea2 = new Tarea(keys.get(seleccion2), tripulante, tareaDesc.get(keys.get(seleccion1)) );
                nave.agregarTarea(tarea1);
                nave.agregarTarea(tarea2);
                seleccion1 ++;
                seleccion2 ++;
            }

            for (int i = 0; i < nave.getTareas().size(); i++) {
                tareaDAO.insertar(nave.getTareas().get(i));
            }

            while (!nave.verificarVictoriaImpostor() || !nave.verificarVictoriaTripulantes()) {
                nave.turno();
            }

            if (nave.verificarVictoriaTripulantes()) {
                System.out.println("============================================\n" +
                        "        🎉  FIN DE LA PARTIDA  🎉\n" +
                        "============================================");
                System.out.println();
                System.out.println("¡VICTORIA DE LOS TRIPULANTES!");

                System.out.print("El impostor ");
                for (int i = 0; i < tripulantes.size(); i++) {
                    if (Objects.equals(tripulantes.get(i).getRol(), "Impostor")) {
                        System.out.print(tripulantes.get(i).getNombre());
                    }
                }
                System.out.println(" ha sido expulsado.");
                System.out.println("La nave esta a salvo.");
                System.out.println();
                System.out.println("=== RESUMEN FINAL ===");
                System.out.println("Tripulantes supervivientes:");
                for (int i = 0; i < tripulantes.size(); i++) {
                    Tripulante trip = tripulantes.get(i);

                    String estado;
                    if (trip.getRol().equalsIgnoreCase("Impostor")) {
                        estado = "Expulsado";
                    } else {
                        if (trip.isVivo()) {
                            estado = "Vivo";
                        } else {
                            estado = "Muerto";
                        }
                    }

                    System.out.printf("[%d] %-15s - %-10s - %s%n", i, trip.getNombre(), trip.getRol(), estado);
                }

                int tareasSinCompletar = 0;
                for (int i = 0; i < nave.getTareas().size(); i++) {
                    if (!nave.getTareas().get(i).isCompletada()) {
                        tareasSinCompletar ++;
                    }
                }

                System.out.println();
                System.out.println("Tareas completadas: " + tareasSinCompletar + "/" + nave.getTareas().size());
            }

            if (nave.verificarVictoriaImpostor()) {
                System.out.println("============================================\n" +
                        "       💀  FIN DE LA PARTIDA  💀 \n" +
                        "============================================");
                System.out.println();
                System.out.println("¡VICTORIA DEL IMPOSTOR!\n");

                System.out.println(impostor.getNombre() + "(Impostor) ha conseguido eliminar a\n" +
                        "suficientes tripulantes. La nave ha caido.");

                System.out.println();
                System.out.println("=== RESUMEN FINAL ===");
                System.out.println("Tripulantes supervivientes:");
                for (int i = 0; i < tripulantes.size(); i++) {
                    Tripulante trip = tripulantes.get(i);

                    String estado;
                    if (trip.getRol().equalsIgnoreCase("Impostor")) {
                        estado = "VIVO  😈";
                    } else {
                        estado = "Eliminado";
                    }

                    System.out.printf("[%d] %-15s - %-10s - %s%n", i, trip.getNombre(), trip.getRol(), estado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Gracias por jugar!");
    }
}