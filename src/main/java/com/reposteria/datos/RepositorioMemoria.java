package com.reposteria.datos;

import java.util.ArrayList;
import java.util.List;

/**
 * CAPA DE DATOS - Implementación que guarda los pedidos en memoria.
 *
 * PATRÓN CREACIONAL: SINGLETON
 * Debe existir UN solo almacén de pedidos; si hubiera dos, los pedidos
 * quedarían repartidos. El constructor es privado y la única forma de
 * obtenerlo es getInstancia().
 */
public class RepositorioMemoria implements RepositorioPedidos {
    private static RepositorioMemoria instancia;          // la única instancia
    private final List<Pedido> pedidos = new ArrayList<>();

    private RepositorioMemoria() { }                      // nadie puede hacer new

    public static RepositorioMemoria getInstancia() {
        if (instancia == null) instancia = new RepositorioMemoria();
        return instancia;
    }

    public void guardar(Pedido pedido) { pedidos.add(pedido); }
    public List<Pedido> listar() { return pedidos; }
}
