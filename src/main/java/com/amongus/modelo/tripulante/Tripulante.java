package com.amongus.modelo.tripulante;

import com.amongus.DAO.TripulanteDAO;
import com.amongus.interfaces.Trabajable;
import com.amongus.interfaces.Votable;
import com.amongus.modelo.Nave;
import com.amongus.modelo.Tarea;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Tripulante implements Trabajable, Votable {

    private int id;
    static private int id_auto = 1;
    private String nombre;
    private String rol;
    private boolean vivo;

    public Tripulante(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.vivo = true;
        this.id = id_auto;
        id_auto ++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    @Override
    public void realizarTarea(Tarea tarea) {
        if (vivo) {
            tarea.setCompletada(true);
        }
    }

    public int votar(int numero) {
        Scanner sc = new Scanner(System.in);

        System.out.println("!Turno de voto de " + this.getNombre() + "!");
        System.out.print("A quien votas? (1-" + numero + ", 0 para skip): ");
        return sc.nextInt();
    }

    public void habilidadEspecial() {}

    @Override
    public String toString() {
        return "Tripulante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", vivo=" + vivo +
                '}';
    }
}
