import java.time.LocalDate;

public class AsignacionCama {
    private int id;
    private int idEnfermera;
    private int idCama;
    private LocalDate fecha;
    private String turno;

    public AsignacionCama(int id, int idEnfermera, int idCama, LocalDate fecha, String turno) {
        this.id = id;
        this.idEnfermera = idEnfermera;
        this.idCama = idCama;
        this.fecha = fecha;
        this.turno = turno;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEnfermera() {
        return idEnfermera;
    }

    public void setIdEnfermera(int idEnfermera) {
        this.idEnfermera = idEnfermera;
    }

    public int getIdCama() {
        return idCama;
    }

    public void setIdCama(int idCama) {
        this.idCama = idCama;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}