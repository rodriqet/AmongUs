package com.amongus.modelo;

import com.amongus.modelo.tripulante.*;

import java.util.*;

public class Nave {

    private ArrayList<Tripulante> tripulantes;

    private ArrayList<Sala> salas;

    private ArrayList<Tarea> tareas;

    public Nave(ArrayList<Tripulante> tripulantes, ArrayList<Sala> salas){
        this.tripulantes = tripulantes;
        this.salas = salas;
        this.tareas = new ArrayList<>();
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulantes;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void agregarTarea(Tarea tarea){
        tareas.add(tarea);
    }

    public ArrayList<Tripulante> getTripulantesVivos() {
        ArrayList<Tripulante> tripulantesVivos = new ArrayList<>();
        for (Tripulante tripulante : tripulantes) {
            if (tripulante.isVivo()){
                tripulantesVivos.add(tripulante);
            }
        }
        return tripulantesVivos;
    }

    public int getImpostoresVivos() {
        return tripulantes.size() - getTripulantesVivos().size();
    }

    public void limpiarPantalla(){
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public void mostrarEstadoNave(){
        System.out.println("ESTADO DE LA NAVE 🚀👩‍🚀");
        System.out.println("\n\033[34mTripulantes vivos en la nave:\033[0m");
        for (Tripulante tripulante : getTripulantesVivos()) {
            System.out.println(tripulante.getNombre());
        }
        System.out.println("\n\033[34mSalas de la nave:\033[0m");
        for (Sala sala : salas) {
            if (sala.isSaboteada()) {
                System.out.println(sala.getNombre() + " saboteado");
            } else {
                System.out.println(sala.getNombre() + " en funcionamiento");
            }
        }
        System.out.println("\n\033[34mTareas pendientes:\033[0m");
        for (Tarea tarea : tareas) {
            if (!tarea.isCompletada()) {
                System.out.println(tarea.getDescripcion());
            }
        }
    }

    public void iniciarVotacion(){
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> votacion = new HashMap<>();
        int tripulanteVotado = 0;

        for (Tripulante tripulante : getTripulantesVivos()) {
            limpiarPantalla();
            for (Tripulante tripulante1 : getTripulantesVivos()) {
                System.out.println(tripulante1.getId() + ") " + tripulante1.getNombre());
            }
            votacion.put(tripulante.getNombre(), 0);
            tripulanteVotado = tripulante.votar(getTripulantesVivos().size());
            votacion.put(String.valueOf(getTripulantesVivos().get(tripulanteVotado)), votacion.get(tripulanteVotado) + 1);
            limpiarPantalla();
        }

        System.out.println(" === RESULTADO DE LA VOTACIÓN === ");

        int maxVotos = Collections.max(votacion.values());

        List<String> masVotados = new ArrayList<>();
        for (Map.Entry<String, Integer> tripulantesVotacion : votacion.entrySet()) {
            if (tripulantesVotacion.getValue() == maxVotos) {
                masVotados.add(tripulantesVotacion.getKey());
            }
        }

        boolean hayEmpate = masVotados.size() > 1;

        System.out.println("=== RESULTADO DE LA VOTACION ===");

        for (Map.Entry<String, Integer> entry : votacion.entrySet()) {
            String nombre = entry.getKey();
            int votos = entry.getValue();

            if (masVotados.contains(nombre) && !hayEmpate) {
                System.out.println(nombre + ": " + votos + " votos EXPULSADO ⬅️");
            } else if (masVotados.contains(nombre) && hayEmpate) {
                System.out.println(nombre + ": " + votos + " votos EMPATE ⚠️");
            } else {
                System.out.println(nombre + ": " + votos + " votos");
            }
        }

        if (hayEmpate) {
            System.out.print("\nEmpataron: ");
            System.out.println(masVotados);
            System.out.println("Nadie es expulsado");
        } else {
            String expulsado = masVotados.getFirst();
            System.out.println("\n" + expulsado + " ha sido expulsado de la nave");

            Tripulante encontrado = null;
            for (Tripulante t : tripulantes) {
                if (Objects.equals(expulsado, t.getNombre())){
                    encontrado = t;
                }
            }
            if (encontrado instanceof Impostor) {
                System.out.println("¡" + expulsado + " era el impostor! 🎉");
            } else {
                System.out.println(expulsado + " era un tripulante inocente 😥😭");
            }
        }


    }

    public boolean verificarVictoriaTripulantes(){
        boolean victoriaPorExpulsion = true;
        if (tareas.isEmpty()){
            return true;
        }
        for (Tripulante tripulante : tripulantes) {
            if (tripulante instanceof Impostor && tripulante.isVivo()){
                victoriaPorExpulsion = false;
            }
        }
        return victoriaPorExpulsion;
    }

    public boolean verificarVictoriaImpostor() {
        return getImpostoresVivos() >= getTripulantesVivos().size();
    }

    public void turno() {
        Scanner sc = new Scanner(System.in);

        for (Tripulante tripulante : getTripulantesVivos()){
            limpiarPantalla();
            System.out.println("¡Pasa el ordenador a "+ tripulante.getNombre() +"!");
            System.out.println("Pulsa enter cuando estes listo\n");
            sc.nextLine();

            System.out.println("-".repeat(50));
            System.out.println("TURNO DE " + tripulante.getNombre());
            System.out.println("Tu rol secreto: " + tripulante.getRol());
            System.out.println("-".repeat(50) + "\n");

            mostrarEstadoNave();
            System.out.println();

            //Menú del impostor
            if (tripulante instanceof Impostor) {
                System.out.println("¿Que quieres hacer?");
                System.out.println(" 1) Realizar tarea");
                System.out.println(" 2) Sabotear una sala");
                System.out.println(" 3) Eliminar a un tripulante");
                System.out.println(" 4) Pasar turno");
                System.out.print("Elige opcion: ");
                int opcion = 0;
                boolean numero = false;
                while (!numero || opcion < 1 || opcion > 4) {
                    try {
                        opcion = sc.nextInt();
                        if (opcion < 1 || opcion > 4) {
                            System.out.println("Error: debes introducir un número del 1 al 4.");
                        }
                        numero = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Error: debes introducir un número.");
                    }
                }

                switch (opcion) {
                    case 1:
                        int opcion2 = 0;
                        System.out.println("¿Qué tarea quieres realizar?");
                        for (Tarea tarea : tareas) {
                            System.out.println(tarea.getDescripcion());
                        }
                        boolean numero2 = false;
                        while (!numero2 || opcion2 < 1 || opcion2 > tareas.size()) {
                            try {
                                opcion2 = sc.nextInt();
                                if (opcion2 < 1 || opcion2 > tareas.size()) {
                                    System.out.println("Error: debes introducir un número del 1 al " + tareas.size() + ".");
                                }
                                numero2 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Error: debes introducir un número.");
                            }
                        }
                        tripulante.realizarTarea(tareas.get(opcion2 - 1));
                        System.out.println("Tarea " + tareas.get(opcion2 - 1).getDescripcion() + "completada!");
                        break;
                    case 2:
                        int opcion3 = 0;
                        System.out.println("¿Qué sala quieres sabotear?");
                        for (Sala sala : salas) {
                            System.out.println(sala.getNombre());
                        }
                        boolean numero3 = false;
                        while (!numero3 || opcion3 < 1 || opcion3 > salas.size()) {
                            try {
                                opcion3 = sc.nextInt();
                                if (opcion3 < 1 || opcion3 > salas.size()) {
                                    System.out.println("Error: debes introducir un número del 1 al " + salas.size() + ".");
                                }
                                numero3 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Error: debes introducir un número.");
                            }
                        }
                        ((Impostor) tripulante).sabotear(salas.get(opcion3 - 1));
                        System.out.println("Sala " + salas.get(opcion3 - 1).getNombre() + "saboteada!");
                        break;
                    case 3:
                        int opcion4 = 0;
                        System.out.println("¿Qué tripulante quieres eliminar?");
                        int seleccio = 1;
                        Impostor impostor = (Impostor) tripulante;
                        getTripulantesVivos().remove(impostor);
                        for (Tripulante tripulanteVivo : getTripulantesVivos()) {
                            System.out.println("[" + seleccio + "] " + tripulanteVivo.getNombre());
                            seleccio ++;
                        }
                        boolean numero4 = false;
                        while (!numero4 || opcion4 < 1 || opcion4 > getTripulantesVivos().size()) {
                            try {
                                opcion4 = sc.nextInt();
                                if (opcion4 < 1 || opcion4 > getTripulantesVivos().size()) {
                                    System.out.println("Error: debes introducir un número del 1 al " + getTripulantesVivos().size() + ".");
                                }
                                numero4 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Error: debes introducir un número.");
                            }
                        }
                        ((Impostor) tripulante).eliminar(getTripulantesVivos().get(opcion4 - 1));
                        getTripulantesVivos().add(impostor);
                        break;
                    case 4:
                        System.out.println("Pasas el turno al siguiente jugador");
                        break;
                }
            } else {
                //Menú de Capitan, Ingeniero, Médico
                ArrayList<Tarea> tareasTripulante = new ArrayList<>();
                System.out.println("Tus tareas pendientes:");
                int numeroTarea = 1;
                for (Tarea tarea : tareas) {
                    if (tripulante == tarea.getTripulanteAsignado() && !tarea.isCompletada()){
                        System.out.println("[" + numeroTarea + "] " + tarea.getDescripcion() + " - " + tarea.getSala().getNombre());
                        numeroTarea++;
                        tareasTripulante.add(tarea);
                    }
                }

                System.out.println("¿Que quieres hacer?");
                System.out.println(" 1) Realizar tarea");
                System.out.print(" 2) Usar habilidad especial: ");
                tripulante.habilidadEspecial();
                System.out.println(" 3) Pasar turno");
                System.out.print("Elige opcion: ");
                int opcion = 0;
                boolean numero = false;
                while (!numero || opcion < 1 || opcion > 3) {
                    try {
                        opcion = sc.nextInt();
                        if (opcion < 1 || opcion > 3) {
                            System.out.println("Error: debes introducir un número del 1 al 3.");
                        }
                        numero = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Error: debes introducir un número.");
                    }
                }

                switch (opcion) {
                    case 1:
                        int opcion2 = 0;
                        System.out.println("¿Qué tarea quieres realizar?");
                        for (Tarea tarea : tareasTripulante) {
                            System.out.println(tarea.getDescripcion());
                        }
                        boolean numero2 = false;
                        while (!numero2 || opcion2 < 1 || opcion2 > tareas.size()) {
                            try {
                                opcion2 = sc.nextInt();
                                if (opcion2 < 1 || opcion2 > tareas.size()) {
                                    System.out.println("Error: debes introducir un número del 1 al " + tareas.size() + ".");
                                }
                                numero2 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Error: debes introducir un número.");
                            }
                        }
                        tripulante.realizarTarea(tareas.get(opcion2 - 1));
                        System.out.println("Tarea " + tareas.get(opcion2 - 1).getDescripcion() + "completada!");
                        break;
                    case 2:
                        if (tripulante instanceof Medico) {
                            int opcion3 = 0;
                            Medico medico = (Medico) tripulante;
                            System.out.println("¿Qué tripulante quieres examinar?");
                            int seleccion = 1;
                            getTripulantesVivos().remove(medico);
                            for (Tripulante tripulanteVivo : getTripulantesVivos()) {
                                System.out.println("[" + seleccion + "] " + tripulanteVivo.getNombre());
                                seleccion ++;
                            }
                            boolean numero3 = false;
                            while (!numero3 || opcion < 1 || opcion > getTripulantesVivos().size()) {
                                try {
                                    opcion3 = sc.nextInt();
                                    if (opcion3 < 1 || opcion3 > getTripulantesVivos().size()) {
                                        System.out.println("Error: debes introducir un número del 1 al " + getTripulantesVivos().size() + ".");
                                    }
                                    numero3 = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Error: debes introducir un número.");
                                }
                            }
                            medico.examinar(getTripulantesVivos().get(opcion3 - 1));
                            getTripulantesVivos().add(medico);
                        } else if (tripulante instanceof Ingeniero) {
                            Ingeniero ingeniero = (Ingeniero) tripulante;
                            int opcion4 = 0;
                            System.out.println("¿Qué sala quieres reparar?");
                            for (Sala sala : salas) {
                                System.out.println(sala.getNombre());
                            }
                            boolean numero3 = false;
                            while (!numero3 || opcion < 1 || opcion > salas.size()) {
                                try {
                                    opcion4 = sc.nextInt();
                                    if (opcion4 < 1 || opcion4 > salas.size()) {
                                        System.out.println("Error: debes introducir un número del 1 al " + salas.size() + ".");
                                    }
                                    numero3 = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Error: debes introducir un número.");
                                }
                            }
                            ingeniero.repararSalas(salas.get(opcion4 - 1));
                        } else {
                            Capitan capitan = (Capitan) tripulante;
                            capitan.convocarVotacion(this);
                        }
                        break;
                    case 3:
                        System.out.println("Pasas el turno al siguiente jugador");
                        break;
                }
            }

            System.out.println("Pulsa Enter para pasar al siguiente turno...\n");
            sc.nextLine();
        }
    }



}
