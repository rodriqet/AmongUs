package com.amongus.modelo.tripulante;

public class Ingeniero extends Tripulante {
    public Ingeniero(String nombre) {

    }

    public void habilidadEspecial() {
        System.out.println("Puedes reparar salas");
    }

    public void repararSalas(Sala sala) {
        if (sala.isSaboteada()) {
            System.out.println("Se esta reparando...");
            sala.setSaboteada(True);
            System.out.println("La sala ya esta reparada");
        } else {
            System.out.println("No hay nada para reparar");
        }
    }
}
