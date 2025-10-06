import java.io.File;
import java.net.URI;
import java.util.Scanner;

/**
 * FICHERO8 - EXPLORADOR DE SISTEMA DE ARCHIVOS
 * 
 * Este programa demuestra el uso de métodos de la clase File:
 * - listFiles(): para obtener contenido de directorios
 * - isFile(): para verificar si un elemento es archivo
 * - isDirectory(): para verificar si un elemento es directorio
 * - toURI(): para convertir rutas del sistema a formato URI
 * 
 * Funcionalidades principales:
 * 1. Explorar carpetas y mostrar su contenido
 * 2. Analizar elementos (archivos con tamaño, directorios con número de elementos)
 * 3. Convertir rutas a formato URI
 * 4. Manejo de errores para rutas inexistentes
 */
public class Fichero8 {
    
    public static void main(String[] args) {
        // Crear scanner para entrada de usuario
        Scanner scanner = new Scanner(System.in);
        
        // Mostrar título del programa
        System.out.println("=== EXPLORADOR INTELIGENTE ===");
        
        // Solicitar al usuario la ruta a explorar
        System.out.print("Introduce la ruta a explorar: ");
        String ruta = scanner.nextLine();
        
        // PASO 1: Explorar la carpeta introducida
        System.out.println("\n--- PASO 1: EXPLORANDO CARPETA ---");
        explorarCarpeta(ruta);
        
        // PASO 2: Mostrar total de elementos encontrados
        System.out.println("\n--- PASO 2: CONTANDO ELEMENTOS ---");
        File directorio = new File(ruta);
        if (directorio.exists() && directorio.isDirectory()) {
            File[] elementos = directorio.listFiles();
            int total = (elementos != null) ? elementos.length : 0;
            System.out.println("Total de elementos encontrados: " + total);
        } else {
            System.out.println("No se puede contar elementos - la ruta no es un directorio válido");
        }
        
        // PASO 3: Conversión a URI
        System.out.println("\n--- PASO 3: CONVERSIÓN A URI ---");
        System.out.println("CONVERSIÓN A URI:");
        convertirAURI(ruta);
        
        // Cerrar scanner
        scanner.close();
        System.out.println("\n=== PROGRAMA FINALIZADO ===");
    }
    
    /**
     * FUNCIÓN 1: EXPLORAR CARPETA
     * 
     * Esta función recibe una ruta y explora su contenido:
     * - Verifica que la ruta exista
     * - Verifica que sea un directorio
     * - Lista todos los elementos usando listFiles()
     * - Llama a analizarElemento() para cada elemento encontrado
     * 
     * @param ruta La ruta de la carpeta a explorar
     */
    public static void explorarCarpeta(String ruta) {
        System.out.println("Explorando: " + ruta);
        
        // Crear objeto File con la ruta proporcionada
        File directorio = new File(ruta);
        
        // VERIFICACIÓN 1: Comprobar si la ruta existe
        if (!directorio.exists()) {
            System.out.println("✗ La ruta no existe: " + ruta);
            System.out.println("  → Verifica que la ruta esté escrita correctamente");
            return;
        }
        
        // VERIFICACIÓN 2: Comprobar si es un directorio usando isDirectory()
        if (!directorio.isDirectory()) {
            System.out.println("✗ La ruta especificada no es un directorio");
            System.out.println("  → La ruta apunta a un archivo, no a una carpeta");
            return;
        }
        
        // LISTAR CONTENIDO: Usar listFiles() para obtener elementos
        System.out.println("✓ Directorio válido - obteniendo contenido...");
        File[] elementos = directorio.listFiles();
        
        // Verificar si se pudo acceder al contenido
        if (elementos == null) {
            System.out.println("✗ No se puede acceder al contenido del directorio");
            System.out.println("  → Posibles causas: permisos insuficientes");
            return;
        }
        
        // Verificar si el directorio está vacío
        if (elementos.length == 0) {
            System.out.println("ℹ El directorio está vacío (0 elementos)");
            return;
        }
        
        // ANALIZAR CADA ELEMENTO encontrado
        System.out.println("✓ Encontrados " + elementos.length + " elementos:");
        System.out.println("  → Analizando cada elemento...");
        for (File elemento : elementos) {
            // Llamar a analizarElemento para cada elemento
            analizarElemento(elemento.getAbsolutePath());
        }
    }
    
    /**
     * FUNCIÓN 2: ANALIZAR ELEMENTO
     * 
     * Esta función analiza un elemento específico del sistema de archivos:
     * - Usa isFile() para determinar si es un archivo
     * - Usa isDirectory() para determinar si es un directorio
     * - Para archivos: muestra el tamaño en bytes
     * - Para directorios: cuenta y muestra el número de elementos internos
     * 
     * @param ruta La ruta del elemento a analizar
     */
    public static void analizarElemento(String ruta) {
        // Crear objeto File para el elemento
        File elemento = new File(ruta);
        
        // Verificar que el elemento existe
        if (!elemento.exists()) {
            System.out.println("- ✗ El elemento no existe: " + ruta);
            return;
        }
        
        // Obtener solo el nombre del archivo/directorio
        String nombre = elemento.getName();
        
        // ANÁLISIS 1: Verificar si es archivo usando isFile()
        if (elemento.isFile()) {
            // Es un archivo - obtener y mostrar tamaño
            long tamaño = elemento.length();
            System.out.println("- " + nombre + " [ARCHIVO - " + tamaño + " bytes]");
            System.out.println("    → Tipo: Archivo regular");
            System.out.println("    → Tamaño: " + formatearTamaño(tamaño));
            
        // ANÁLISIS 2: Verificar si es directorio usando isDirectory()  
        } else if (elemento.isDirectory()) {
            // Es un directorio - contar elementos internos
            System.out.println("    → Tipo: Directorio - contando elementos internos...");
            File[] contenido = elemento.listFiles();
            int numElementos = (contenido != null) ? contenido.length : 0;
            System.out.println("- " + nombre + " [DIRECTORIO - " + numElementos + " elementos]");
            System.out.println("    → Contenido: " + numElementos + " elementos internos");
            
        } else {
            // Tipo desconocido (enlaces simbólicos, etc.)
            System.out.println("- " + nombre + " [TIPO DESCONOCIDO]");
            System.out.println("    → Puede ser un enlace simbólico u otro tipo especial");
        }
    }
    
    /**
     * FUNCIÓN 3: CONVERTIR A URI
     * 
     * Esta función convierte una ruta del sistema de archivos a formato URI:
     * - Usa el método toURI() de la clase File
     * - Muestra la ruta original y la URI resultante
     * - Verifica si la conversión fue exitosa
     * 
     * @param ruta La ruta a convertir
     */
    public static void convertirAURI(String ruta) {
        System.out.println("Ruta original: " + ruta);
        System.out.println("  → Iniciando conversión a formato URI...");
        
        try {
            // Crear objeto File y convertir a URI usando toURI()
            File archivo = new File(ruta);
            System.out.println("  → Aplicando método toURI()...");
            URI uri = archivo.toURI();
            
            // Mostrar resultado de la conversión
            System.out.println("URI equivalente: " + uri);
            System.out.println("  → Conversión completada exitosamente");
            
            // VERIFICACIÓN: Comprobar si la URI es válida
            if (archivo.exists()) {
                System.out.println("✓ La URI es válida y apunta al mismo elemento");
                System.out.println("  → La ruta existe en el sistema de archivos");
                System.out.println("  → Esquema URI: " + uri.getScheme());
                System.out.println("  → Ruta URI: " + uri.getPath());
            } else {
                System.out.println("✗ hay un problema con la conversión a URI");
                System.out.println("  → La ruta original no existe, pero la URI se generó");
                System.out.println("  → URI generada: " + uri);
            }
            
        } catch (Exception e) {
            // Manejo de errores en la conversión
            System.out.println("✗ Error al convertir la ruta a URI: " + e.getMessage());
            System.out.println("  → Causa posible: caracteres especiales en la ruta");
        }
    }
    
    /**
     * FUNCIÓN AUXILIAR: FORMATEAR TAMAÑO
     * 
     * Convierte bytes a unidades más legibles (KB, MB, GB)
     * 
     * @param bytes Tamaño en bytes
     * @return Tamaño formateado con unidades
     */
    private static String formatearTamaño(long bytes) {
        if (bytes < 1024) {
            return bytes + " bytes";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
}
