package com.example.rachel.projectefinalraquel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachel on 18/04/2018.
 */

public class RefugioAnimal  implements Serializable{
    private String nombre;
    private String latitud;
    private String longitud;

    public RefugioAnimal(String nombre, String latitud, String longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public RefugioAnimal() {

    }

    public String getNombre() {
        return nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
