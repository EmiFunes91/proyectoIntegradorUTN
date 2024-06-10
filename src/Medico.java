class Medico extends Persona {
    private String especialidad;
    private String tipo;

    public Medico(int id, String nombre, String apellido, String direccion, String telefono, String especialidad, String tipo) {
        super(id, nombre, apellido, direccion, telefono);
        this.especialidad = especialidad;
        this.tipo = tipo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

