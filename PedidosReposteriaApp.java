import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Actividad 10 - Patrones de diseno
 * Caso de uso: Pedidos de reposteria para llevar (terminal).
 *
 * Patrones usados:
 *   - Creacional ......... SINGLETON  (Mostrador de pedidos)
 *   - Estructural ........ DECORATOR  (extras que se agregan a un postre)
 *   - Comportamiento ..... OBSERVER   (avisos cuando se registra un pedido)
 *
 * Compilar: javac PedidosReposteriaApp.java
 * Ejecutar: java PedidosReposteriaApp
 */
public class PedidosReposteriaApp {

    // =====================================================================
    // PATRON ESTRUCTURAL: DECORATOR
    // Un postre tiene una descripcion y un precio. En vez de crear una clase
    // por cada combinacion (TortaConMensaje, TortaConMensajeYCaja...)
    // "envolvemos" el postre base con decoradores que le suman extras.
    // =====================================================================

    /** Componente: lo que todo postre sabe hacer. */
    interface Postre {
        String descripcion();
        double precio();
    }

    /** Componente concreto: el postre base elegido del menu. */
    static class PostreBase implements Postre {
        private final String nombre;
        private final double precio;

        PostreBase(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        public String descripcion() { return nombre; }
        public double precio() { return precio; }
    }

    /** Decorador base: guarda el postre que envuelve. */
    static abstract class Extra implements Postre {
        protected final Postre postre;
        Extra(Postre postre) { this.postre = postre; }
    }

    /** Decorador concreto: mensaje escrito con chocolate. */
    static class ConMensaje extends Extra {
        private final String texto;
        ConMensaje(Postre postre, String texto) { super(postre); this.texto = texto; }
        public String descripcion() { return postre.descripcion() + " + Mensaje \"" + texto + "\""; }
        public double precio() { return postre.precio() + 10; }
    }

    /** Decorador concreto: caja de regalo para llevar. */
    static class CajaRegalo extends Extra {
        CajaRegalo(Postre postre) { super(postre); }
        public String descripcion() { return postre.descripcion() + " + Caja de regalo"; }
        public double precio() { return postre.precio() + 15; }
    }

    // =====================================================================
    // PATRON DE COMPORTAMIENTO: OBSERVER
    // Cuando el mostrador registra un pedido, avisa a todos los suscritos
    // (cocina, cliente). El mostrador no sabe COMO avisa cada uno;
    // solo llama a notificar().
    // =====================================================================

    /** Observador: cualquiera que quiera enterarse de un nuevo pedido. */
    interface Observador {
        void notificar(String cliente, Postre postre);
    }

    /** Observador concreto: la cocina recibe que debe preparar. */
    static class Cocina implements Observador {
        public void notificar(String cliente, Postre postre) {
            System.out.println("  [Cocina] Preparar: " + postre.descripcion());
        }
    }

    /** Observador concreto: simula un WhatsApp al cliente. */
    static class AvisoCliente implements Observador {
        public void notificar(String cliente, Postre postre) {
            System.out.println("  [WhatsApp a " + cliente + "] Pedido recibido. Total Bs " + postre.precio());
        }
    }

    // =====================================================================
    // PATRON CREACIONAL: SINGLETON
    // La reposteria tiene UN solo mostrador donde se anotan los pedidos.
    // El constructor es privado y la unica forma de obtenerlo es
    // getInstancia(), asi todo el programa usa la misma lista de pedidos.
    // (El Mostrador tambien es el "Sujeto" del patron Observer.)
    // =====================================================================

    static class Mostrador {
        private static Mostrador instancia;                 // la unica instancia
        private final List<String> pedidos = new ArrayList<>();
        private final List<Observador> observadores = new ArrayList<>();

        private Mostrador() { }                             // nadie puede hacer new Mostrador()

        static Mostrador getInstancia() {
            if (instancia == null) instancia = new Mostrador();
            return instancia;
        }

        void suscribir(Observador o) { observadores.add(o); }

        void registrar(String cliente, Postre postre) {
            pedidos.add(cliente + ": " + postre.descripcion() + " | Bs " + postre.precio());
            for (Observador o : observadores) o.notificar(cliente, postre);  // Observer en accion
        }

        void listar() {
            if (pedidos.isEmpty()) System.out.println("  No hay pedidos.");
            for (int i = 0; i < pedidos.size(); i++) System.out.println("  " + (i + 1) + ". " + pedidos.get(i));
        }
    }

    // =====================================================================
    // PROGRAMA PRINCIPAL (menu de terminal)
    // =====================================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Mostrador mostrador = Mostrador.getInstancia();
        mostrador.suscribir(new Cocina());
        mostrador.suscribir(new AvisoCliente());

        while (true) {
            System.out.println("\n=== REPOSTERIA - PEDIDOS PARA LLEVAR ===");
            System.out.println("1. Nuevo pedido");
            System.out.println("2. Ver pedidos");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            String op = sc.nextLine().trim();

            if (op.equals("1")) {
                System.out.print("Nombre del cliente: ");
                String cliente = sc.nextLine();
                System.out.println("  a) Torta de chocolate  Bs 120");
                System.out.println("  b) Cheesecake          Bs 90");
                System.out.println("  c) 6 Cupcakes          Bs 45");
                System.out.print("Postre: ");
                String p = sc.nextLine().trim().toLowerCase();

                Postre postre;
                if (p.equals("a")) postre = new PostreBase("Torta de chocolate", 120);
                else if (p.equals("b")) postre = new PostreBase("Cheesecake", 90);
                else postre = new PostreBase("6 Cupcakes", 45);

                System.out.print("Mensaje en el postre (Enter para ninguno): ");
                String msg = sc.nextLine().trim();
                if (!msg.isEmpty()) postre = new ConMensaje(postre, msg);
                System.out.print("Caja de regalo? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) postre = new CajaRegalo(postre);

                // Prueba del Singleton: pedimos el mostrador otra vez y es el mismo
                Mostrador.getInstancia().registrar(cliente, postre);
            } else if (op.equals("2")) {
                mostrador.listar();
            } else if (op.equals("0")) {
                System.out.println("Gracias por su compra.");
                break;
            } else {
                System.out.println("Opcion no valida.");
            }
        }
    }
}
