public class Enfermera extends Persona {
    private String area;

    public Enfermera(String nombre, String apellido, String direccion, String telefono, String area) {
        super(nombre, apellido, direccion, telefono);
        this.area = area;
    }

    public Enfermera(int id, String nombre, String apellido, String direccion, String telefono, String area) {
        super(nombre, apellido, direccion, telefono);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getIdPersona() {
        return getId();
    }

    @Override
    public String toString() {
        return "Enfermera{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", direccion='" + getDireccion() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}

