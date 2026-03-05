package com.amongus.modelo;

import com.amongus.modelo.tripulante.Impostor;
import com.amongus.modelo.tripulante.Tripulante;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Tripulante> getTripulantesVivos() {
        ArrayList<Tripulante> tripulantesVivos = new ArrayList<>();
        for (Tripulante tripulante : tripulantes) {
            if (tripulante.isVivo()){
                if (!(tripulante instanceof Impostor)){
                    tripulantesVivos.add(tripulante);
                }
            }
        }
        return tripulantesVivos;
    }

    public int getImpostoresVivos() {
        return tripulantes.size() - getTripulantesVivos().size();
    }

    public void limpiarPantalla(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
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
        HashMap<String, Integer> votacion = new HashMap<>();
        for (Tripulante tripulante : getTripulantesVivos()) {
            tripulante.votar(tripulante);
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

    //• turno(): Turno completo: limpiar, mostrar info, menú, acción, victoria.
    public void turno() {












    }



}
