import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AsignacionCamaDAO {
    private Connection connection;

    public AsignacionCamaDAO(Connection connection) {
        this.connection = connection;
    }

    public void createAsignacionCama(AsignacionCama asignacionCama) {
        String sql = "INSERT INTO asignaciones_cama (id_enfermera, id_cama, fecha, turno) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, asignacionCama.getIdEnfermera());
            stmt.setInt(2, asignacionCama.getIdCama());
            stmt.setTimestamp(3, Timestamp.valueOf(asignacionCama.getFecha()));
            stmt.setString(4, asignacionCama.getTurno());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AsignacionCama getById(int id) {
        String sql = "SELECT * FROM asignaciones_cama WHERE id = ?";
        AsignacionCama asignacionCama = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                asignacionCama = new AsignacionCama(
                        rs.getInt("id"),
                        rs.getInt("id_enfermera"),
                        rs.getInt("id_cama"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("turno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignacionCama;
    }

    public List<AsignacionCama> getAll() {
        List<AsignacionCama> asignaciones = new ArrayList<>();
        String sql = "SELECT * FROM asignaciones_cama";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                AsignacionCama asignacionCama = new AsignacionCama(
                        rs.getInt("id"),
                        rs.getInt("id_enfermera"),
                        rs.getInt("id_cama"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("turno"));
                asignaciones.add(asignacionCama);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    public void update(AsignacionCama asignacionCama) {
        String sql = "UPDATE asignaciones_cama SET id_enfermera = ?, id_cama = ?, fecha = ?, turno = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, asignacionCama.getIdEnfermera());
            pstmt.setInt(2, asignacionCama.getIdCama());
            pstmt.setTimestamp(3, Timestamp.valueOf(asignacionCama.getFecha()));
            pstmt.setString(4, asignacionCama.getTurno());
            pstmt.setInt(5, asignacionCama.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(AsignacionCama asignacionCama) {
        String sql = "DELETE FROM asignaciones_cama WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, asignacionCama.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void gestionAsignaciones(Scanner scanner) throws SQLException {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear una nueva asignación de cama");
        System.out.println("2. Ver todas las asignaciones de cama");
        System.out.println("3. Actualizar una asignación de cama");
        System.out.println("4. Eliminar una asignación de cama");
        System.out.println("5. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        AsignacionCamaDAO asignacionCamaDAO = new AsignacionCamaDAO(DatabaseConnection.getConnection());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el ID de la enfermera:");
                int idEnfermera = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.println("Ingrese el ID de la cama:");
                int idCama = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.println("Ingrese la fecha (AAAA-MM-DD HH:mm:ss):");
                String fechaStr = scanner.nextLine();
                LocalDateTime fecha = LocalDateTime.parse(fechaStr, formatter);
                System.out.println("Ingrese el turno:");
                String turno = scanner.nextLine();

                AsignacionCama nuevaAsignacion = new AsignacionCama(idEnfermera, idCama, fecha, turno);
                asignacionCamaDAO.createAsignacionCama(nuevaAsignacion);
                System.out.println("Nueva asignación de cama creada con éxito.");
                break;
            case 2:
                List<AsignacionCama> asignaciones = asignacionCamaDAO.getAll();
                if (asignaciones.isEmpty()) {
                    System.out.println("No hay asignaciones de cama para mostrar.");
                } else {
                    System.out.println("Asignaciones de Cama:");
                    for (AsignacionCama asignacion : asignaciones) {
                        System.out.println(asignacion);
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el ID de la asignación de cama que desea actualizar:");
                int idActualizar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                AsignacionCama asignacionActualizar = asignacionCamaDAO.getById(idActualizar);
                if (asignacionActualizar != null) {
                    System.out.println("Ingrese el nuevo ID de la enfermera:");
                    int nuevoIdEnfermera = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    System.out.println("Ingrese el nuevo ID de la cama:");
                    int nuevoIdCama = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    System.out.println("Ingrese la nueva fecha (AAAA-MM-DD HH:mm:ss):");
                    String nuevaFechaStr = scanner.nextLine();
                    LocalDateTime nuevaFecha = LocalDateTime.parse(nuevaFechaStr, formatter);
                    System.out.println("Ingrese el nuevo turno:");
                    String nuevoTurno = scanner.nextLine();

                    asignacionActualizar.setIdEnfermera(nuevoIdEnfermera);
                    asignacionActualizar.setIdCama(nuevoIdCama);
                    asignacionActualizar.setFecha(nuevaFecha);
                    asignacionActualizar.setTurno(nuevoTurno);

                    asignacionCamaDAO.update(asignacionActualizar);
                    System.out.println("Asignación de cama actualizada con éxito.");
                } else {
                    System.out.println("La asignación de cama con el ID especificado no existe.");
                }
                break;
            case 4:
                System.out.println("Ingrese el ID de la asignación de cama que desea eliminar:");
                int idEliminar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                AsignacionCama asignacionEliminar = asignacionCamaDAO.getById(idEliminar);
                if (asignacionEliminar != null) {
                    asignacionCamaDAO.delete(asignacionEliminar);
                    System.out.println("Asignación de cama eliminada con éxito.");
                } else {
                    System.out.println("La asignación de cama con el ID especificado no existe.");
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


