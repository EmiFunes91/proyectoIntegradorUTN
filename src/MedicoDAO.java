import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // Crear un nuevo médico
    public void createMedico(Medico medico) {
        String sql = "INSERT INTO Medicos (nombre, apellido, especialidad) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, medico.getNombre());
            pstmt.setString(2, medico.getApellido());
            pstmt.setString(3, medico.getEspecialidad());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Leer un médico por ID
    public Medico readMedico(int id) {
        String sql = "SELECT * FROM Medicos WHERE id = ?";
        Medico medico = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                medico = new Medico(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("especialidad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medico;
    }

    // Actualizar un médico
    public void updateMedico(Medico medico) {
        String sql = "UPDATE Medicos SET nombre = ?, apellido = ?, especialidad = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, medico.getNombre());
            pstmt.setString(2, medico.getApellido());
            pstmt.setString(3, medico.getEspecialidad());
            pstmt.setInt(4, medico.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un médico
    public void deleteMedico(int id) {
        String sql = "DELETE FROM Medicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Leer todos los médicos
    public List<Medico> readAllMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM Medicos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("especialidad")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }
}

