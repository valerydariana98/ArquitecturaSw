package com.reposteria.negocio;

/**
 * PATRÓN DE COMPORTAMIENTO: OBSERVER - Observador.
 * Cualquiera que quiera enterarse cuando se registra un pedido.
 */
public interface Observador {
    void notificar(String cliente, Postre postre);
}
