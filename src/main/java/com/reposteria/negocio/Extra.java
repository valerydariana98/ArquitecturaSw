package com.reposteria.negocio;

/**
 * DECORATOR - Decorador base: guarda el postre que envuelve.
 *
 * SOLID - O (Abierto/Cerrado): para un extra nuevo (ej. velas) se crea
 * otra subclase, sin modificar las clases que ya existen.
 */
public abstract class Extra implements Postre {
    protected final Postre postre;

    protected Extra(Postre postre) { this.postre = postre; }
}
