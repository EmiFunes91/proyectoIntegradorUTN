import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoriaClinicaDAO {

    // Crear una nueva historia clínica
    public void createHistoriaClinica(HistoriaClinica historiaClinica) {
        String sql = "INSERT INTO HistoriasClinicas (id_paciente, problema, fecha_creacion) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, historiaClinica.getIdPaciente());
            pstmt.setString(2, historiaClinica.getProblema());
            pstmt.setDate(3, java.sql.Date.valueOf(historiaClinica.getFechaCreacion()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtener una historia clínica por su ID
    public HistoriaClinica getHistoriaClinicaById(int id) {
        String sql = "SELECT * FROM HistoriasClinicas WHERE id = ?";
        HistoriaClinica historiaClinica = null;
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                historiaClinica = new HistoriaClinica(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getString("problema"),
                        sql, rs.getDate("fecha_creacion").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiaClinica;
    }

    // Obtener todas las historias clínicas
    public List<HistoriaClinica> getAllHistoriasClinicas() {
        List<HistoriaClinica> historiasClinicas = new ArrayList<>();
        String sql = "SELECT * FROM HistoriasClinicas";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                HistoriaClinica historiaClinica = new HistoriaClinica(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getString("problema"),
                        sql, rs.getDate("fecha_creacion").toLocalDate());
                historiasClinicas.add(historiaClinica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiasClinicas;
    }

    // Actualizar una historia clínica
    public void updateHistoriaClinica(HistoriaClinica historiaClinica) {
        String sql = "UPDATE HistoriasClinicas SET id_paciente = ?, problema = ?, fecha_creacion = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, historiaClinica.getIdPaciente());
            pstmt.setString(2, historiaClinica.getProblema());
            pstmt.setDate(3, java.sql.Date.valueOf(historiaClinica.getFechaCreacion()));
            pstmt.setInt(4, historiaClinica.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar una historia clínica
    public void deleteHistoriaClinica(int id) {
        String sql = "DELETE FROM HistoriasClinicas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para gestionar las historias clínicas
    public static void gestionHistoriasClinicas(Scanner scanner) {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear una nueva historia clínica");
        System.out.println("2. Ver todas las historias clínicas");
        System.out.println("3. Actualizar una historia clínica");
        System.out.println("4. Eliminar una historia clínica");
        System.out.println("5. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        HistoriaClinicaDAO historiaClinicaDAO = new HistoriaClinicaDAO();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el ID del paciente:");
                int idPaciente = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.println("Ingrese el problema:");
                String problema = scanner.nextLine();
                System.out.println("Ingrese la fecha de creación (AAAA-MM-DD):");
                String fechaCreacionStr = scanner.nextLine();
                LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);

                HistoriaClinica nuevaHistoriaClinica = new HistoriaClinica(idPaciente, idPaciente, problema, problema, fechaCreacion);
                historiaClinicaDAO.createHistoriaClinica(nuevaHistoriaClinica);
                System.out.println("Nueva historia clínica creada con éxito.");
                break;
            case 2:
                List<HistoriaClinica> historiasClinicas = historiaClinicaDAO.getAllHistoriasClinicas();
                if (historiasClinicas.isEmpty()) {
                    System.out.println("No hay historias clínicas para mostrar.");
                } else {
                    System.out.println("Historias Clínicas:");
                    for (HistoriaClinica hc : historiasClinicas) {
                        System.out.println(hc);
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el ID de la historia clínica que desea actualizar:");
                int idActualizar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                HistoriaClinica historiaClinicaActualizar = historiaClinicaDAO.getHistoriaClinicaById(idActualizar);
                if (historiaClinicaActualizar != null) {
                    System.out.println("Ingrese el nuevo ID del paciente:");
                    int nuevoIdPaciente = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    System.out.println("Ingrese el nuevo problema:");
                    String nuevoProblema = scanner.nextLine();
                    System.out.println("Ingrese la nueva fecha de creación (AAAA-MM-DD):");
                    String nuevaFechaCreacionStr = scanner.nextLine();
                    LocalDate nuevaFechaCreacion = LocalDate.parse(nuevaFechaCreacionStr);

                    historiaClinicaActualizar.setIdPaciente(nuevoIdPaciente);
                    historiaClinicaActualizar.setProblema(nuevoProblema);
                    historiaClinicaActualizar.setFechaCreacion(nuevaFechaCreacion);

                    historiaClinicaDAO.updateHistoriaClinica(historiaClinicaActualizar);
                    System.out.println("Historia clínica actualizada con éxito.");
                } else {
                    System.out.println("La historia clínica con el ID especificado no existe.");
                }
                break;
            case 4:
                System.out.println("Ingrese el ID de la historia clínica que desea eliminar:");
                int idEliminar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                historiaClinicaDAO.deleteHistoriaClinica(idEliminar);
                System.out.println("Historia clínica eliminada con éxito.");
                break;
            case 5:
                // No se requiere ninguna acción, simplemente salir del switch
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
}
