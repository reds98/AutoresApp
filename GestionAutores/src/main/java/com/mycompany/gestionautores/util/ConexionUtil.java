package com.mycompany.gestionautores.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionUtil {
    // Si usas SQL Express, usa "localhost\\SQLEXPRESS"
    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;"
            + "databaseName=DBAutores;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private static final String USER = "usuario_crud";
    private static final String PASS = "Crud2024#";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexión exitosa a la base de datos!");
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}