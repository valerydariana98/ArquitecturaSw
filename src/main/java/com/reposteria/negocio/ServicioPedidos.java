package com.reposteria.negocio;

import java.util.List;

/**
 * CAPA DE NEGOCIO - Contrato de lo que el negocio ofrece a la presentación.
 *
 * SOLID - D: la presentación depende de esta interfaz, no de la implementación.
 * SOLID - I: solo expone las 4 operaciones que la presentación necesita.
 */
public interface ServicioPedidos {
    void suscribir(Observador observador);
    List<String> menu();
    void registrarPedido(String cliente, String opcion, String mensaje, boolean cajaRegalo);
    List<String> listarPedidos();
}
