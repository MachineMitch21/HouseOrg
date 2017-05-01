
package houseorg;

import javafx.beans.property.*;
/**
 *
 * @author mitch
 */
public class Entry {
 
    private final SimpleStringProperty date = new SimpleStringProperty("");
    private final SimpleStringProperty tenant = new SimpleStringProperty("");
    private final SimpleDoubleProperty rent = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty rentPaid = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty costPerMonth = new SimpleDoubleProperty(0);
    private final SimpleIntegerProperty entryID = new SimpleIntegerProperty(0);
    
    public Entry(){
        this(0, "", "", 0, 0, 0);
    }
    
   public Entry(int id, String date, String tenant, double rent, double rentPaid, double cost){
               setEntryID(id);
               setDate(date);
               setTenant(tenant);
               setRent(rent);
               setRentPaid(rentPaid);
               setCostPerMonth(cost);
   }
   
   public void setEntryID(int id){
       entryID.set(id);
   }
   
   public void setDate(String date){
       this.date.set(date);
   }
   
   public void setTenant(String tenant){
       this.tenant.set(tenant);
   }
    
   public void setRent(double rent){
       this.rent.set(rent);
   }
     
   public void setRentPaid(double rentPaid){
       this.rentPaid.set(rentPaid);
   }
      
   public void setCostPerMonth(double cost){
       costPerMonth.set(cost);
   }
   
   public int getEntryID(){
       return entryID.get();
   }
   
   public String getDate(){
       return date.get();
   }
   
   public String getTenant(){
       return tenant.get();
   }
    
   public double getRent(){
       return rent.get();
   }
     
   public double getRentPaid(){
       return rentPaid.get();
   }
      
   public double getCostPerMonth(){
       return costPerMonth.get();
   }
}
