package com.amongus.modelo;

import java.util.ArrayList;

public class Nave {
    private ArrayList<Tripulante> tripulantes;

    private ArrayList<Sala> salas;

    private ArrayList<Tarea> tareas;

    public Nave(ArrayList<Tripulante> tripulantes, ArrayList<Sala> salas){
        this.tripulantes = tripulantes;
        this.salas = salas;
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

//• agregarTarea(Tarea): Añade una tarea.
//• limpiarPantalla(): Ejecuta System.out.print("\033[H\033[2J") + flush().
//• mostrarEstadoNave(): Muestra tripulantes, salas y progreso de tareas.
//• iniciarVotacion(): Gestiona la votación con limpieza de pantalla entre votos.
//• verificarVictoriaTripulantes(): boolean — true si tareas completas o impostor
//expulsado.
//• verificarVictoriaImpostor(): boolean — true si impostores vivos ≥ normales vivos.
//• turno(): Turno completo: limpiar, mostrar info, menú, acción, victoria.









}
