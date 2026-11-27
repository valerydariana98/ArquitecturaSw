package com.reposteria;

import com.reposteria.datos.RepositorioMemoria;
import com.reposteria.datos.RepositorioPedidos;
import com.reposteria.negocio.AvisoCliente;
import com.reposteria.negocio.Cocina;
import com.reposteria.negocio.ServicioPedidos;
import com.reposteria.negocio.ServicioPedidosImpl;
import com.reposteria.presentacion.ConsolaPedidos;

/**
 * Punto de arranque: aquí se "arman" las 3 capas y se conectan entre sí.
 *
 *   PRESENTACIÓN (ConsolaPedidos) -> NEGOCIO (ServicioPedidos) -> DATOS (RepositorioPedidos)
 *
 * SOLID - D (Inversión de dependencias): el servicio recibe el repositorio
 * por su constructor como interfaz; aquí decidimos qué implementación usar.
 *
 * En IntelliJ: clic derecho en Main > Run 'Main.main()'.
 */
public class Main {
    public static void main(String[] args) {
        // Capa de datos (Singleton: siempre la misma instancia)
        RepositorioPedidos repositorio = RepositorioMemoria.getInstancia();

        // Capa de negocio, con sus observadores suscritos
        ServicioPedidos servicio = new ServicioPedidosImpl(repositorio);
        servicio.suscribir(new Cocina());
        servicio.suscribir(new AvisoCliente());

        // Capa de presentación
        new ConsolaPedidos(servicio).iniciar();
    }
}
