// TestPacienteDAO.java
import java.sql.Date;

public class TestPacienteDAO {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        
        // Crear un nuevo paciente
        Paciente nuevoPaciente = new Paciente(12345678, "Juan", "Perez", Date.valueOf("1985-10-10"), "Calle Falsa 123", "555-1234");
        pacienteDAO.createPaciente(nuevoPaciente);
        System.out.println("Paciente creado.");

        // Leer el paciente
        Paciente paciente = pacienteDAO.readPaciente(12345678);
        System.out.println("Paciente leído: " + paciente.getNombre() + " " + paciente.getApellido());

        // Actualizar el paciente
        paciente.setDireccion("Avenida Siempre Viva 742");
        pacienteDAO.updatePaciente(paciente);
        System.out.println("Paciente actualizado.");

        // Leer el paciente actualizado
        Paciente pacienteActualizado = pacienteDAO.readPaciente(12345678);
        System.out.println("Paciente actualizado: " + pacienteActualizado.getDireccion());

        // Eliminar el paciente
        pacienteDAO.deletePaciente(12345678);
        System.out.println("Paciente eliminado.");

        // Verificar la eliminación
        Paciente pacienteEliminado = pacienteDAO.readPaciente(12345678);
        if (pacienteEliminado == null) {
            System.out.println("Verificación de eliminación exitosa.");
        } else {
            System.out.println("Error: El paciente no fue eliminado.");
        }
    }
}