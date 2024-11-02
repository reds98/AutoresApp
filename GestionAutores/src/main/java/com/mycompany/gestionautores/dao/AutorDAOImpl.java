package com.mycompany.gestionautores.dao;

import com.mycompany.gestionautores.modelo.Autor;
import com.mycompany.gestionautores.util.ConexionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {
    
    @Override
    public int insertar(Autor autor) throws SQLException {
        String sql = "INSERT INTO Autor (Nombre, Pais, Publicaciones, FechaNacimiento, FechaPrimeraPublicacion) "
                  + "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getPais());
            stmt.setInt(3, autor.getPublicaciones());
            stmt.setDate(4, Date.valueOf(autor.getFechaNacimiento()));
            stmt.setDate(5, Date.valueOf(autor.getFechaPrimeraPublicacion()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }
    
    @Override
    public List<Autor> listarTodos() throws SQLException {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM Autor ORDER BY Nombre";
        
        try (Connection conn = ConexionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Autor autor = new Autor(
                    rs.getInt("IdAutor"),
                    rs.getString("Nombre"),
                    rs.getString("Pais"),
                    rs.getInt("Publicaciones"),
                    rs.getDate("FechaNacimiento").toLocalDate(),
                    rs.getDate("FechaPrimeraPublicacion").toLocalDate()
                );
                autores.add(autor);
            }
        }
        return autores;
    }
    
    @Override
    public Autor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Autor WHERE IdAutor = ?";
        
        try (Connection conn = ConexionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Autor(
                        rs.getInt("IdAutor"),
                        rs.getString("Nombre"),
                        rs.getString("Pais"),
                        rs.getInt("Publicaciones"),
                        rs.getDate("FechaNacimiento").toLocalDate(),
                        rs.getDate("FechaPrimeraPublicacion").toLocalDate()
                    );
                }
            }
        }
        return null;
    }
    
    @Override
    public void actualizar(Autor autor) throws SQLException {
        String sql = "UPDATE Autor SET Nombre = ?, Pais = ?, Publicaciones = ?, "
                  + "FechaNacimiento = ?, FechaPrimeraPublicacion = ? WHERE IdAutor = ?";
        
        try (Connection conn = ConexionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getPais());
            stmt.setInt(3, autor.getPublicaciones());
            stmt.setDate(4, Date.valueOf(autor.getFechaNacimiento()));
            stmt.setDate(5, Date.valueOf(autor.getFechaPrimeraPublicacion()));
            stmt.setInt(6, autor.getIdAutor());
            
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Autor WHERE IdAutor = ?";
        
        try (Connection conn = ConexionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}