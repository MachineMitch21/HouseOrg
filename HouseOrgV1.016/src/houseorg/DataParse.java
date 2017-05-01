/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import javafx.scene.control.TreeItem;

/**
 *
 * @author mitch
 */
public class DataParse {
    
    public String getTableNameFormat(String addressToFormat){
        addressToFormat = addressToFormat.replace(" ", "_");
        addressToFormat = addressToFormat.replace(addressToFormat.substring(0, 1), "_" + addressToFormat.charAt(0));
        for(int i = 0; i < addressToFormat.length(); i++){
           
            if(addressToFormat.charAt(i) == '_'){
               
                if(i != 0){
                    StringBuilder sb = new StringBuilder(addressToFormat);
                    if(Character.isDigit(addressToFormat.charAt(i-1)) && Character.isDigit(addressToFormat.charAt(i+1))){
                    sb.deleteCharAt(i);
                    }
                    if(addressToFormat.charAt(i-1) == '_'){
                        sb.deleteCharAt(i-1);
                    }
                    if(addressToFormat.charAt(i+1) == '_'){
                        sb.deleteCharAt(i+1);
                    }
                    
                    addressToFormat = sb.toString();
                }
            }
        }
        return addressToFormat;
    }
    
    public String getAddressFromTreeItem(Object treeItem){
        String addressFromItem = treeItem.toString().substring(treeItem.toString().indexOf(':') +2, 
                                                                             treeItem.toString().indexOf(']') -1);
        return addressFromItem;
    }
    
    public String getReadableFormat(String addressFromDatabase){
        addressFromDatabase = addressFromDatabase.replace("_", " ");
        addressFromDatabase = addressFromDatabase.replace(addressFromDatabase.substring(0), addressFromDatabase.substring(1));
        return addressFromDatabase;
    }
}
