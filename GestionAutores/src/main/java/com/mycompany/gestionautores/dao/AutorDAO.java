package com.mycompany.gestionautores.dao;

import com.mycompany.gestionautores.modelo.Autor;
import java.util.List;
import java.sql.SQLException;

public interface AutorDAO {
    // Create
    int insertar(Autor autor) throws SQLException;
    
    // Read
    List<Autor> listarTodos() throws SQLException;
    Autor buscarPorId(int id) throws SQLException;
    
    // Update
    void actualizar(Autor autor) throws SQLException;
    
    // Delete
    void eliminar(int id) throws SQLException;
}