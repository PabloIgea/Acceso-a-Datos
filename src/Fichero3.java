//Fichero3-> Haz una ruta con URI. Creamos el objeto, verificamos que exista, si es un directorio o archivo(try/catch, )

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Fichero3 {

    public static void main(String[] args){

        try {
            URI uri = new URI("file:///C://Users//AlumnoAfternoon//Documents//pruebasJava//hijo.txt");

            File archivo = new File(uri);

            if (archivo.exists()) {
                if (archivo.isDirectory()) {
                    //Si en la ruta el ultimo elemento es un directoruio se muestra un mensaje en pantalla de que la riuta especificada es un archivo
                    System.out.println("La ruta presenta un directorio en: " + archivo.getAbsolutePath());
                } else if (archivo.isFile()) {
                    System.out.println("La ruta presenta un archivo en: " + archivo.getAbsolutePath());
                } else {
                    System.out.println("La ruta existe pero no contiene ni directorio ni archivo");
                }

            }

        } catch (URISyntaxException e) {
            System.err.println("La URI es invalida");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}