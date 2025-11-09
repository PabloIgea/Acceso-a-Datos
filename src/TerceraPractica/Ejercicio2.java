package AccesoDatosBinarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario en el sistema.
 */
public class Usuario {
    private int id;         // identificador único de usuario
    private String nombre;  // nombre completo
    private String email;   // correo electrónico
    private int edad;       // edad en años

    public Usuario(int id, String nombre, String email, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }
    // Getters y setters omitidos por espacio

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre +
               ", Email: " + email + ", Edad: " + edad;
    }
}

/**
 * CRUD de usuarios usando JDBC y buenas prácticas con PreparedStatement.
 */
public class SistemaUsuariosJDBC {

    public static void crearTabla(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "nombre VARCHAR(100)," +
            "email VARCHAR(100)," +
            "edad INT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public static int insertarUsuario(Connection conn, String nombre, String email, int edad) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, email);
            pstmt.setInt(3, edad);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public static List<Usuario> buscarPorNombre(Connection conn, String nombre) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("edad")
                    ));
                }
            }
        }
        return lista;
    }

    public static boolean actualizarEmail(Connection conn, int id, String nuevoEmail) throws SQLException {
        String sql = "UPDATE usuarios SET email=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEmail);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public static boolean eliminarUsuario(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}
