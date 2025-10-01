import java.io.File;
import java.io.IOException;

public class Fichero7 {

    /*
  Función organizarBiblioteca(): crea carpeta de categoría y archivo catalogo.txt.
• Función verificarLibro(): verifica si existe un libro; si no, pregunta si se crea.
• Practica: exists(), mkdir(), createNewFile(), funciones separadas*/

    public void organizarBiblioteca() {

        String directorioBiblioteca = "C:\\Users\\AlumnoAfternoon\\Documents\\Biblioteca";
        String directorioCategoria =  "C:\\Users\\AlumnoAfternoon\\Documents\\Biblioteca\\Categoría";
        String catalogo = "C:\\Users\\AlumnoAfternoon\\Documents\\Biblioteca\\Categoría\\catalogo.txt";

        File dirBib = new File(directorioBiblioteca);
        File dirCat = new File(directorioCategoria);
        // Verificar si el archivo y directorio existe
        boolean fin = false;

        do{
            if (!dirBib.exists()){
                System.out.println("El directorio no existe.");
                dirBib.mkdir();
                System.out.println("El directorio ha sido creado correctamente");
                fin = true;
            } else if (!dirCat.exists()) {
                System.out.println("El directorio no existe.");
                dirCat.mkdir();
                System.out.println("El directorio ha sido creado correctamente");
                fin = true;
            } else {
                System.out.println("El directorio y el archivo ya existe");
                fin = true;
            }
        }while(!fin);

    }


    public static void main (String[] args) throws IOException {

    }

}
