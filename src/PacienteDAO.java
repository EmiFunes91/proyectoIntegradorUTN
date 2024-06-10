import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PacienteDAO {
    private Connection connection;

    public PacienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void createPaciente(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nombre, apellido, direccion, telefono, dni, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getDireccion());
            stmt.setString(4, paciente.getTelefono());
            stmt.setString(5, paciente.getDni());
            stmt.setString(6, paciente.getFechaNacimiento());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Paciente readPaciente(String dni) {
        String sql = "SELECT * FROM pacientes WHERE dni = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paciente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("dni"),
                        rs.getString("fecha_nacimiento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePaciente(Paciente paciente) {
        String sql = "UPDATE pacientes SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE dni = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getDireccion());
            stmt.setString(4, paciente.getTelefono());
            stmt.setString(5, paciente.getFechaNacimiento());
            stmt.setString(6, paciente.getDni());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePaciente(String dni) {
        String sql = "DELETE FROM pacientes WHERE dni = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Paciente> getAllPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("dni"),
                        rs.getString("fecha_nacimiento"));
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public static void gestionPacientes(Scanner scanner) throws SQLException {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear un nuevo paciente");
        System.out.println("2. Ver todos los pacientes");
        System.out.println("3. Actualizar un paciente");
        System.out.println("4. Eliminar un paciente");
        System.out.println("5. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        PacienteDAO pacienteDAO = new PacienteDAO(DatabaseConnection.getConnection());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nombre del paciente:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el apellido del paciente:");
                String apellido = scanner.nextLine();
                System.out.println("Ingrese la dirección del paciente:");
                String direccion = scanner.nextLine();
                System.out.println("Ingrese el teléfono del paciente:");
                String telefono = scanner.nextLine();
                System.out.println("Ingrese el DNI del paciente:");
                String dni = scanner.nextLine();
                System.out.println("Ingrese la fecha de nacimiento del paciente (AAAA-MM-DD):");
                String fechaNacimiento = scanner.nextLine();

                Paciente nuevoPaciente = new Paciente(nombre, apellido, direccion, telefono, dni, fechaNacimiento);
                pacienteDAO.createPaciente(nuevoPaciente);
                System.out.println("Nuevo paciente creado con éxito.");
                break;
            case 2:
                List<Paciente> pacientes = pacienteDAO.getAllPacientes();
                if (pacientes.isEmpty()) {
                    System.out.println("No hay pacientes para mostrar.");
                } else {
                    System.out.println("Pacientes:");
                    for (Paciente paciente : pacientes) {
                        System.out.println(paciente);
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el DNI del paciente que desea actualizar:");
                String dniActualizar = scanner.nextLine();
                Paciente pacienteActualizar = pacienteDAO.readPaciente(dniActualizar);
                if (pacienteActualizar != null) {
                    System.out.println("Ingrese el nuevo nombre del paciente:");
                    String nuevoNombre = scanner.nextLine();
                    System.out.println("Ingrese el nuevo apellido del paciente:");
                    String nuevoApellido = scanner.nextLine();
                    System.out.println("Ingrese la nueva dirección del paciente:");
                    String nuevaDireccion = scanner.nextLine();
                    System.out.println("Ingrese el nuevo teléfono del paciente:");
                    String nuevoTelefono = scanner.nextLine();
                    System.out.println("Ingrese la nueva fecha de nacimiento del paciente (AAAA-MM-DD):");
                    String nuevaFechaNacimiento = scanner.nextLine();

                    pacienteActualizar.setNombre(nuevoNombre);
                    pacienteActualizar.setApellido(nuevoApellido);
                    pacienteActualizar.setDireccion(nuevaDireccion);
                    pacienteActualizar.setTelefono(nuevoTelefono);
                    pacienteActualizar.setFechaNacimiento(nuevaFechaNacimiento);

                    pacienteDAO.updatePaciente(pacienteActualizar);
                    System.out.println("Paciente actualizado con éxito.");
                } else {
                    System.out.println("El paciente con el DNI especificado no existe.");
                }
                break;
            case 4:
                System.out.println("Ingrese el DNI del paciente que desea eliminar:");
                String dniEliminar = scanner.nextLine();
                Paciente pacienteEliminar = pacienteDAO.readPaciente(dniEliminar);
                if (pacienteEliminar != null) {
                    pacienteDAO.deletePaciente(dniEliminar);
                    System.out.println("Paciente eliminado con éxito.");
                } else {
                    System.out.println("El paciente con el DNI especificado no existe.");
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
