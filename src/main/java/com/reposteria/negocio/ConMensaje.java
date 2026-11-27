package com.reposteria.negocio;

/** DECORATOR concreto: mensaje escrito con chocolate (+Bs 10). */
public class ConMensaje extends Extra {
    private final String texto;

    public ConMensaje(Postre postre, String texto) {
        super(postre);
        this.texto = texto;
    }

    public String descripcion() { return postre.descripcion() + " + Mensaje \"" + texto + "\""; }
    public double precio() { return postre.precio() + 10; }
}
