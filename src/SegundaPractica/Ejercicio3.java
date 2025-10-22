package SegundaPractica;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

enum NivelLog { INFO, WARNING, ERROR }

public class Ejercicio3 {

    public static class SistemaLog {
        private final String archivoLog;
        private final long tamanoMaximo;
        private int numeroRotacion = 1;

        public SistemaLog(String archivoLog, long tamanoMaximo) {
            if (archivoLog == null || archivoLog.isBlank())
                throw new IllegalArgumentException("Ruta del archivo no válida");
            if (tamanoMaximo <= 0)
                throw new IllegalArgumentException("El tamaño máximo debe ser positivo");
            this.archivoLog = archivoLog;
            this.tamanoMaximo = tamanoMaximo;
        }

        /**
         * Escribe un mensaje en el log con timestamp
         * @param mensaje contenido a registrar
         * @param nivel nivel del log (INFO, WARNING, ERROR)
         * @throws IOException si hay error al escribir
         */
        public void escribirLog(String mensaje, NivelLog nivel) throws IOException {
            if (rotarSiNecesario()) {
                System.out.println("ROTACIÓN: " + archivoLog +
                        " renombrado a " + archivoLog + "." + (numeroRotacion - 1));
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String linea = String.format("[%s] [%s] %s", timestamp, nivel, mensaje);

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(archivoLog, true), StandardCharsets.UTF_8))) {
                writer.write(linea);
                writer.newLine();
                writer.flush();
            }

            System.out.println("Log escrito: " + mensaje);
        }

        /**
         * Verifica si el archivo debe rotarse y ejecuta la rotación
         * @return true si se realizó la rotación
         * @throws IOException si hay error en la rotación
         */
        private boolean rotarSiNecesario() throws IOException {
            File log = new File(archivoLog);
            if (log.exists() && obtenerTamanoLog() >= tamanoMaximo) {
                File rotado = new File(archivoLog + "." + numeroRotacion++);
                if (rotado.exists()) rotado.delete();
                if (!log.renameTo(rotado)) {
                    throw new IOException("No se pudo rotar el archivo de log");
                }
                return true;
            }
            return false;
        }

        /**
         * Obtiene el tamaño actual del archivo de log
         * @return tamaño en bytes
         */
        private long obtenerTamanoLog() {
            File log = new File(archivoLog);
            return log.exists() ? log.length() : 0;
        }
    }

    public static void main(String[] args) {
        try {
            SistemaLog log = new SistemaLog("app.log", 1024); // máximo 1KB
            log.escribirLog("Aplicación iniciada", NivelLog.INFO);
            log.escribirLog("Usuario conectado", NivelLog.INFO);
            log.escribirLog("Error de conexión", NivelLog.ERROR);
        } catch (IOException e) {
            System.err.println("Error en el sistema de log: " + e.getMessage());
        }
    }
}

