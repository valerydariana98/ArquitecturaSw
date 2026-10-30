# Actividad 10 PATRONES — texto para pegar en el documento

## 1. Título del Caso de Uso y tecnologías

**Título:** Módulo de Pedidos de Repostería para Llevar

**Tecnologías:**
- Lenguaje: Java (JDK 8 o superior)
- Interfaz: terminal (consola), sin interfaz gráfica
- Control de versiones: Git / GitHub
- Ejecución: `javac PedidosReposteriaApp.java` y luego `java PedidosReposteriaApp`

## 2. Detalle de los Patrones usados

| Tipo de patrón | Patrón | Clases que lo implementan |
|---|---|---|
| Creacional | **Singleton** | `Mostrador` (constructor privado + `getInstancia()`) |
| Estructural | **Decorator** | `Postre` (interfaz), `PostreBase`, `Extra` (decorador base), `ConMensaje`, `CajaRegalo` |
| Comportamiento | **Observer** | `Observador` (interfaz), `Cocina`, `AvisoCliente`; el `Mostrador` es el sujeto que notifica |

## 3. Explicación de caso de uso

Una repostería recibe pedidos para llevar en su mostrador. El sistema muestra un menú en la terminal con tres opciones: nuevo pedido, ver pedidos y salir.

**Flujo principal (Nuevo pedido):**
1. El vendedor elige la opción 1 e ingresa el nombre del cliente.
2. Elige el postre del menú: torta de chocolate (Bs 120), cheesecake (Bs 90) o 6 cupcakes (Bs 45).
3. El sistema pregunta si el postre lleva un mensaje escrito con chocolate (+Bs 10) y si va en caja de regalo (+Bs 15).
4. El pedido se anota en el único mostrador de la repostería.
5. Automáticamente se avisa a la cocina qué debe preparar y al cliente (simulación de WhatsApp) con el total a pagar.

**Flujo alterno (Ver pedidos):** el sistema lista todos los pedidos registrados con su detalle y precio total.

**Actores:** vendedor (usa el sistema), cocina y cliente (reciben los avisos).

## 4. Pegar aquí el Código Fuente

Archivo: `PedidosReposteriaApp.java`

```java
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
```

**Ejemplo de corrida:**

```
=== REPOSTERIA - PEDIDOS PARA LLEVAR ===
1. Nuevo pedido
2. Ver pedidos
0. Salir
Opcion: 1
Nombre del cliente: Maria Lopez
  a) Torta de chocolate  Bs 120
  b) Cheesecake          Bs 90
  c) 6 Cupcakes          Bs 45
Postre: a
Mensaje en el postre (Enter para ninguno): Feliz cumple
Caja de regalo? (s/n): s
  [Cocina] Preparar: Torta de chocolate + Mensaje "Feliz cumple" + Caja de regalo
  [WhatsApp a Maria Lopez] Pedido recibido. Total Bs 145.0

Opcion: 2
  1. Maria Lopez: Torta de chocolate + Mensaje "Feliz cumple" + Caja de regalo | Bs 145.0
```

## 5. Explicación de la razón de cada patrón de diseño usado

**Singleton (Creacional) en `Mostrador`.**
La repostería tiene un solo mostrador donde se anotan todos los pedidos. Si cada parte del programa creara su propio mostrador con `new`, los pedidos quedarían repartidos en listas distintas y se perderían. Por eso el constructor de `Mostrador` es privado y la única forma de obtenerlo es `Mostrador.getInstancia()`, que lo crea la primera vez y después siempre devuelve el mismo. En el `main` se ve: al inicio se obtiene el mostrador para suscribir los avisos, y al registrar el pedido se vuelve a pedir con `getInstancia()`; como es el mismo, los pedidos y los avisos se mantienen.

**Decorator (Estructural) en los postres.**
A un postre se le pueden agregar extras (mensaje escrito, caja de regalo) que cambian su descripción y su precio. Sin el patrón tendríamos que crear una clase por cada combinación: `TortaConMensaje`, `TortaConCaja`, `TortaConMensajeYCaja`, lo mismo para el cheesecake y los cupcakes, y crecería con cada extra nuevo. Con Decorator, cada extra es una clase pequeña que "envuelve" a otro postre, llama a su `precio()` y le suma su propio monto. Así se combinan libremente: `new CajaRegalo(new ConMensaje(new PostreBase("Torta de chocolate", 120), "Feliz cumple"))` cuesta 120 + 10 + 15 = 145. Para agregar un extra nuevo (por ejemplo, velas) solo se crea un decorador más, sin tocar las clases existentes.

**Observer (Comportamiento) en los avisos.**
Cuando se registra un pedido, varias partes deben enterarse: la cocina, para prepararlo, y el cliente, para saber el total. Si el `Mostrador` llamara directamente a cada uno, quedaría acoplado a todos ellos. Con Observer, el `Mostrador` (sujeto) solo guarda una lista de `Observador` y, al registrar, recorre la lista llamando a `notificar(...)`. Cada observador decide qué hacer (mostrar la orden en cocina, enviar WhatsApp). Si mañana se quiere avisar también al repartidor, basta con crear `AvisoRepartidor` y suscribirlo con `mostrador.suscribir(...)`, sin modificar el `Mostrador`.

## 6. Link del repositorio GIT

https://github.com/valerydariana98/ArquitecturaSw
