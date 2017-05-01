/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

/**
 *
 * @author heidi
 */
public class EventFlagger {
    private static boolean addedHouse = false;
    private static boolean addedEntry = false;
    private static boolean removedHouse = false;
    
    public void setHouseAdded(boolean addedH){
        addedHouse = addedH;
    }
    
    public void setEntryAdded(boolean addedE){
        addedEntry = addedE;
    }
    
    public void setRemovedHouse(boolean removedH){
        removedHouse = removedH;
    }
    
    public boolean getHouseAdded(){
        return addedHouse;
    }
    
    public boolean getAddedEntry(){
        return addedEntry;
    }
    
    public boolean getRemovedHouse(){
        return removedHouse;
    }
}
