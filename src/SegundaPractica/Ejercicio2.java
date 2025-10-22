package SegundaPractica;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Ejercicio2 {

    /**
     * Combina múltiples archivos en uno solo, filtrando líneas
     * @param archivosEntrada array con las rutas de los archivos a combinar
     * @param archivoSalida ruta del archivo resultado
     * @param filtro palabra que debe contener la línea para incluirse (null = todas)
     * @return número total de líneas escritas
     * @throws IOException si hay error de lectura/escritura
     */
    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {
        int totalEscritas = 0;

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(archivoSalida), StandardCharsets.UTF_8))) {

            for (String ruta : archivosEntrada) {
                File archivo = new File(ruta);
                if (!archivo.exists()) {
                    System.out.println("El archivo " + ruta + " no existe. Se omite.");
                    continue;
                }

                int coinciden = 0;
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        if (cumpleFiltro(linea, filtro)) {
                            writer.write(linea);
                            writer.newLine();
                            totalEscritas++;
                            coinciden++;
                        }
                    }
                }

                System.out.println("Procesando " + archivo.getName() + ": " + coinciden + " líneas coinciden");
            }
        }

        System.out.println("Total: " + totalEscritas + " líneas escritas en " + archivoSalida);
        return totalEscritas;
    }

    /**
     * Verifica si una línea cumple el criterio de filtrado
     * @param linea línea a evaluar
     * @param filtro criterio de búsqueda (null = siempre true)
     * @return true si la línea debe incluirse
     */
    private static boolean cumpleFiltro(String linea, String filtro) {
        return (filtro == null || linea.contains(filtro));
    }

    public static void main(String[] args) {
        String[] archivosEntrada = {"archivo1.txt", "archivo2.txt"};
        String archivoSalida = "combinado.txt";
        String filtro = "Java";

        try {
            combinarArchivos(archivosEntrada, archivoSalida, filtro);
        } catch (IOException e) {
            System.err.println("Error al procesar los archivos: " + e.getMessage());
        }
    }
}

