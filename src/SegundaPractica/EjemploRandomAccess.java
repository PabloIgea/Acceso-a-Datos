package SegundaPractica;

import java.io.*;

public class EjemploRandomAccess {

    public static final String SALIDA = "src/SegundaPractica/resources/entradaRandom.txt";

    public static void main(String[] args) {

        try (RandomAccessFile raf = new RandomAccessFile(SALIDA, "rw")){

            raf.writeBytes("INICIO");

            raf.seek(20);
            raf.writeBytes("MEDIO");

            raf.seek(40);
            raf.writeBytes("FINAL");

            raf.seek(0);
            System.out.println("Posicion 0" + raf.readLine());

            raf.seek(20);
            System.out.println("Posicion 20" + raf.readLine());

            raf.seek(40);
            System.out.println("Posicion 40" + raf.readLine());

            System.out.println("Tamaño del archivo " + raf.length() + " bytes.");

        }catch(IOException e) {
            System.err.println("Error al acceder al archivo: " + e.getMessage());
        }

    }

}

