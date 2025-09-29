import java.io.File;

public class Fichero1 {
    public static void main(String[] args) {
        //Creamos un directorio padre en la ruta de documentos
        File directorioPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\pruebasJava");

        //Ruta relativa al fichero que acabo de crear
        String nombreHijo = "hijo.txt";

        //Creamos una instancia Filr utilizando el constructor y la variable de arriba
        File archivo = new File(directorioPadre, nombreHijo);

        if(archivo.exists()){
            System.out.println("Archivo encontrado en la ruta: " + archivo.getAbsolutePath());
        } else  {
            System.out.println("Archivo NO encontrado en la ruta: " + archivo.getAbsolutePath());
        }

    }
}
