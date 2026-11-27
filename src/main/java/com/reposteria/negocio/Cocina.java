package com.reposteria.negocio;

/** OBSERVER concreto: la cocina recibe qué debe preparar. */
public class Cocina implements Observador {
    public void notificar(String cliente, Postre postre) {
        System.out.println("  [Cocina] Preparar: " + postre.descripcion());
    }
}
