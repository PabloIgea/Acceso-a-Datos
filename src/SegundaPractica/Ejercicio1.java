package SegundaPractica;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Ejercicio1 {

    private static int numeroLineas = 0;
    private static int numeroPalabras = 0;
    private static int numeroCaracteres = 0;
    private static String palabraMasLarga = "";

    public static void main(String[] args) {
        try {
            String archivoEntrada = "archivo.txt";
            String archivoSalida = "resultado.txt";

            analizarArchivo(archivoEntrada);
            mostrarEstadisticas();
            guardarEstadisticas(archivoSalida);

        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    public static void analizarArchivo(String nombreArchivo) throws IOException {
        File file = new File(nombreArchivo);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("El archivo no existe o no es válido: " + nombreArchivo);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                numeroLineas++;
                numeroCaracteres += linea.length();
                String[] palabras = linea.split("\\s+");
                for (String palabra : palabras) {
                    if (!palabra.isEmpty()) {
                        numeroPalabras++;
                        if (palabra.length() > palabraMasLarga.length()) {
                            palabraMasLarga = palabra;
                        }
                    }
                }
            }
        }
    }

    public static void mostrarEstadisticas() {
        System.out.println("=== Estadísticas del archivo ===");
        System.out.println("Líneas: " + numeroLineas);
        System.out.println("Palabras: " + numeroPalabras);
        System.out.println("Caracteres: " + numeroCaracteres);
        System.out.println("Palabra más larga: " + palabraMasLarga +
                " (" + palabraMasLarga.length() + " caracteres)");
    }

    public static void guardarEstadisticas(String archivoSalida) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(archivoSalida), StandardCharsets.UTF_8))) {

            bw.write("=== Estadísticas del archivo ===\n");
            bw.write("Líneas: " + numeroLineas + "\n");
            bw.write("Palabras: " + numeroPalabras + "\n");
            bw.write("Caracteres: " + numeroCaracteres + "\n");
            bw.write("Palabra más larga: " + palabraMasLarga +
                    " (" + palabraMasLarga.length() + " caracteres)\n");
        }
    }
}

