package com.reposteria.datos;

import java.util.List;

/**
 * CAPA DE DATOS - Contrato de almacenamiento.
 *
 * SOLID - D: la capa de negocio depende de esta interfaz, no de cómo se guarda.
 * SOLID - L: cualquier implementación (memoria, archivo, MySQL) puede reemplazar
 *            a otra sin que el negocio lo note.
 */
public interface RepositorioPedidos {
    void guardar(Pedido pedido);
    List<Pedido> listar();
}
