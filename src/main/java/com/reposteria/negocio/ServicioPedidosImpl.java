package com.reposteria.negocio;

import java.util.ArrayList;
import java.util.List;
import com.reposteria.datos.Pedido;
import com.reposteria.datos.RepositorioPedidos;

/**
 * CAPA DE NEGOCIO
 * Aplica las reglas de la repostería: valida el pedido, arma el postre con sus
 * extras, lo manda a guardar y avisa a los interesados.
 *
 * SOLID - D: depende de la INTERFAZ RepositorioPedidos, no de una clase concreta.
 *            Se la entregan por el constructor (inyección de dependencias).
 *
 * PATRÓN DE COMPORTAMIENTO: OBSERVER
 * Este servicio es el "Sujeto": guarda una lista de Observadores y, al
 * registrar un pedido, los notifica a todos sin saber qué hace cada uno.
 */
public class ServicioPedidosImpl implements ServicioPedidos {
    private final RepositorioPedidos repositorio;
    private final List<Observador> observadores = new ArrayList<>();

    public ServicioPedidosImpl(RepositorioPedidos repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void suscribir(Observador o) { observadores.add(o); }

    @Override
    public List<String> menu() { return FabricaPostres.menu(); }

    @Override
    public void registrarPedido(String cliente, String opcion, String mensaje, boolean cajaRegalo) {
        if (cliente.isBlank()) throw new IllegalArgumentException("El nombre del cliente es obligatorio.");

        // PATRÓN ESTRUCTURAL: DECORATOR -> se envuelve el postre con sus extras
        Postre postre = FabricaPostres.crear(opcion);
        if (!mensaje.isEmpty()) postre = new ConMensaje(postre, mensaje);
        if (cajaRegalo) postre = new CajaRegalo(postre);

        repositorio.guardar(new Pedido(cliente, postre.descripcion(), postre.precio()));
        for (Observador o : observadores) o.notificar(cliente, postre);   // Observer en acción
    }

    @Override
    public List<String> listarPedidos() {
        List<String> lineas = new ArrayList<>();
        for (Pedido p : repositorio.listar()) lineas.add(p.toString());
        return lineas;
    }
}
