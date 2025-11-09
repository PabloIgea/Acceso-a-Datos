package AccesoDatosBinarios;

import java.io.*;
import java.util.Properties;

/**
 * Clase utilitaria para manejar configuración con Properties.
 */
public class ConfiguradorApp {

    /**
     * Carga la configuración desde un archivo, o crea con valores por defecto si no existe.
     */
    public static Properties cargarConfiguracion(String archivo) throws IOException {
        Properties props = new Properties();
        File f = new File(archivo);
        if (f.exists()) {
            try (FileInputStream fis = new FileInputStream(f)) {
                props.load(fis);
            }
        } else {
            props.setProperty("db.host", "localhost");
            props.setProperty("db.port", "3306");
            props.setProperty("db.name", "mibasedatos");
            props.setProperty("db.user", "admin");
            props.setProperty("app.titulo", "Mi Aplicación");
            props.setProperty("app.version", "1.0.0");
            props.setProperty("app.debug", "false");
            guardarConfiguracion(props, archivo, "Configuración por defecto generada automáticamente");
        }
        return props;
    }

    public static String getString(Properties props, String clave, String valorDefecto) {
        return props.getProperty(clave, valorDefecto);
    }

    public static int getInt(Properties props, String clave, int valorDefecto) {
        String valor = props.getProperty(clave);
        try {
            return valor != null ? Integer.parseInt(valor) : valorDefecto;
        } catch (NumberFormatException e) {
            return valorDefecto;
        }
    }

    public static boolean getBoolean(Properties props, String clave, boolean valorDefecto) {
        String valor = props.getProperty(clave);
        return valor != null ? Boolean.parseBoolean(valor) : valorDefecto;
    }

    public static void guardarConfiguracion(Properties props, String archivo, String comentario) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            props.store(fos, comentario);
        }
    }

    public static void mostrarConfiguracion(Properties props) {
        for (String key : props.stringPropertyNames()) {
            System.out.println(key + " = " + props.getProperty(key));
        }
    }
}
