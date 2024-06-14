import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnfermeraDAO {
    private Connection connection;

    public EnfermeraDAO(Connection connection) {
        this.connection = connection;
    }

    public void createEnfermera(Enfermera enfermera) {
        String sqlPersona = "INSERT INTO Persona (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";
        String sqlEnfermera = "INSERT INTO enfermera (id_persona, area) VALUES (?, ?)";
        try (PreparedStatement stmtPersona = connection.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtEnfermera = connection.prepareStatement(sqlEnfermera)) {

            stmtPersona.setString(1, enfermera.getNombre());
            stmtPersona.setString(2, enfermera.getApellido());
            stmtPersona.setString(3, enfermera.getDireccion());
            stmtPersona.setString(4, enfermera.getTelefono());
            int affectedRows = stmtPersona.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmtPersona.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idPersona = generatedKeys.getInt(1);
                    stmtEnfermera.setInt(1, idPersona);
                    stmtEnfermera.setString(2, enfermera.getArea());
                    stmtEnfermera.executeUpdate();
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Enfermera readEnfermera(int id) {
        String sql = "SELECT * FROM enfermera INNER JOIN Persona ON enfermera.id_persona = Persona.id WHERE enfermera.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Enfermera(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("area"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateEnfermera(Enfermera enfermera) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE id = ?";
        String sqlEnfermera = "UPDATE enfermera SET area = ? WHERE id_persona = ?";
        try (PreparedStatement stmtPersona = connection.prepareStatement(sqlPersona);
             PreparedStatement stmtEnfermera = connection.prepareStatement(sqlEnfermera)) {

            stmtPersona.setString(1, enfermera.getNombre());
            stmtPersona.setString(2, enfermera.getApellido());
            stmtPersona.setString(3, enfermera.getDireccion());
            stmtPersona.setString(4, enfermera.getTelefono());
            stmtPersona.setInt(5, enfermera.getIdPersona());
            stmtPersona.executeUpdate();

            stmtEnfermera.setString(1, enfermera.getArea());
            stmtEnfermera.setInt(2, enfermera.getIdPersona());
            stmtEnfermera.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEnfermera(int id) {
        String sqlEnfermera = "DELETE FROM enfermera WHERE id = ?";
        String sqlPersona = "DELETE FROM Persona WHERE id = ?";
        try (PreparedStatement stmtEnfermera = connection.prepareStatement(sqlEnfermera);
             PreparedStatement stmtPersona = connection.prepareStatement(sqlPersona)) {

            stmtEnfermera.setInt(1, id);
            stmtEnfermera.executeUpdate();

            stmtPersona.setInt(1, id);
            stmtPersona.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Enfermera> getAllEnfermeras() {
        List<Enfermera> enfermeras = new ArrayList<>();
        String sql = "SELECT * FROM enfermera INNER JOIN Persona ON enfermera.id_persona = Persona.id";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Enfermera enfermera = new Enfermera(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("area"));
                enfermeras.add(enfermera);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enfermeras;
    }

    public static void gestionEnfermeras(Scanner scanner) throws SQLException {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear una nueva enfermera");
        System.out.println("2. Ver todas las enfermeras");
        System.out.println("3. Actualizar una enfermera");
        System.out.println("4. Eliminar una enfermera");
        System.out.println("5. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        EnfermeraDAO enfermeraDAO = new EnfermeraDAO(DatabaseConnection.getConnection());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nombre de la enfermera:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el apellido de la enfermera:");
                String apellido = scanner.nextLine();
                System.out.println("Ingrese la dirección de la enfermera:");
                String direccion = scanner.nextLine();
                System.out.println("Ingrese el teléfono de la enfermera:");
                String telefono = scanner.nextLine();
                System.out.println("Ingrese el área de la enfermera:");
                String area = scanner.nextLine();

                Enfermera nuevaEnfermera = new Enfermera(nombre, apellido, direccion, telefono, area);
                enfermeraDAO.createEnfermera(nuevaEnfermera);
                System.out.println("Nueva enfermera creada con éxito.");
                break;
            case 2:
                List<Enfermera> enfermeras = enfermeraDAO.getAllEnfermeras();
                if (enfermeras.isEmpty()) {
                    System.out.println("No hay enfermeras para mostrar.");
                } else {
                    System.out.println("Enfermeras:");
                    for (Enfermera enfermera : enfermeras) {
                        System.out.println(enfermera);
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el ID de la enfermera que desea actualizar:");
                int idActualizar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                Enfermera enfermeraActualizar = enfermeraDAO.readEnfermera(idActualizar);
                if (enfermeraActualizar != null) {
                    System.out.println("Ingrese el nuevo nombre de la enfermera:");
                    String nuevoNombre = scanner.nextLine();
                    System.out.println("Ingrese el nuevo apellido de la enfermera:");
                    String nuevoApellido = scanner.nextLine();
                    System.out.println("Ingrese la nueva dirección de la enfermera:");
                    String nuevaDireccion = scanner.nextLine();
                    System.out.println("Ingrese el nuevo teléfono de la enfermera:");
                    String nuevoTelefono = scanner.nextLine();
                    System.out.println("Ingrese el nuevo área de la enfermera:");
                    String nuevoArea = scanner.nextLine();

                    enfermeraActualizar.setNombre(nuevoNombre);
                    enfermeraActualizar.setApellido(nuevoApellido);
                    enfermeraActualizar.setDireccion(nuevaDireccion);
                    enfermeraActualizar.setTelefono(nuevoTelefono);
                    enfermeraActualizar.setArea(nuevoArea);

                    enfermeraDAO.updateEnfermera(enfermeraActualizar);
                    System.out.println("Enfermera actualizada con éxito.");
                } else {
                    System.out.println("La enfermera con el ID especificado no existe.");
                }
                break;
            case 4:
                System.out.println("Ingrese el ID de la enfermera que desea eliminar:");
                int idEliminar = scanner.nextInt();
                scanner.nextLine();
                enfermeraDAO.deleteEnfermera(idEliminar);
                System.out.println("Enfermera eliminada con éxito.");
                break;
            case 5:
                System.out.println("Volviendo al menú principal.");
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }
}

