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
            System.out.println("3. Insertar asignación a cama");
            System.out.println("4. Insertar nuevo médico");
            System.out.println("5. Insertar historia clínica");
            System.out.println("6. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    insertarPaciente(scanner);
                    break;
                case 2:
                    insertarSector(scanner);
                    break;
                case 3:
                    insertarAsignacionCama(scanner);
                    break;
                case 4:
                    insertarMedico(scanner);
                    break;
                case 5:
                    insertarHistoriaClinica(scanner);
                    break;
                case 6:
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

    private static void insertarAsignacionCama(Scanner scanner) {
        AsignacionCamaDAO asignacionCamaDAO = new AsignacionCamaDAO();

        System.out.println("Ingrese el ID de la enfermera:");
        int idEnfermera = scanner.nextInt();

        System.out.println("Ingrese el ID de la cama:");
        int idCama = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Ingrese la fecha (yyyy-mm-dd):");
        String fechaStr = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.println("Ingrese el turno:");
        String turno = scanner.nextLine();

        AsignacionCama asignacionCama = new AsignacionCama(idEnfermera, idCama, idCama, fecha, turno);
        asignacionCamaDAO.createAsignacionCama(asignacionCama);

        System.out.println("Asignación a cama insertada exitosamente.");
    }

    private static void insertarMedico(Scanner scanner) {
        MedicoDAO medicoDAO = new MedicoDAO();

        System.out.println("Ingrese el nombre del médico:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del médico:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese la especialidad del médico:");
        String especialidad = scanner.nextLine();

        Medico medico = new Medico(0, nombre, apellido, especialidad);
        medicoDAO.createMedico(medico);

        System.out.println("Médico insertado exitosamente.");
    }

    private static void insertarHistoriaClinica(Scanner scanner) {
        HistoriaClinicaDAO historiaClinicaDAO = new HistoriaClinicaDAO();

        System.out.println("Ingrese el ID del paciente:");
        int idPaciente = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Ingrese la descripción de la historia clínica:");
        String problema = scanner.nextLine();

        System.out.println("Ingrese la descripción del tratamiento del paciente:");
        String tratamiento = scanner.nextLine();

        System.out.println("Ingrese la fecha de creación (yyyy-mm-dd):");
        String fechaCreacionStr = scanner.nextLine();
        LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);

        HistoriaClinica historiaClinica = new HistoriaClinica(0, idPaciente, problema, tratamiento, fechaCreacion);
        historiaClinicaDAO.createHistoriaClinica(historiaClinica);

        System.out.println("Historia clínica insertada exitosamente.");
    }
}