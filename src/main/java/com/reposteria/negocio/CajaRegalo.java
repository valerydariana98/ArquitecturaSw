package com.reposteria.negocio;

/** DECORATOR concreto: caja de regalo para llevar (+Bs 15). */
public class CajaRegalo extends Extra {
    public CajaRegalo(Postre postre) { super(postre); }

    public String descripcion() { return postre.descripcion() + " + Caja de regalo"; }
    public double precio() { return postre.precio() + 15; }
}
