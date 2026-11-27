package com.reposteria.negocio;

/**
 * PATRÓN ESTRUCTURAL: DECORATOR - Componente.
 * Lo que todo postre sabe hacer, tenga o no extras.
 *
 * SOLID - I (Segregación de interfaces): interfaz pequeña, solo dos métodos.
 * SOLID - L (Sustitución de Liskov): un postre con extras se usa igual que uno simple.
 */
public interface Postre {
    String descripcion();
    double precio();
}
