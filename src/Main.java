import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Menu Principal:");
            System.out.println("1. Gestion de Pacientes");
            System.out.println("2. Gestion de Medicos");
            System.out.println("3. Gestion de Enfermeras");
            System.out.println("4. Gestion de Historias Clinicas");
            System.out.println("5. Gestion de Asignaciones");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    PacienteDAO.gestionPacientes(scanner);
                    break;
                case 2:
                    MedicoDAO.gestionMedicos(scanner);
                    break;
                case 3:
                    EnfermeraDAO.gestionEnfermeras(scanner);
                    break;
                case 4:
                    HistoriaClinicaDAO.gestionHistoriasClinicas(scanner);
                    break;
                case 5:
                    AsignacionCamaDAO.gestionAsignaciones(scanner);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (option != 6);

        scanner.close();
    }
}