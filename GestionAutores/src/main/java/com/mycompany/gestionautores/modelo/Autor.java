package com.mycompany.gestionautores.modelo;

import java.time.LocalDate;

public class Autor {
    private Integer idAutor;
    private String nombre;
    private String pais;
    private int publicaciones;
    private LocalDate fechaNacimiento;
    private LocalDate fechaPrimeraPublicacion;
    
    // Constructor vacío
    public Autor() {
    }
    
    // Constructor con todos los campos excepto ID (útil para insertar)
    public Autor(String nombre, String pais, int publicaciones, 
                 LocalDate fechaNacimiento, LocalDate fechaPrimeraPublicacion) {
        this.nombre = nombre;
        this.pais = pais;
        this.publicaciones = publicaciones;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaPrimeraPublicacion = fechaPrimeraPublicacion;
    }
    
    // Constructor con todos los campos (útil para leer de la BD)
    public Autor(Integer idAutor, String nombre, String pais, int publicaciones, 
                 LocalDate fechaNacimiento, LocalDate fechaPrimeraPublicacion) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.pais = pais;
        this.publicaciones = publicaciones;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaPrimeraPublicacion = fechaPrimeraPublicacion;
    }
    
    // Getters y Setters
    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(int publicaciones) {
        this.publicaciones = publicaciones;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaPrimeraPublicacion() {
        return fechaPrimeraPublicacion;
    }

    public void setFechaPrimeraPublicacion(LocalDate fechaPrimeraPublicacion) {
        this.fechaPrimeraPublicacion = fechaPrimeraPublicacion;
    }
    
    @Override
    public String toString() {
        return "Autor{" + "idAutor=" + idAutor + 
               ", nombre=" + nombre + 
               ", pais=" + pais + 
               ", publicaciones=" + publicaciones + 
               ", fechaNacimiento=" + fechaNacimiento + 
               ", fechaPrimeraPublicacion=" + fechaPrimeraPublicacion + '}';
    }
}