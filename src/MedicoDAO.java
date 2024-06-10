import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO extends DAO<Medico> {

    public MedicoDAO(Connection connection) {
        super();
    }

    @Override
    public boolean create(Medico medico) {
        String query = "INSERT INTO medicos (nombre, apellido, especialidad) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medico.getNombre());
            statement.setString(2, medico.getApellido());
            statement.setString(3, medico.getEspecialidad());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Medico retrieve(int id) {
        String query = "SELECT * FROM medicos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String especialidad = resultSet.getString("especialidad");

                return new Medico(id, nombre, apellido, especialidad);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Medico> retrieveAll() {
        List<Medico> medicos = new ArrayList<>();
        String query = "SELECT * FROM medicos";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String especialidad = resultSet.getString("especialidad");

                medicos.add(new Medico(id, nombre, apellido, especialidad));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return medicos;
    }

    @Override
    public boolean update(Medico medico) {
        String query = "UPDATE medicos SET nombre = ?, apellido = ?, especialidad = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medico.getNombre());
            statement.setString(2, medico.getApellido());
            statement.setString(3, medico.getEspecialidad());
            statement.setInt(4, medico.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM medicos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
