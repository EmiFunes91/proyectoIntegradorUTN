import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Insertar nuevo paciente");
            System.out.println("2. Insertar nuevo sector");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (opcion) {
                case 1:
                    insertarPaciente(scanner);
                    break;
                case 2:
                    insertarSector(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void insertarPaciente(Scanner scanner) {
        PacienteDAO pacienteDAO = new PacienteDAO();

        System.out.println("Ingrese el nombre del paciente:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del paciente:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento del paciente (yyyy-mm-dd):");
        String fechaNacimientoStr = scanner.nextLine();
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.println("Ingrese la dirección del paciente:");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese el teléfono del paciente:");
        String telefono = scanner.nextLine();

        Paciente paciente = new Paciente(0, nombre, apellido, fechaNacimiento, direccion, telefono);
        pacienteDAO.createPaciente(paciente);

        System.out.println("Paciente insertado exitosamente.");
    }

    private static void insertarSector(Scanner scanner) {
        SectorDAO sectorDAO = new SectorDAO();

        System.out.println("Ingrese el nombre del sector:");
        String nombreSector = scanner.nextLine();

        System.out.println("Ingrese la cantidad de camas:");
        int cantCamas = scanner.nextInt();

        System.out.println("Es reservable (true/false):");
        boolean reservable = scanner.nextBoolean();

        Sector sector = new Sector(nombreSector, cantCamas, reservable);
        sectorDAO.createSector(sector);

        System.out.println("Sector insertado exitosamente.");
    }
}

