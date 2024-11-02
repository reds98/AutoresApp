package com.mycompany.gestionautores.test;

import com.mycompany.gestionautores.dao.AutorDAO;
import com.mycompany.gestionautores.dao.AutorDAOImpl;
import com.mycompany.gestionautores.modelo.Autor;
import java.time.LocalDate;

public class TestAutor {
    public static void main(String[] args) {
        try {
            AutorDAO autorDAO = new AutorDAOImpl();
            
            // Crear un nuevo autor
            Autor nuevoAutor = new Autor(
                "Gabriel García Márquez",
                "Colombia",
                25,
                LocalDate.of(1927, 3, 6),
                LocalDate.of(1947, 12, 17)
            );
            
            // Insertar y obtener el ID
            int id = autorDAO.insertar(nuevoAutor);
            System.out.println("Autor insertado con ID: " + id);
            
            // Listar todos los autores
            System.out.println("\nListado de autores:");
            autorDAO.listarTodos().forEach(System.out::println);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}