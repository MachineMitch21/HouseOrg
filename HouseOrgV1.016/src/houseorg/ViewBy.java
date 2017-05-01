/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

/**
 *
 * @author mitch
 */
public class ViewBy {
    public enum ViewSelection {All, Date, Tenant, UpkeepCost};
    private static ViewSelection selection = ViewSelection.All;
    
    private static String dateToViewBy;
    private static String tenantToViewBy;
    private static double costToViewBy;
    private static double endCostToViewBy;
    
    public void setSelection(ViewSelection selection){
        this.selection = selection;
    }
    
    public ViewSelection getSelection(){
        return selection;
    }
    
    public String getStringSelection(){
        String selectionString = null;
        switch(getSelection()){
            case Date:
                selectionString = "DateOfEntry";
                break;
            case Tenant:
                selectionString = "Tenant";
                break;
            case UpkeepCost:
                selectionString = "upKeepCost";
                break;
        }
        return selectionString;
    }
    
    public void setDateSelection(String date){
        this.dateToViewBy = date;
    }
    
    public void setTenantSelection(String tenant){
        this.tenantToViewBy = tenant;
    }
    
    public void setCostSelection(double cost){
        this.costToViewBy = cost;
    }
    
    public void setEndCostSelection(double cost){
        this.endCostToViewBy = cost;
    }
    
    public String getDateSelection(){
        return dateToViewBy;
    }
    
    public String getTenantSelection(){
        return tenantToViewBy;
    }
    
    public double getCostSelection(){
        return costToViewBy;
    }
    
    public double getEndCostSelection(){
        return endCostToViewBy;
    }
    
    public String getCurrentlySelected(){
        String s = "";
        
        switch(selection){
            case Date:
                s = dateToViewBy;
                break;
            case Tenant:
                s = tenantToViewBy;
                break;
            case UpkeepCost:
               s = Double.toString(costToViewBy);
               break;
        }
        return s;
    }
}
