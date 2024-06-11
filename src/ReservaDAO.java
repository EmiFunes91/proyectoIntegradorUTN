import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservaDAO {

    public void createReserva(Reserva reserva) {
      String sql = "INSERT INTO Reservas (IDCama, IDPaciente, FechaYHora) VALUES (?,?,?)";
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, reserva.getCamaID());
          pstmt.setInt(2, reserva.getPacienteID());
          pstmt.setString(3, reserva.getFechaYHora());
          pstmt.executeUpdate();
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }
  
    public Reserva readReserva(int pkReserva) {
      String sql = "SELECT p.IDCama, p.IDPaciente, p.FechaYHora FROM Reservas p WHERE p.PK_Reserva = ?";
      Reserva reserva = null;
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, pkReserva);
          ResultSet rs = pstmt.executeQuery();
  
          if (rs.next()) {
              reserva = new Reserva(
                  rs.getInt("IDCama"),
                  rs.getInt("IDPaciente"),
                  rs.getString("FechaYHora")
              );
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  
      return reserva;
    }
  
    public void updateReserva(Reserva reserva, int pkReserva) {
      String sql = "UPDATE Reservas SET IDCama = ?, IDPaciente = ?, FechaYHora = ? WHERE PK_Reserva = ?";
      try (Connection conn = DatabaseConnection.getConnection()) {
          conn.setAutoCommit(false);  // Start transaction
          try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setInt(1, reserva.getCamaID());
              pstmt.setInt(2, reserva.getPacienteID());
              pstmt.setString(3, reserva.getFechaYHora());
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