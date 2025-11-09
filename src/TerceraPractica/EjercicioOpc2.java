package AccesoDatosBinarios;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Clase para migración, exportación y sincronización entre archivos Properties y bases de datos.
 */
public class EjercicioOpc2 {
    /**
     * Migra todas las propiedades del archivo a base de datos.
     */
    public static int migrarPropertiesABD(String archivo, Connection conn) throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            props.load(fis);
        }
        int count = 0;
        String crearTabla = "CREATE TABLE IF NOT EXISTS configuracion (clave VARCHAR(100) PRIMARY KEY, valor VARCHAR(255))";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(crearTabla);
        }
        String insertar = "REPLACE INTO configuracion (clave, valor) VALUES (?, ?)";
        for (String key : props.stringPropertyNames()) {
            try (PreparedStatement pstmt = conn.prepareStatement(insertar)) {
                pstmt.setString(1, key);
                pstmt.setString(2, props.getProperty(key));
                pstmt.executeUpdate();
                count++;
            }
        }
        return count;
    }

    /**
     * Exporta configuración de base de datos a archivo Properties.
     */
    public static int exportarBDaProperties(Connection conn, String archivo) throws SQLException, IOException {
        Properties props = new Properties();
        String sql = "SELECT clave, valor FROM configuracion";
        int count = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
             FileOutputStream fos = new FileOutputStream(archivo)) {
            while (rs.next()) {
                props.setProperty(rs.getString("clave"), rs.getString("valor"));
                count++;
            }
            props.store(fos, "Configuración exportada desde Base de Datos");
        }
        return count;
    }

    /**
     * Sincroniza (actualiza) solo las propiedades cambiadas entre archivo y base de datos.
     */
    public static int sincronizarPropiedades(String archivo, Connection conn) throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            props.load(fis);
        }
        int actualizados = 0;
        String update = "UPDATE configuracion SET valor=? WHERE clave=? AND valor<>?";
        for (String key : props.stringPropertyNames()) {
            try (PreparedStatement pstmt = conn.prepareStatement(update)) {
                pstmt.setString(1, props.getProperty(key));
                pstmt.setString(2, key);
                pstmt.setString(3, props.getProperty(key));
                int rows = pstmt.executeUpdate();
                actualizados += rows;
            }
        }
        return actualizados;
    }
}
