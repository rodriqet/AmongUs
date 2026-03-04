package com.amongus.modelo;

import com.amongus.modelo.tripulante.Impostor;
import com.amongus.modelo.tripulante.Tripulante;

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

    public void agregarTarea(Tarea tarea){
        tareas.add(tarea);
    }

    public void limpiarPantalla(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void mostrarEstadoNave(){
        for (Tripulante tripulante : tripulantes) {
            System.out.println(tripulante.toString());
        }
        for (Sala sala : salas) {
            System.out.println(sala.toString());
        }
        for (Tarea tarea : tareas) {
            System.out.println(tarea.toString());
        }
    }

    //• iniciarVotacion(): Gestiona la votación con limpieza
    // de pantalla entre votos.
    public void iniciarVotacion(){

    }

    public boolean verificarVictoriaTripulantes(){
        boolean victoriaPorExpulsion = false;

        for (Tripulante impostor : tripulantes) {
            if (impostor instanceof Impostor){
                victoria = false;
                break;
            }
        }


        if (tareas.isEmpty()){
            victoria = true;
        }
    }



//• verificarVictoriaTripulantes(): boolean — true si tareas completas o impostor
//expulsado.
//• verificarVictoriaImpostor(): boolean — true si impostores vivos ≥ normales vivos.
//• turno(): Turno completo: limpiar, mostrar info, menú, acción, victoria.









}
