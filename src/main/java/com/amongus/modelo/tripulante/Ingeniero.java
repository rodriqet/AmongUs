package com.amongus.modelo.tripulante;

import com.amongus.modelo.Sala;

public class Ingeniero extends Tripulante {

    public Ingeniero(String nombre) {
        super(nombre, "ingeniero");
    }

    public void habilidadEspecial() {
        System.out.println("Puedes reparar salas");
    }

    public void repararSalas(Sala sala) {
        if (sala.isSaboteada()) {
            System.out.println("Se esta reparando...");
            sala.setSaboteada(true);
            System.out.println("La sala ya esta reparada");
        } else {
            System.out.println("No hay nada para reparar");
        }
    }
}
