package AccesoDatosBinarios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Datos y reporte de análisis de archivo binario.
 */
class ElementoDato {
    int posicion;
    String tipo;
    String valor;

    public ElementoDato(int posicion, String tipo, String valor) {
        this.posicion = posicion;
        this.tipo = tipo;
        this.valor = valor;
    }
}

/**
 * Clase Reporte para recoger y mostrar información sobre el análisis de binarios.
 */
class Reporte {
    String nombreArchivo;
    long tamañoBytes;
    List<ElementoDato> elementos = new ArrayList<>();
    int totalInts, totalDoubles, totalStrings, totalBooleans;

    public Reporte(String archivo, long tamaño) {
        nombreArchivo = archivo;
        tamañoBytes = tamaño;
    }

    public void agregarElemento(ElementoDato elem) {
        elementos.add(elem);
        switch (elem.tipo) {
            case "INT": totalInts++; break;
            case "DOUBLE": totalDoubles++; break;
            case "UTF": totalStrings++; break;
            case "BOOLEAN": totalBooleans++; break;
        }
    }
}

/**
 * Analizador y generador de reporte de archivos binarios.
 */
public class EjercicioOpc3 {
    public static Reporte analizarArchivoBinario(String archivo) throws IOException {
        File f = new File(archivo);
        Reporte reporte = new Reporte(archivo, f.length());
        try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
            int pos = 0;
            while (dis.available() > 0) {
                // Detección simple (demo)
                if (dis.available() >= 4) {
                    int valor = dis.readInt();
                    reporte.agregarElemento(new ElementoDato(pos, "INT", String.valueOf(valor)));
                    pos += 4;
                }
                if (dis.available() >= 2) {
                    try {
                        String txt = dis.readUTF();
                        reporte.agregarElemento(new ElementoDato(pos, "UTF", txt));
                        pos += 2 + txt.length();
                    } catch (IOException e) {}
                }
                if (dis.available() >= 8) {
                    double d = dis.readDouble();
                    reporte.agregarElemento(new ElementoDato(pos, "DOUBLE", String.valueOf(d)));
                    pos += 8;
                }
                if (dis.available() >= 1) {
                    boolean b = dis.readBoolean();
                    reporte.agregarElemento(new ElementoDato(pos, "BOOLEAN", String.valueOf(b)));
                    pos += 1;
                }
            }
        }
        return reporte;
    }

    public static void guardarReporte(Reporte reporte, String archivo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Reporte de Análisis de Archivo Binario");
            pw.println("Archivo: " + reporte.nombreArchivo);
            pw.println("Tamaño: " + reporte.tamañoBytes + " bytes");
            for (ElementoDato elem : reporte.elementos) {
                pw.println("Posición: " + elem.posicion + " Tipo: " + elem.tipo + " Valor: " + elem.valor);
            }
            pw.println("Resumen:");
            pw.println("INT: " + reporte.totalInts);
            pw.println("DOUBLE: " + reporte.totalDoubles);
            pw.println("UTF: " + reporte.totalStrings);
            pw.println("BOOLEAN: " + reporte.totalBooleans);
        }
    }

    public static void mostrarReporte(Reporte reporte) {
        System.out.println("Reporte de Análisis de Archivo Binario");
        System.out.println("Archivo: " + reporte.nombreArchivo);
        System.out.println("Tamaño: " + reporte.tamañoBytes + " bytes");
        for (ElementoDato elem : reporte.elementos) {
            System.out.println("Posición: " + elem.posicion + " Tipo: " + elem.tipo + " Valor: " + elem.valor);
        }
        System.out.println("Resumen:");
        System.out.println("INT: " + reporte.totalInts);
        System.out.println("DOUBLE: " + reporte.totalDoubles);
        System.out.println("UTF: " + reporte.totalStrings);
        System.out.println("BOOLEAN: " + reporte.totalBooleans);
    }
}
