class Paciente extends Persona {
    private String dni;
    private String fechaNacimiento;

    public Paciente(String nombre, String apellido, String direccion, String telefono, String dni, String fechaNacimiento) {
        super(nombre, apellido, direccion, telefono);
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}

