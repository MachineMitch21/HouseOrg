
package houseorg;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class AddEntryController implements Initializable {

    @FXML
    TextField addressText;
    @FXML
    TextField tenantText;
    @FXML
    TextField rentText;
    @FXML
    TextField rentPaidText;
    @FXML
    TextField costPerMonthText;
    @FXML
    TextField dateText;
    @FXML
    TextArea reportText;
    @FXML
    Label msgLabel;
   
    //Checks if any of the input fields are empty returning true if yes and false if no
    private boolean fieldsEmpty(){
        if(addressText.getText().equals("") || dateText.getText().equals("") || tenantText.getText().equals("") || rentPaidText.getText().equals("") ||
                costPerMonthText.getText().equals("") || rentText.getText().equals("")){
            return true;
        }else{
            return false;
        }
    }
    
    @FXML
    public void SubmitAdd(){
        boolean fieldsAreEmpty = fieldsEmpty();
        DatabaseManager dm = new DatabaseManager();
         DataParse dp = new DataParse();
         String address = "";
         if(fieldsAreEmpty == false){
            address = addressText.getText();
            address = dp.getTableNameFormat(address);
         }
        
        ResultSet rs;
        boolean houseFound = false;
        
        if(fieldsAreEmpty == false){
            rs = dm.selectFromWhere("House.db", "houses", "user", DatabaseManager.currentUser);
            try{
                while(rs.next()){
                    if(rs.getString("Address").equals(address)){
                        houseFound = true;
                        break;
                    }else{
                        houseFound = false;
                    }
                    
                }
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        if(houseFound == true && fieldsAreEmpty == false){
            dm.insertInto("Houses.db", address, "(DateOfEntry, Tenant, Rent, RentPaid, upKeepCost, ExpenseReport)", " (" 
                    + "'" + dateText.getText() + "'" + ", "
                    + "'" + tenantText.getText() + "'" + ", " 
                    + rentText.getText() + ", "
                    + rentPaidText.getText() + ", "
                    + costPerMonthText.getText() + ", "     
                    + "'" + reportText.getText() + "'"
                    + ")");
        
        Stage myStage = (Stage)dateText.getScene().getWindow();
        EventFlagger ef = new EventFlagger();
        ef.setEntryAdded(true);

        WindowManager winMan = new WindowManager();
        winMan.openWindow("MainApplicationWindow.fxml");
        winMan.closeStage(myStage);
        }else if(fieldsAreEmpty == true){
            msgLabel.setText("Fields must be filled out..");
            
        }else{
            msgLabel.setText("Trouble locating house for entry..");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      reportText.setMaxWidth(200);
      reportText.setPrefWidth(200);
      reportText.setPrefHeight(50);
    }    
}
