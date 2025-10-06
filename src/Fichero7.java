import java.io.*;
import java.util.Scanner;

/* Función organizarBiblioteca(): crea carpeta de categoría y archivo catalogo.txt.
• Función verificarLibro(): verifica si existe un libro; si no, pregunta si se crea.
• Practica: exists(), mkdir(), createNewFile(), funciones separadas*/ 

public class Fichero7 {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== ORGANIZADOR DE BIBLIOTECA ===");
        
        // Solicitar nombre de categoría
        System.out.print("Introduce el nombre de la categoría: ");
        String categoria = scanner.nextLine();
        
        // Organizar biblioteca (crear categoría y catálogo)
        File dirCategoria = organizarBiblioteca(categoria);
        
        System.out.println();
        
        // Solicitar categoría del libro
        System.out.print("Introduce la categoría del libro: ");
        String categoriaLibro = scanner.nextLine();
        
        // Solicitar nombre del libro
        System.out.print("Introduce el nombre del libro: ");
        String nombreLibro = scanner.nextLine();
        
        // Verificar y crear libro si es necesario
        verificarLibro(dirCategoria, nombreLibro);
        
        // Manejo de categorías con caracteres especiales
        if (categoria.contains("/") || categoria.contains("\\") || categoria.contains(":")) {
            System.out.println("\nPermite crear varias categorías,");
            System.out.println("permite los caracteres \"\\\",");
            System.out.println("\"/\",\":\"");
        }
        
        scanner.close();
    }
    
    /**
     * Función organizarBiblioteca(): crea carpeta de categoría y archivo catalogo.txt
     * @param categoria nombre de la categoría
     * @return File objeto del directorio creado
     */
    public static File organizarBiblioteca(String categoria) {
        File dirCategoria = new File("C:\\biblioteca\\" + categoria);
        boolean categoriaExistia = dirCategoria.exists();
        
        if (!categoriaExistia) {
            // Crear directorio usando mkdir()
            if (dirCategoria.mkdirs()) {
                System.out.println("✓ Categoría '" + categoria + "' creada exitosamente");
                
                // Crear archivo catálogo usando createNewFile()
                File catalogo = new File(dirCategoria, "catalogo.txt");
                try {
                    if (catalogo.createNewFile()) {
                        System.out.println("✓ Catálogo creado en: " + catalogo.getAbsolutePath());
                        System.out.println("\nCreo un directorio nuevo,");
                        System.out.println("será por defecto un archivo");
                        System.out.println("dentro de ese directorio recién creado");
                        
                        // Mostrar información del archivo
                        System.out.println("\nCompruebo si existe realmente");
                        System.out.println("el directorio con el nuevo fichero");
                        System.out.println("recién creado y muestro su peso en bytes");
                        System.out.println("Tamaño: " + catalogo.length() + " bytes");
                    }
                } catch (IOException e) {
                    System.out.println("Error al crear catálogo: " + e.getMessage());
                }
            } else {
                System.out.println("Error al crear la categoría");
            }
        } else {
            System.out.println("✓ La categoría '" + categoria + "' ya existe");
            File catalogo = new File(dirCategoria, "catalogo.txt");
            
            // Verificar si existe el catálogo usando exists()
            if (catalogo.exists()) {
                System.out.println("✓ El catálogo ya existe en: " + catalogo.getAbsolutePath());
            } else {
                // Crear catálogo si no existe
                try {
                    if (catalogo.createNewFile()) {
                        System.out.println("✓ Catálogo creado en: " + catalogo.getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.out.println("Error al crear catálogo: " + e.getMessage());
                }
            }
            
            System.out.println("\nCompruebo si existe de verdad el");
            System.out.println("directorio, la función que crea el archivo");
            System.out.println("por defecto debe de comprobar si ya");
            System.out.println("existe");
        }
        
        return dirCategoria;
    }
    
    /**
     * Función verificarLibro(): verifica si existe un libro; si no, pregunta si se crea
     * @param dirCategoria directorio de la categoría
     * @param nombreLibro nombre del libro
     */
    public static void verificarLibro(File dirCategoria, String nombreLibro) {
        File archivoLibro = new File(dirCategoria, nombreLibro + ".txt");
        
        // Verificar si existe usando exists()
        if (!archivoLibro.exists()) {
            System.out.println("✗ El libro no existe en: " + archivoLibro.getAbsolutePath());
            System.out.print("¿Quieres crear el libro? (s/n): ");
            String respuesta = scanner.nextLine();
            
            if (respuesta.equalsIgnoreCase("s")) {
                try {
                    // Crear archivo usando createNewFile()
                    if (archivoLibro.createNewFile()) {
                        System.out.println("✓ Libro creado exitosamente en: " + archivoLibro.getAbsolutePath());
                        
                        System.out.println("\nDentro de la carpeta " + dirCategoria.getName() + " vamos a");
                        System.out.println("comprobar si existe un fichero distinto a");
                        System.out.println("catálogo, si no existe debe preguntarnos si");
                        System.out.println("queremos crearlo o no, si ponemos \"s\" lo crea");
                        System.out.println("dentro de la carpeta que hemos puesto arriba,");
                        System.out.println("si ponemos \"n\", no la crea");
                    }
                } catch (IOException e) {
                    System.out.println("Error al crear el archivo: " + e.getMessage());
                }
            } else {
                System.out.println("Libro no creado.");
            }
        } else {
            System.out.println("✓ El libro existe en: " + archivoLibro.getAbsolutePath());
            System.out.println("Tamaño: " + archivoLibro.length() + " bytes");
        }
    }
}