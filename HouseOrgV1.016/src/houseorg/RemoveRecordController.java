
package houseorg;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class RemoveRecordController implements Initializable {

    @FXML
    TextField removeText;
    
    @FXML
    Label heading;
    
    @FXML 
    Label removeLabel;
    
    @FXML
    TextField addressText;
    
    @FXML
    Button deleteAllBtn;
    
    @FXML
    Label msgLabel;
    
    private boolean byDate = true;
    
    @FXML
    public void SubmitRemove(){
        DatabaseManager dm = new DatabaseManager();
        DataParse dp = new DataParse();
        String address = dp.getTableNameFormat(addressText.getText());
        ResultSet rs = dm.selectFromWhere("House.db", "houses", "user", DatabaseManager.currentUser);
        boolean houseFound = false;
        
        try{
            while(rs.next()){
                if(rs.getString("Address").equals(address)){
                    houseFound = true;
                    break;
                }else{
                    houseFound = false;
                }
            }
        }catch(SQLException e){
            e.getMessage();
        }
        
        if(houseFound){
            if(byDate){
                dm.deleteRecord(address, "DateOfEntry", removeText.getText());
            }else{
                dm.deleteRecord(address, "Tenant", removeText.getText());
            }
            
            Stage myStage = (Stage)deleteAllBtn.getScene().getWindow();
            WindowManager winMan = new WindowManager();
            winMan.closeStage(myStage);
            winMan.openWindow("MainApplicationWindow.fxml");
        }else{
            msgLabel.setText("House not found..");
        }
    }
    
    @FXML
    public void removeByDate(){
        heading.setText("Remove By Date");
        removeLabel.setText("Date: ");
        byDate = true;
        deleteAllBtn.setText("Delete all Records related to Date");
    }
    
    @FXML
    public void removeByTenant(){
        heading.setText("Remove By Tenant");
        removeLabel.setText("Tenant: ");
        byDate = false;
        deleteAllBtn.setText("Delete all Records related to Tenant");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
