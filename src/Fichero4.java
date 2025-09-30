/*Fichero4-> Copia exacta del ejercicio 1, pero mostrando dentro del if el contenido de la carpeta, sus nombres (List(). y length().)*/

import java.io.File;

public class Fichero4 {public static void main(String[] args) {


    //Creamos un directorio padre en la ruta de documentos
    File directorioPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\pruebasJava");

    //Ruta relativa al fichero que acabo de crear
    String nombreHijo = "hijo.txt";

    //Creamos una instancia Filr utilizando el constructor y la variable de arriba
    File archivo = new File(directorioPadre, nombreHijo);

    if(archivo.exists()){
        System.out.println("Archivo encontrado en la ruta: " + archivo.getAbsolutePath());

        String [] contenido = directorioPadre.list();

        if (contenido != null  && contenido.length> 0 ) {
            System.out.println("El contenido del directorio es: ");

            for (String nombre : contenido) {
                System.out.println(nombre);
            }
            System.out.println("Número total de elementos: " + contenido.length);
        } else
            System.out.println("El contenido está vacío");
    } else  {
        System.out.println("Archivo NO encontrado en la ruta: " + archivo.getAbsolutePath());
    }

}

}
