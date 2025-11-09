package AccesoDatosBinarios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un producto en el inventario binario.
 */
public class Producto implements Serializable {
    private int id;           // identificador único del producto (int)
    private String nombre;    // nombre descriptivo (String)
    private double precio;    // precio en decimal (double)
    private int stock;        // cantidad de unidades disponibles (int)

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    // Getters y setters omitidos por espacio

    @Override
    public String toString() {
        return "ID: " + id +
               ", Nombre: " + nombre +
               ", Precio: " + precio +
               ", Stock: " + stock;
    }
}

/**
 * Funciones de inventario binario.
 */
public class InventarioBinario {
    /**
     * Escribe un producto en el archivo binario, sobrescribiéndolo.
     */
    public static void escribirProducto(String archivo, Producto producto) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            dos.writeInt(producto.getId());
            dos.writeUTF(producto.getNombre());
            dos.writeDouble(producto.getPrecio());
            dos.writeInt(producto.getStock());
        }
    }

    /**
     * Lee todos los productos de un archivo binario y retorna la lista.
     */
    public static List<Producto> leerProductos(String archivo) throws IOException {
        List<Producto> productos = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (true) {
                int id = dis.readInt();
                String nombre = dis.readUTF();
                double precio = dis.readDouble();
                int stock = dis.readInt();
                productos.add(new Producto(id, nombre, precio, stock));
            }
        } catch (EOFException e) {
            // Fin de archivo alcanzado
        }
        return productos;
    }

    /**
     * Añade un producto al final del archivo binario sin sobrescribir.
     */
    public static void agregarProducto(String archivo, Producto producto) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo, true))) {
            dos.writeInt(producto.getId());
            dos.writeUTF(producto.getNombre());
            dos.writeDouble(producto.getPrecio());
            dos.writeInt(producto.getStock());
        }
    }
}
