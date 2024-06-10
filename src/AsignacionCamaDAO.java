import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AsignacionCamaDAO {

    public void createAsignacionCama(AsignacionCama asignacionCama) {
        String sql = "INSERT INTO AsignacionCama (id_enfermera, id_cama, fecha, turno) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, asignacionCama.getIdEnfermera());
            pstmt.setInt(2, asignacionCama.getIdCama());
            pstmt.setObject(3, asignacionCama.getFecha());
            pstmt.setString(4, asignacionCama.getTurno());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    asignacionCama.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating AsignacionCama failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AsignacionCama getById(int id) {
        String sql = "SELECT * FROM AsignacionCama WHERE id = ?";
        AsignacionCama asignacionCama = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                asignacionCama = new AsignacionCama(
                    rs.getInt("id"),
                    rs.getInt("id_enfermera"),
                    rs.getInt("id_cama"),
                    rs.getObject("fecha", LocalDateTime.class).toLocalDate(),
                    rs.getString("turno")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignacionCama;
    }

    public List<AsignacionCama> getAll() {
        List<AsignacionCama> asignaciones = new ArrayList<>();
        String sql = "SELECT * FROM AsignacionCama";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                AsignacionCama asignacionCama = new AsignacionCama(
                    rs.getInt("id"),
                    rs.getInt("id_enfermera"),
                    rs.getInt("id_cama"),
                    rs.getObject("fecha", LocalDateTime.class).toLocalDate(),
                    rs.getString("turno")
                );
                asignaciones.add(asignacionCama);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    public void update(AsignacionCama asignacionCama) {
        String sql = "UPDATE AsignacionCama SET id_enfermera = ?, id_cama = ?, fecha = ?, turno = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, asignacionCama.getIdEnfermera());
            pstmt.setInt(2, asignacionCama.getIdCama());
            pstmt.setObject(3, asignacionCama.getFecha());
            pstmt.setString(4, asignacionCama.getTurno());
            pstmt.setInt(5, asignacionCama.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(AsignacionCama asignacionCama) {
        String sql = "DELETE FROM AsignacionCama WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, asignacionCama.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

