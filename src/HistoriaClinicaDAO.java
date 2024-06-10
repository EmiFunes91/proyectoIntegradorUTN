import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class HistoriaClinicaDAO {

    // Crear una nueva historia clínica
    public void createHistoriaClinica(HistoriaClinica historiaClinica) {
        String sql = "INSERT INTO HistoriasClinicas (id_paciente, problema, fecha_creacion) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, historiaClinica.getIdPaciente());
            pstmt.setString(2, historiaClinica.getProblema());
            pstmt.setDate(3, java.sql.Date.valueOf(historiaClinica.getFechaCreacion()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
