import java.time.LocalDate;

public class HistoriaClinica {
    private int id;
    private int idPaciente;
    private String problema;
    private String tratamiento;
    private LocalDate fechaCreacion;

    // Constructor
    public HistoriaClinica(int id, int idPaciente, String problema, String tratamiento, LocalDate fechaCreacion) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.problema = problema;
        this.tratamiento = tratamiento;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

