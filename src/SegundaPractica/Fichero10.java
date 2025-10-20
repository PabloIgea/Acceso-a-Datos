package SegundaPractica;

import java.io.*;

public class Fichero10 {

    public static void main(String[] args) {

        int caracter;

        try(FileReader fr = new FileReader("src/SegundaPractica/resources/entrada.txt")){

            while((caracter = fr.read()) != -1) {
                System.out.println((char) caracter);
            }

        } catch (IOException e ) {

        }

    }

}
