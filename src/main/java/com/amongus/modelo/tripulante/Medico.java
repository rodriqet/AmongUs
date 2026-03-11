package com.amongus.modelo.tripulante;

public class Medico extends Tripulante {

    public Medico(String nombre) {
        super(nombre, "medico");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedes examinar tripulantes");
    }

    public void examinar(Tripulante tripulante) {
        if (tripulante.getRol().equals("impostor")) {
            System.out.println("El impostor es: " + tripulante.getNombre());
        } else {
            System.out.println("No es el impostor: " + tripulante.getNombre());
        }
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}
