import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReservaDAO {

    public void createReserva(Reserva reserva, LocalDateTime fechaYHora) {
        String sql = "INSERT INTO Reservas (IDCama, IDPaciente, FechaYHora) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reserva.getCamaID());
            pstmt.setInt(2, reserva.getPacienteID());
            pstmt.setTimestamp(3, Timestamp.valueOf(fechaYHora));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public Reserva readReserva(int pkReserva) {
        String sql = "SELECT IDCama, IDPaciente, FechaYHora FROM Reservas WHERE PK_Reserva = ?";
        Reserva reserva = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pkReserva);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                reserva = new Reserva(
                    rs.getInt("IDCama"),
                    rs.getInt("IDPaciente")
                );
                LocalDateTime fechaYHora = rs.getTimestamp("FechaYHora").toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
    }

    public void updateReserva(Reserva reserva, LocalDateTime fechaYHora, int pkReserva) {
        String sql = "UPDATE Reservas SET IDCama = ?, IDPaciente = ?, FechaYHora = ? WHERE PK_Reserva = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, reserva.getCamaID());
                pstmt.setInt(2, reserva.getPacienteID());
                pstmt.setTimestamp(3, Timestamp.valueOf(fechaYHora));
                pstmt.setInt(4, pkReserva);
                pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReserva(int pkReserva) {
        String sql = "DELETE FROM Reservas WHERE PK_Reserva = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pkReserva);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
