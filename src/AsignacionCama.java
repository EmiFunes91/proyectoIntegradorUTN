import java.time.LocalDateTime;

public class AsignacionCama {
    private int id;
    private int idEnfermera;
    private int idCama;
    private LocalDateTime fecha;
    private String turno;

    public AsignacionCama(int id, int idEnfermera, int idCama, LocalDateTime fecha, String turno) {
        this.id = id;
        this.idEnfermera = idEnfermera;
        this.idCama = idCama;
        this.fecha = fecha;
        this.turno = turno;
    }

    public AsignacionCama(int idEnfermera, int idCama, LocalDateTime fecha, String turno) {
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}


