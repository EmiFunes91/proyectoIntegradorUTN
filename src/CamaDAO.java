import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CamaDAO {

    public void createCama(Cama cama) {
      String sql = "INSERT INTO Camas (IDSector, Reservable) VALUES (?,?)";
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, cama.getSectorID());
          pstmt.setBoolean(2, cama.getReservable());
          pstmt.executeUpdate();
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }
  
    public Cama readCama(int idCama) {
      String sql = "SELECT p.IDCama, p.IDSector p.Reservable FROM Camas p WHERE p.IDCama = ?";
      Cama cama = null;
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, idCama);
          ResultSet rs = pstmt.executeQuery();
  
          if (rs.next()) {
              cama = new Cama (
                  rs.getInt("IDSector"),
                  rs.getBoolean("Reservable")
              );
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  
      return cama;
    }
  
    public void updateCama(Cama cama, int idCama) {
      String sql = "UPDATE Camas SET IDSector = ?, Reservable = ? IDCama = ?";
      try (Connection conn = DatabaseConnection.getConnection()) {
          conn.setAutoCommit(false);  // Start transaction
          try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setInt(1, cama.getSectorID());
              pstmt.setBoolean(2, cama.getReservable());
              pstmt.setInt(3, idCama);
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
  
    public void deleteCama(int idCama) {
      String sql = "DELETE FROM Camas WHERE IDCama = ?";
      try (Connection conn = DatabaseConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, idCama);
          pstmt.executeUpdate();
      } catch (SQLException e) {
          e.printStackTrace();
      }
  
    }
  
  }