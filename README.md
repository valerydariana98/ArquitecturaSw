# Repostería - Pedidos para llevar (Arquitectura de 3 capas)

Java por terminal. Capas: `presentacion`, `negocio`, `datos` (en `src/main/java/com/reposteria`).

**IntelliJ:** File > Open > carpeta `Reposteria`; marcar `src/main/java` como *Sources Root* si hace falta; clic derecho en `Main` > Run.

**Terminal:**
```bash
javac -d out $(find src -name "*.java")
java -cp out com.reposteria.Main
```
