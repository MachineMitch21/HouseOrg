/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;
import java.sql.*;
/**
 *
 * @author mitch
 */
public class OutputManager {
    private String errorMessage;
    private String goodOutput;
    private ResultSet rs;
    
    public void setErrorMsg(String error){
        errorMessage = error;
    }
    
    public String getErrorMsg(){
        return errorMessage;
    }
}
