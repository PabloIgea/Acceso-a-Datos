package AccesoDatosBinarios;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para exportar e importar productos entre base de datos y archivo binario.
 */
public class EjercicioOpc1 {
    /**
     * Exporta todos los productos de la base de datos a archivo binario.
     */
    public static int exportarProductos(Connection conn, String archivo) throws SQLException, IOException {
        String sql = "SELECT id, nombre, precio, stock FROM productos";
        int contador = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            while (rs.next()) {
                dos.writeInt(rs.getInt("id"));
                dos.writeUTF(rs.getString("nombre"));
                dos.writeDouble(rs.getDouble("precio"));
                dos.writeInt(rs.getInt("stock"));
                contador++;
            }
        }
        return contador;
    }

    /**
     * Importa productos desde archivo binario a la base de datos.
     */
    public static int importarProductos(Connection conn, String archivo) throws SQLException, IOException {
        String sql = "INSERT INTO productos (id, nombre, precio, stock) VALUES (?, ?, ?, ?)";
        int contador = 0;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (true) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, dis.readInt());
                    pstmt.setString(2, dis.readUTF());
                    pstmt.setDouble(3, dis.readDouble());
                    pstmt.setInt(4, dis.readInt());
                    pstmt.executeUpdate();
                    contador++;
                }
            }
        } catch (EOFException e) {
            // Fin de archivo alcanzado
        }
        return contador;
    }
}
