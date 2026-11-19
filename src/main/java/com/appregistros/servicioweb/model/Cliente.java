package com.appregistros.servicioweb.model;

/**
 * Cliente tal como se muestra en la tabla.
 * Los getters son usados por PropertyValueFactory en el FXML.
 */
public class Cliente {
    private final Integer id;
    private final String nombre;
    private final String correo;
    private final String telefono;
    private final String ciudad;
    private final String estado;

    public Cliente(Integer id, String nombre, String correo, String telefono, String ciudad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    /** Indica si el texto aparece en el nombre, correo o ciudad (sin distinguir mayúsculas). */
    public boolean coincideCon(String texto) {
        if (texto == null || texto.isBlank()) {
            return true;
        }
        String filtro = texto.toLowerCase().trim();
        return nombre.toLowerCase().contains(filtro)
                || correo.toLowerCase().contains(filtro)
                || ciudad.toLowerCase().contains(filtro);
    }
}
