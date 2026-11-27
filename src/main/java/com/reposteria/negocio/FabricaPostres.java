package com.reposteria.negocio;

import java.util.List;

/**
 * Regla de negocio: qué postres vende la repostería y a qué precio.
 * Centraliza la creación de los postres base, así la presentación no
 * conoce precios ni clases concretas.
 *
 * SOLID - S: su única responsabilidad es crear los postres del catálogo.
 */
public class FabricaPostres {
    public static List<String> menu() {
        return List.of("a) Torta de chocolate  Bs 120",
                       "b) Cheesecake          Bs 90",
                       "c) 6 Cupcakes          Bs 45");
    }

    public static Postre crear(String opcion) {
        switch (opcion.toLowerCase()) {
            case "a": return new PostreBase("Torta de chocolate", 120);
            case "b": return new PostreBase("Cheesecake", 90);
            case "c": return new PostreBase("6 Cupcakes", 45);
            default: throw new IllegalArgumentException("Postre no existe en el menu.");
        }
    }
}
