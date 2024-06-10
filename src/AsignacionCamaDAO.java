import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionCamaDAO extends DAO<AsignacionCama> {
    private Connection connection;

    public AsignacionCamaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(AsignacionCama asignacionCama) {
        String query = "INSERT INTO asignacioncamas (id_enfermera, id_cama, fecha, turno) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacionCama.getIdEnfermera());
            statement.setInt(2, asignacionCama.getIdCama());
            statement.setDate(3, java.sql.Date.valueOf(asignacionCama.getFecha()));
            statement.setString(4, asignacionCama.getTurno());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AsignacionCama getById(int id) {
        String query = "SELECT * FROM asignacioncamas WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int idEnfermera = rs.getInt("id_enfermera");
                int idCama = rs.getInt("id_cama");
                java.sql.Date fecha = rs.getDate("fecha");
                String turno = rs.getString("turno");
                return new AsignacionCama(id, idEnfermera, idCama, fecha.toLocalDate(), turno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AsignacionCama> getAll() {
        List<AsignacionCama> asignaciones = new ArrayList<>();
        String query = "SELECT * FROM asignacioncamas";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idEnfermera = rs.getInt("id_enfermera");
                int idCama = rs.getInt("id_cama");
                java.sql.Date fecha = rs.getDate("fecha");
                String turno = rs.getString("turno");
                asignaciones.add(new AsignacionCama(id, idEnfermera, idCama, fecha.toLocalDate(), turno));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    @Override
    public void update(AsignacionCama asignacionCama) {
        String query = "UPDATE asignacioncamas SET id_enfermera = ?, id_cama = ?, fecha = ?, turno = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacionCama.getIdEnfermera());
            statement.setInt(2, asignacionCama.getIdCama());
            statement.setDate(3, java.sql.Date.valueOf(asignacionCama.getFecha()));
            statement.setString(4, asignacionCama.getTurno());
            statement.setInt(5, asignacionCama.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(AsignacionCama asignacionCama) {
        String query = "DELETE FROM asignacioncamas WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacionCama.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAsignacionCama(AsignacionCama asignacionCama) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAsignacionCama'");
    }
}

