import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Crear un nuevo paciente
    public void createPaciente(Paciente paciente) {
        String sqlPersonas = "INSERT INTO Personas (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";
        String sqlPacientes = "INSERT INTO Pacientes (id, fecha_nacimiento, fecha_ingreso) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmtPersonas = conn.prepareStatement(sqlPersonas, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstmtPacientes = conn.prepareStatement(sqlPacientes)) {

                // Insert into Personas
                pstmtPersonas.setString(1, paciente.getNombre());
                pstmtPersonas.setString(2, paciente.getApellido());
                pstmtPersonas.setString(3, paciente.getDireccion());
                pstmtPersonas.setString(4, paciente.getTelefono());
                pstmtPersonas.executeUpdate();

                // Get generated id
                ResultSet rs = pstmtPersonas.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    paciente.setId(id);

                    // Insert into Pacientes
                    pstmtPacientes.setInt(1, id);
                    pstmtPacientes.setDate(2, java.sql.Date.valueOf(paciente.getFechaNacimiento()));
                    pstmtPacientes.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now())); // Hora y fecha actual
                    pstmtPacientes.executeUpdate();

                    conn.commit();  // Commit transaction
                }
            } catch (SQLException e) {
                conn.rollback();  // Rollback transaction on error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Leer un paciente por ID
    public Paciente readPaciente(int id) {
        String sql = "SELECT p.id, p.nombre, p.apellido, p.direccion, p.telefono, pa.fecha_nacimiento " +
                     "FROM Personas p JOIN Pacientes pa ON p.id = pa.id WHERE p.id = ?";
        Paciente paciente = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                paciente = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    // Actualizar un paciente
    public void updatePaciente(Paciente paciente) {
        String sqlPersonas = "UPDATE Personas SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE id = ?";
        String sqlPacientes = "UPDATE Pacientes SET fecha_nacimiento = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmtPersonas = conn.prepareStatement(sqlPersonas);
                 PreparedStatement pstmtPacientes = conn.prepareStatement(sqlPacientes)) {

                // Update Personas
                pstmtPersonas.setString(1, paciente.getNombre());
                pstmtPersonas.setString(2, paciente.getApellido());
                pstmtPersonas.setString(3, paciente.getDireccion());
                pstmtPersonas.setString(4, paciente.getTelefono());
                pstmtPersonas.setInt(5, paciente.getId());
                pstmtPersonas.executeUpdate();

                // Update Pacientes
                pstmtPacientes.setDate(1, Date.valueOf(paciente.getFechaNacimiento()));
                pstmtPacientes.setInt(2, paciente.getId());
                pstmtPacientes.executeUpdate();

                conn.commit();  // Commit transaction
            } catch (SQLException e) {
                conn.rollback();  // Rollback transaction on error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un paciente
    public void deletePaciente(int id) {
        String sqlPacientes = "DELETE FROM Pacientes WHERE id = ?";
        String sqlPersonas = "DELETE FROM Personas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmtPacientes = conn.prepareStatement(sqlPacientes);
                 PreparedStatement pstmtPersonas = conn.prepareStatement(sqlPersonas)) {

                // Delete from Pacientes
                pstmtPacientes.setInt(1, id);
                pstmtPacientes.executeUpdate();

                // Delete from Personas
                pstmtPersonas.setInt(1, id);
                pstmtPersonas.executeUpdate();

                conn.commit();  // Commit transaction
            } catch (SQLException e) {
                conn.rollback();  // Rollback transaction on error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Leer todos los pacientes
    public List<Paciente> readAllPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.apellido, p.direccion, p.telefono, pa.fecha_nacimiento " +
                     "FROM Personas p JOIN Pacientes pa ON p.id = pa.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }
}



