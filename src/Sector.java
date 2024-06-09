public class Sector {

  private String nombreSector;
  private int cantCamas;
  private boolean reservable;

  public Sector(String nombreSector, int cantCamas, boolean reservable) {
    this.nombreSector = nombreSector;
    this.cantCamas = cantCamas;
    this.reservable = reservable;
  }

  public String getNombreSector() {
    return nombreSector;
  }

  public void setNombreSector(String nombreSector) {
    this.nombreSector = nombreSector;
  }

  public int getCantCamas() {
    return cantCamas;
  }

  public void setCantCamas(int cantCamas) {
    this.cantCamas = cantCamas;
  }

  public boolean isReservable() {
    return reservable;
  }

  public void setReservable(boolean reservable) {
    this.reservable = reservable;
  }

}
