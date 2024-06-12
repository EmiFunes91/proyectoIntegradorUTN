public class Reserva {
    private int camaID;
    private int pacienteID;

    public Reserva(int camaID, int pacienteID) {
        this.camaID = camaID;
        this.pacienteID = pacienteID;
    }

    public int getCamaID() {
        return camaID;
    }

    public int getPacienteID() {
        return pacienteID;
    }

    public void setCamaID(int camaID) {
        this.camaID = camaID;
    }

    public void setPacienteID(int pacienteID) {
        this.pacienteID = pacienteID;
    }
}
