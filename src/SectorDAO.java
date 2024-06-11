import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectorDAO {

  public void createSector(Sector sector) {
    String sql = "INSERT INTO Sectores (NombreSector, CantCamas, Reservable) VALUES (?,?,?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, sector.getNombreSector());
        pstmt.setInt(2, sector.getCantCamas());
        pstmt.setBoolean(3, sector.isReservable());
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  public Sector readSector(int idSector) {
    String sql = "SELECT p.IDSector, p.NombreSector, p.CantCamas, p.Reservable FROM Sectores p WHERE p.IDSector = ?";
    Sector sector = null;
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, idSector);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            sector = new Sector(
                rs.getString("NombreSector"),
                rs.getInt("CantCamas"),
                rs.getBoolean("Reservable")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return sector;
  }

  public void updateSector(Sector sector, int idSector) {
    String sql = "UPDATE Sectores SET NombreSector = ?, CantCamas = ?, Reservable = ? WHERE IDSector = ?";
    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false);  // Start transaction
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sector.getNombreSector());
            pstmt.setInt(2, sector.getCantCamas());
            pstmt.setBoolean(3, sector.isReservable());
            pstmt.setInt(4, idSector);
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

  public void deleteSector(int idSector) {
    String sql = "DELETE FROM Sectores WHERE IDSector = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, idSector);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }

  }

}
