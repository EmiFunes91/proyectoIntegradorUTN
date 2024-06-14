import java.sql.Date;

public class HistoriaClinica {
    private int id;
    private int idPaciente;
    private String problema;
    private Date fechaCreacion;

    public HistoriaClinica(int idPaciente, String problema, Date fechaCreacion) {
        this.idPaciente = idPaciente;
        this.problema = problema;
        this.fechaCreacion = fechaCreacion;
    }

    public HistoriaClinica(int id, int idPaciente, String problema, Date fechaCreacion) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.problema = problema;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getProblema() {
        return problema;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public String toString() {
        return "HistoriaClinica{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", problema='" + problema + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}


