package com.amongus.DAO;

import java.util.ArrayList;

public interface TripulanteDAO {
    void insertar(Tripulante);
    Tripulante obtener(int id);
    ArrayList<Tripulante> obtenerTodos();
    void actualizar(Tripulante);
    void eliminar(int id);
}
