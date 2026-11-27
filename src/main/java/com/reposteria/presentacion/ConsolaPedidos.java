package com.reposteria.presentacion;

import java.util.Scanner;
import com.reposteria.negocio.ServicioPedidos;

/**
 * CAPA DE PRESENTACIÓN
 * Solo conversa con el usuario por la terminal: muestra el menú y lee datos.
 * No calcula precios ni guarda nada; todo eso se lo pide a la capa de negocio.
 *
 * SOLID - S (Responsabilidad única): su único trabajo es la interacción por consola.
 */
public class ConsolaPedidos {
    private final ServicioPedidos servicio;
    private final Scanner sc = new Scanner(System.in);

    public ConsolaPedidos(ServicioPedidos servicio) {
        this.servicio = servicio;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n=== REPOSTERIA - PEDIDOS PARA LLEVAR ===");
            System.out.println("1. Nuevo pedido");
            System.out.println("2. Ver pedidos");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            String op = sc.nextLine().trim();

            if (op.equals("1")) nuevoPedido();
            else if (op.equals("2")) verPedidos();
            else if (op.equals("0")) { System.out.println("Gracias por su compra."); return; }
            else System.out.println("Opcion no valida.");
        }
    }

    private void nuevoPedido() {
        System.out.print("Nombre del cliente: ");
        String cliente = sc.nextLine();
        for (String linea : servicio.menu()) System.out.println("  " + linea);
        System.out.print("Postre: ");
        String opcion = sc.nextLine().trim();
        System.out.print("Mensaje en el postre (Enter para ninguno): ");
        String mensaje = sc.nextLine().trim();
        System.out.print("Caja de regalo? (s/n): ");
        boolean caja = sc.nextLine().equalsIgnoreCase("s");

        try {
            servicio.registrarPedido(cliente, opcion, mensaje, caja);
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private void verPedidos() {
        if (servicio.listarPedidos().isEmpty()) System.out.println("  No hay pedidos.");
        int i = 1;
        for (String p : servicio.listarPedidos()) System.out.println("  " + (i++) + ". " + p);
    }
}
