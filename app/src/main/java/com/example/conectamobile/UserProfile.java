package com.example.conectamobile;

public class UserProfile {
    private String name;
    private String email;
    private String id; // Campo opcional para almacenar el ID de Firebase

    // Constructor vacío requerido por Firebase
    public UserProfile() {}

    // Constructor con parámetros
    public UserProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
