import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoriaClinicaDAO {
    private Connection connection;

    public HistoriaClinicaDAO(Connection connection) {
        this.connection = connection;
    }

    public void createHistoriaClinica(HistoriaClinica historiaClinica) {
        String sql = "INSERT INTO historiasclinicas (id_paciente, problema, fecha_creacion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, historiaClinica.getIdPaciente());
            stmt.setString(2, historiaClinica.getProblema());
            stmt.setDate(3, new java.sql.Date(historiaClinica.getFechaCreacion().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HistoriaClinica readHistoriaClinica(int id) {
        String sql = "SELECT * FROM historiasclinicas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HistoriaClinica(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getString("problema"),
                        rs.getDate("fecha_creacion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateHistoriaClinica(HistoriaClinica historiaClinica) {
        String sql = "UPDATE historiasclinicas SET id_paciente = ?, problema = ?, fecha_creacion = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, historiaClinica.getIdPaciente());
            stmt.setString(2, historiaClinica.getProblema());
            stmt.setDate(3, new java.sql.Date(historiaClinica.getFechaCreacion().getTime()));
            stmt.setInt(4, historiaClinica.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHistoriaClinica(int id) {
        String sql = "DELETE FROM historiasclinicas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HistoriaClinica> getAllHistoriasClinicas() {
        List<HistoriaClinica> historiasClinicas = new ArrayList<>();
        String sql = "SELECT * FROM historiasclinicas";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HistoriaClinica historiaClinica = new HistoriaClinica(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getString("problema"),
                        rs.getDate("fecha_creacion")
                );
                historiasClinicas.add(historiaClinica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiasClinicas;
    }

    public static void gestionHistoriasClinicas(Scanner scanner) throws SQLException {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Crear una nueva historia clínica");
        System.out.println("2. Ver todas las historias clínicas");
        System.out.println("3. Actualizar una historia clínica");
        System.out.println("4. Eliminar una historia clínica");
        System.out.println("5. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        HistoriaClinicaDAO historiaClinicaDAO = new HistoriaClinicaDAO(DatabaseConnection.getConnection());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el ID del paciente:");
                int idPaciente = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.println("Ingrese el problema:");
                String problema = scanner.nextLine();
                System.out.println("Ingrese la fecha de creación (AAAA-MM-DD):");
                String fechaCreacionStr = scanner.nextLine();
                Date fechaCreacion = Date.valueOf(fechaCreacionStr);

                HistoriaClinica nuevaHistoriaClinica = new HistoriaClinica(idPaciente, problema, fechaCreacion);
                historiaClinicaDAO.createHistoriaClinica(nuevaHistoriaClinica);
                System.out.println("Nueva historia clínica creada con éxito.");
                break;
            case 2:
                List<HistoriaClinica> historiasClinicas = historiaClinicaDAO.getAllHistoriasClinicas();
                for (HistoriaClinica historiaClinica : historiasClinicas) {
                    System.out.println(historiaClinica);
                }
                break;
            case 3:
                System.out.println("Ingrese el ID de la historia clínica a actualizar:");
                int id = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada

                HistoriaClinica historiaClinica = historiaClinicaDAO.readHistoriaClinica(id);
                if (historiaClinica == null) {
                    System.out.println("Historia clínica no encontrada.");
                    break;
                }

                System.out.println("Ingrese el nuevo ID del paciente:");
                idPaciente = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.println("Ingrese el nuevo problema:");
                problema = scanner.nextLine();
                System.out.println("Ingrese la nueva fecha de creación (AAAA-MM-DD):");
                fechaCreacionStr = scanner.nextLine();
                fechaCreacion = Date.valueOf(fechaCreacionStr);

                historiaClinica = new HistoriaClinica(id, idPaciente, problema, fechaCreacion);
                historiaClinicaDAO.updateHistoriaClinica(historiaClinica);
                System.out.println("Historia clínica actualizada con éxito.");
                break;
            case 4:
                System.out.println("Ingrese el ID de la historia clínica a eliminar:");
                id = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                historiaClinicaDAO.deleteHistoriaClinica(id);
                System.out.println("Historia clínica eliminada con éxito.");
                break;
            case 5:
                return;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }
}

