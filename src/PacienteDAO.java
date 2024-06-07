// PacienteDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteDAO {
    public void createPaciente(Paciente paciente) {
        String sql = "INSERT INTO Pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paciente.getId());
            pstmt.setString(2, paciente.getNombre());
            pstmt.setString(3, paciente.getApellido());
            pstmt.setDate(4, paciente.getFechaNacimiento());
            pstmt.setString(5, paciente.getDireccion());
            pstmt.setString(6, paciente.getTelefono());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Paciente readPaciente(int id) {
        String sql = "SELECT * FROM Pacientes WHERE id = ?";
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
                    rs.getDate("fecha_nacimiento"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    public void updatePaciente(Paciente paciente) {
        String sql = "UPDATE Pacientes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, direccion = ?, telefono = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, paciente.getNombre());
            pstmt.setString(2, paciente.getApellido());
            pstmt.setDate(3, paciente.getFechaNacimiento());
            pstmt.setString(4, paciente.getDireccion());
            pstmt.setString(5, paciente.getTelefono());
            pstmt.setInt(6, paciente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePaciente(int id) {
        String sql = "DELETE FROM Pacientes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


