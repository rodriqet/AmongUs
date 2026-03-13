package com.amongus.DAO;

import com.amongus.modelo.Sala;
import com.amongus.modelo.Tarea;
import com.amongus.modelo.tripulante.Tripulante;

import java.util.ArrayList;

public interface TareaDAO {


    void insertar(Tarea tarea);

    Tarea obtener(int id);

    ArrayList<Tarea> obtenerTodos();

    void actualizar(Tarea tarea);

    void eliminar(int id);
}
