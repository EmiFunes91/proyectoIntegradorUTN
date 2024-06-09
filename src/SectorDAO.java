import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public void createSector (Sector sector) {

String sql = "INSERT INTO Sectores (NombreSector, CantCamas, Reservable) VALUES (?,?,?)";
 try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, sector.getNombreSector());
         pstmt.setInt(2, sector.getCantCamas());
         pstmt.setBool(3, sector.getReservable());
         pstmt.executeUpdate();

         } catch (SQLException e) {
            e.printStackTrace();
         }
     }
     
public Sector readSector (int IDSector) {
   String sql = "SELECT p.IDSector, p.NombreSector, p.CantCamas, p.Reservable WHERE p.IDSector = ?";
   Sector sector = null;
   try (Connection conn = DatabaseConnection.getConnection();
   PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, IDSector);
      ResultSet rs pstmt.executeQuery();

      if (rs.next()) {
         sector = new Sector (
            rs.getString("NombreSector"),
            rs.getInt("CantCamas"),
            rs.getBool("Reservable"),
         );   
      }
      
   } catch (SQLException e) {
      e.printStackTrace();
   }

   return sector;

}

public void updateSector (Sector sector) {

   String sql = "UPDATE Sectores NombreSector = ?, CantCamas = ?, Reservable = ? WHERE IDSector = ?";
   try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmtSectores = conn.prepareStatement(sqlSectores)) {

               pstmtSectores.setString(1, sector.getNombreSector);
               pstmtSectores.setInt(2, sector.getCantCamas);
               pstmtSectores.getBool(3, sector.getReservable);
               pstmtSectores.executeUpdate();

               conn.commit();
               
             } catch (SQLException e) {
               conn.rollback();
               e.printStackTrace();
             }
    } catch (SQLException e) {
      e.printStackTrace();
    }
}
