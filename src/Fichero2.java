import java.io.File;

public class Fichero2
{
    public static void main(String[] args) {
        //Creamos un archivo padre en la ruta de documentos
        File ruta = new File("C:\\Users\\AlumnoAfternoon\\Documents\\pruebasJava");

        //Ruta relativa al fichero que acabo de crear
        String nombreHijo = "hijo.txt";

        //Creamos una instancia Filr utilizando el constructor y la variable de arriba
        File archivo = new File(ruta, nombreHijo);

        if (ruta.exists()) {
            if (ruta.isDirectory()) {
                //Si en la ruta el ultimo elemento es un directoruio se muestra un mensaje en pantalla de que la riuta especificada es un archivo
                System.out.println("La ruta presenta un archivo en: " + ruta.getAbsolutePath());
            } else if (ruta.isFile()) {
                System.out.println("La ruta presenta un archivo en: " + ruta.getAbsolutePath());
            }
        }
    }
}
