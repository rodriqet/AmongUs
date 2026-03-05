package com.amongus.modelo.tripulante;

import com.amongus.modelo.Nave;

public class Capitan extends Tripulante {

    public Capitan(String nombre) {
        super(nombre, "capitan");
    }

    public void habilidadEspecial() {
        System.out.println("Puedes convocar votaciones");
    }

    public void convocarVotacion(Nave nave) {
        nave.iniciarVotacion();
    }
}
