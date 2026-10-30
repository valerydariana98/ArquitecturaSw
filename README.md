# Pedidos de Repostería para Llevar - Patrones de Diseño (Actividad 10)

Módulo de pedidos de una repostería en Java, por terminal, que demuestra tres patrones de diseño:

| Tipo | Patrón | Dónde |
|---|---|---|
| Creacional | Singleton | `Mostrador` |
| Estructural | Decorator | `Postre`, `PostreBase`, `ConMensaje`, `CajaRegalo` |
| Comportamiento | Observer | `Observador`, `Cocina`, `AvisoCliente` |

## Cómo ejecutar

Requiere Java 8 o superior.

```bash
javac PedidosReposteriaApp.java
java PedidosReposteriaApp
```

## Ejemplo de uso

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
```
