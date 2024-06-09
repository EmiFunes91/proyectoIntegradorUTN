public class Sector {

   private String identificador;
   private int cantCamas;
   private boolean reservable;
   
   public Sector (String identificador, int CantCamas, boolean reservable) {
    
    this.identificador = identificador;
    this.cantCamas = cantCamas;
    this.reservable = reservable;
   }
    
   public String getIdentificador () {
    return identificador;
    
   }
   
   public void setIdentificador () { 
    this.identificador = identificador;
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

