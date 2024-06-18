import java.time.LocalDate;

public class AsignacionCama {
    private int id;
    private int idEnfermera;
    private int idCama;
    private LocalDate fecha;
    private String turno;

    public AsignacionCama(int idEnfermera, int idCama, LocalDate fecha, String turno) {
        this.idEnfermera = idEnfermera;
        this.idCama = idCama;
        this.fecha = fecha;
        this.turno = turno;
    }

    public AsignacionCama(int id, int idEnfermera, int idCama, LocalDate fecha, String turno) {
        this.id = id;
        this.idEnfermera = idEnfermera;
        this.idCama = idCama;
        this.fecha = fecha;
        this.turno = turno;
    }

    public int getId() {
        return id;
    }

    public int getIdEnfermera() {
        return idEnfermera;
    }

    public int getIdCama() {
        return idCama;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setIdEnfermera(int idEnfermera) {
        this.idEnfermera = idEnfermera;
    }

    public void setIdCama(int idCama) {
        this.idCama = idCama;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "AsignacionCama{" +
                "id=" + id +
                ", idEnfermera=" + idEnfermera +
                ", idCama=" + idCama +
                ", fecha=" + fecha +
                ", turno='" + turno + '\'' +
                '}';
    }
}


