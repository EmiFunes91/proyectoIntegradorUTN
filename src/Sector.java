public class Sector {

  
   private String nombreSector;
   private int cantCamas;
   private boolean reservable;
   
   public Sector (String nombreSector, int CantCamas, boolean reservable) {
    
  
    this.nombreSector = nombreSector;
    this.cantCamas = cantCamas;
    this.reservable = reservable;
   }

   public String getNombreSector() {
    return nombreSector;
   }

   public void setNombreSector() {
    this.nombreSector = nombreSector;
   }
   
   public int getCantCamas () {
    return cantCamas;
   }
   
   public void setCantCamas () {
    this.cantCamas = cantCamas;
   }
   
   public boolean getReservable () {
    return reservable; 
   }
   
   public void setReservable () {
    this.reservable = reservable;
  }

}