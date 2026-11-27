package com.reposteria.datos;

/**
 * CAPA DE DATOS - Modelo: lo que se guarda de cada pedido.
 */
public class Pedido {
    private final String cliente;
    private final String detalle;
    private final double total;

    public Pedido(String cliente, String detalle, double total) {
        this.cliente = cliente;
        this.detalle = detalle;
        this.total = total;
    }

    @Override
    public String toString() { return cliente + ": " + detalle + " | Bs " + total; }
}
