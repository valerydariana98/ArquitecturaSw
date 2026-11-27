package com.reposteria.negocio;

/** OBSERVER concreto: simula un WhatsApp al cliente con el total. */
public class AvisoCliente implements Observador {
    public void notificar(String cliente, Postre postre) {
        System.out.println("  [WhatsApp a " + cliente + "] Pedido recibido. Total Bs " + postre.precio());
    }
}
