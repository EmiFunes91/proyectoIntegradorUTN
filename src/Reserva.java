
public class Reserva {

    private int camaID;
    private int pacienteID;
    private String fechaYhora;

    // Constructor
    Reserva (int camaID, int pacienteID, String fh) {

        this.camaID = camaID;
        this.pacienteID = pacienteID;
        this.fechaYhora = fh;

    } 

    // Getters y setters

    int getCamaID () {
        return this.camaID;        
    }

    void setCamaID (int camaID) {
        this.camaID = camaID;
    }

    int getPacienteID () {
        return this.pacienteID;
    }

    void setPacienteID (int pacienteID) {
        this.pacienteID = pacienteID;
    }

    String getFechaYHora () {
        return this.fechaYhora;
    }

    void setFechaYHora (String fechaYhora) {
        this.fechaYhora = fechaYhora;
    }

}