package com.amongus.modelo.tripulante;

import com.amongus.interfaces.Saboteable;
import com.amongus.modelo.Sala;
import com.amongus.modelo.Tarea;

public class Impostor extends Tripulante implements Saboteable {

    public Impostor(String nombre) {
        super(nombre, "impostor");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedes sabotear y eliminar");
    }

    @Override
    public void sabotear(Sala sala) {
        sala.setSaboteada(true);
    }

    public void eliminar(Tripulante tripulante) {
        if (!tripulante.getRol().equals("impostor")) {
            tripulante.setVivo(false);
            System.out.println("El tripunlante " + tripulante.getNombre() + " esta muerto.");
        } else {
            System.out.println("No puedes eliminarte a ti mismo");
        }
    }

    public void realizarTarea(Tarea tarea) {
        tarea.setCompletada(false);
    }
}
