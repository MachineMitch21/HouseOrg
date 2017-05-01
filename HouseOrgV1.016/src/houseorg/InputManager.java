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
public class InputManager {

    
    public static String windowToOpen = "FXMLDocument.fxml";              //Static String to be passed into JavaDocumentController start method 
                                                    //that will hold the string value of the fxml document to be loaded
    

    
    public boolean passEqu(String pass, String passVer){
        if(pass.equals(passVer)){
            return true;
        }else {
            return false;
        }
    }
    
}
