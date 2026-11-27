package com.reposteria.negocio;

/** DECORATOR - Componente concreto: el postre elegido del menú, sin extras. */
public class PostreBase implements Postre {
    private final String nombre;
    private final double precio;

    public PostreBase(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String descripcion() { return nombre; }
    public double precio() { return precio; }
}
