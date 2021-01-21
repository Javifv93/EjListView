package com.example.ejlistview;

public class Contacto {
    private int id = -1;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String email;
    private String direccion;
    private String observaciones;

    public Contacto (String nombre)
    {
        this.nombre = nombre;
    }
    public Contacto (String nombre, String apellidos, int telefono, String email, String direccion, String observaciones)
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.observaciones = observaciones;
    }
    public Contacto (int id, String nombre, String apellidos, int telefono, String email, String direccion, String observaciones)
    {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.observaciones = observaciones;
    }

    public void setId(int id) { this.id = id; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getId() {return  id;}

    public String getNombre() {
        return nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getObservaciones() {
        return observaciones;
    }


    public String getApellidos() {
        return apellidos;
    }
}
