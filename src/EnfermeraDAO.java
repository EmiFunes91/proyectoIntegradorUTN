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
        String sql = "INSERT INTO enfermeras (id, nombre, apellido, direccion, telefono, area) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, enfermera.getId());
            stmt.setString(2, enfermera.getNombre());
            stmt.setString(3, enfermera.getApellido());
            stmt.setString(4, enfermera.getDireccion());
            stmt.setString(5, enfermera.getTelefono());
            stmt.setString(6, enfermera.getArea());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Enfermera readEnfermera(int id) {
        String sql = "SELECT * FROM enfermeras WHERE id = ?";
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
                        rs.getString("area")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateEnfermera(Enfermera enfermera) {
        String sql = "UPDATE enfermeras SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, area = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, enfermera.getNombre());
            stmt.setString(2, enfermera.getApellido());
            stmt.setString(3, enfermera.getDireccion());
            stmt.setString(4, enfermera.getTelefono());
            stmt.setString(5, enfermera.getArea());
            stmt.setInt(6, enfermera.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEnfermera(int id) {
        String sql = "DELETE FROM enfermeras WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Enfermera> getAllEnfermeras() {
        List<Enfermera> enfermeras = new ArrayList<>();
        String sql = "SELECT * FROM enfermeras";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Enfermera enfermera = new Enfermera(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("area")
                );
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
                System.out.println("Ingrese el ID de la enfermera:");
                int id = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
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

                Enfermera nuevaEnfermera = new Enfermera(id, nombre, apellido, direccion, telefono, area);
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
                    String nuevaArea = scanner.nextLine();

                    enfermeraActualizar.setNombre(nuevoNombre);
                    enfermeraActualizar.setApellido(nuevoApellido);
                    enfermeraActualizar.setDireccion(nuevaDireccion);
                    enfermeraActualizar.setTelefono(nuevoTelefono);
                    enfermeraActualizar.setArea(nuevaArea);

                    enfermeraDAO.updateEnfermera(enfermeraActualizar);
                    System.out.println("Enfermera actualizada con éxito.");
                } else {
                    System.out.println("La enfermera con el ID especificado no existe.");
                }
                break;
            case 4:
                System.out.println("Ingrese el ID de la enfermera que desea eliminar:");
                int idEliminar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                enfermeraDAO.deleteEnfermera(idEliminar);
                System.out.println("Enfermera eliminada con éxito.");
                break;
            case 5:
                // Volver al menú principal
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
}
