import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicoDAO {
    private Connection connection;

    public MedicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void createMedico(Medico medico) {
        String sqlPersona = "INSERT INTO Persona (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";
        String sqlMedico = "INSERT INTO Medico (id_persona, especialidad, tipo) VALUES (?, ?, ?)";
        try (PreparedStatement stmtPersona = connection.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtMedico = connection.prepareStatement(sqlMedico)) {

            stmtPersona.setString(1, medico.getNombre());
            stmtPersona.setString(2, medico.getApellido());
            stmtPersona.setString(3, medico.getDireccion());
            stmtPersona.setString(4, medico.getTelefono());
            int affectedRows = stmtPersona.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmtPersona.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idPersona = generatedKeys.getInt(1);
                    stmtMedico.setInt(1, idPersona);
                    stmtMedico.setString(2, medico.getEspecialidad());
                    stmtMedico.setString(3, medico.getTipo());
                    stmtMedico.executeUpdate();
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Medico readMedico(int id) {
        String sql = "SELECT * FROM Medico INNER JOIN Persona ON Medico.id_persona = Persona.id WHERE Medico.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medico(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("especialidad"),
                        rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateMedico(Medico medico) {
        String sql = "UPDATE Medicos SET id_persona = ?, nombre = ?, apellido = ?, direccion = ?, telefono = ?, especialidad = ?, tipo = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, medico.getIdPersona());
            stmt.setString(2, medico.getNombre());
            stmt.setString(3, medico.getApellido());
            stmt.setString(4, medico.getDireccion());
            stmt.setString(5, medico.getTelefono());
            stmt.setString(6, medico.getEspecialidad());
            stmt.setString(7, medico.getTipo());
            stmt.setInt(8, medico.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMedico(int id) {
        String sql = "DELETE FROM medicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medico> getAllMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medicos";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("especialidad"),
                        rs.getString("tipo"));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }

    public static void gestionMedicos(Scanner scanner) throws SQLException {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear un nuevo médico");
        System.out.println("2. Ver todos los médicos");
        System.out.println("3. Actualizar un médico");
        System.out.println("4. Eliminar un médico");
        System.out.println("5. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        MedicoDAO medicoDAO = new MedicoDAO(DatabaseConnection.getConnection());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el ID del médico:");
                int id = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.println("Ingrese el nombre del médico:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el apellido del médico:");
                String apellido = scanner.nextLine();
                System.out.println("Ingrese la dirección del médico:");
                String direccion = scanner.nextLine();
                System.out.println("Ingrese el teléfono del médico:");
                String telefono = scanner.nextLine();
                System.out.println("Ingrese la especialidad del médico:");
                String especialidad = scanner.nextLine();
                System.out.println("Ingrese el tipo del médico:");
                String tipo = scanner.nextLine();

                Medico nuevoMedico = new Medico(id, nombre, apellido, direccion, telefono, especialidad, tipo);
                medicoDAO.createMedico(nuevoMedico);
                System.out.println("Nuevo médico creado con éxito.");
                break;
            case 2:
                List<Medico> medicos = medicoDAO.getAllMedicos();
                if (medicos.isEmpty()) {
                    System.out.println("No hay médicos para mostrar.");
                } else {
                    System.out.println("Médicos:");
                    for (Medico medico : medicos) {
                        System.out.println(medico);
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el ID del médico que desea actualizar:");
                int idActualizar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                Medico medicoActualizar = medicoDAO.readMedico(idActualizar);
                if (medicoActualizar != null) {
                    System.out.println("Ingrese el nuevo nombre del médico:");
                    String nuevoNombre = scanner.nextLine();
                    System.out.println("Ingrese el nuevo apellido del médico:");
                    String nuevoApellido = scanner.nextLine();
                    System.out.println("Ingrese la nueva dirección del médico:");
                    String nuevaDireccion = scanner.nextLine();
                    System.out.println("Ingrese el nuevo teléfono del médico:");
                    String nuevoTelefono = scanner.nextLine();
                    System.out.println("Ingrese la nueva especialidad del médico:");
                    String nuevaEspecialidad = scanner.nextLine();
                    System.out.println("Ingrese el nuevo tipo del médico:");
                    String nuevoTipo = scanner.nextLine();

                    medicoActualizar.setNombre(nuevoNombre);
                    medicoActualizar.setApellido(nuevoApellido);
                    medicoActualizar.setDireccion(nuevaDireccion);
                    medicoActualizar.setTelefono(nuevoTelefono);
                    medicoActualizar.setEspecialidad(nuevaEspecialidad);
                    medicoActualizar.setTipo(nuevoTipo);

                    medicoDAO.updateMedico(medicoActualizar);
                    System.out.println("Médico actualizado con éxito.");
                } else {
                    System.out.println("El médico con el ID especificado no existe.");
                }
                break;
            case 4:
                System.out.println("Ingrese el ID del médico que desea eliminar:");
                int idEliminar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                Medico medicoEliminar = medicoDAO.readMedico(idEliminar);
                if (medicoEliminar != null) {
                    medicoDAO.deleteMedico(idEliminar);
                    System.out.println("Médico eliminado con éxito.");
                } else {
                    System.out.println("El médico con el ID especificado no existe.");
                }
                break;
            case 5:
                // No se requiere ninguna acción, simplemente salir del switch
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

}
