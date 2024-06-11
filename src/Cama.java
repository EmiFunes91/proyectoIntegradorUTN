public class Cama {

    private int sectorID;
    private boolean reservable;

    // Constructor
    Cama (int sectorID, boolean reservable) {

        this.sectorID = sectorID;
        this.reservable = reservable;
    }

    // Getters y setters
    public int getSectorID () {
        return this.sectorID;
    }

    public void setSectorID (int sectorID) {
        this.sectorID = sectorID;
    }

    public boolean getReservable () {
        return this.reservable;
    }

    public void setReservable (boolean reservable) {
        this.reservable = reservable;
    }


}