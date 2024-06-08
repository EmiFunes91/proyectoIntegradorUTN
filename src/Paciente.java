import java.time.LocalDate;

public class Paciente extends Persona {
    private LocalDate fechaNacimiento;

    // Constructor
    public Paciente(int id, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono) {
        super(id, nombre, apellido, direccion, telefono);
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}

