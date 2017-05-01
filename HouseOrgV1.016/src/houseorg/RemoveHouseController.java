
package houseorg;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class RemoveHouseController implements Initializable {

    @FXML
    TextField addressText;
    
    @FXML
    Label msgLabel;
    
    @FXML
    public void SubmitRemove(){
        DatabaseManager dm = new DatabaseManager();
        DataParse dp = new DataParse();
        boolean foundHouse = false;
        String parsedAddress = dp.getTableNameFormat(addressText.getText());
        ResultSet rs = dm.selectFromWhere("House.db", "houses", "user", dm.currentUser);
        try{
            while(rs.next()){
                if(parsedAddress.equals(rs.getString("Address"))){
                    foundHouse = true;
                    break;
                }else{
                    foundHouse = false;
                }
            }
            rs.close();
        }catch(SQLException e){
            System.out.println("Trouble finding House table");
            e.printStackTrace();
        }
        if(foundHouse == true){
            dm.deleteTable("House.db", parsedAddress);
            dm.deleteRecord("houses", "Address", parsedAddress);
            dm.deleteRecord("Images", "Address", parsedAddress);
            msgLabel.setText("");
            Stage myStage = (Stage)msgLabel.getScene().getWindow();
            EventFlagger ef = new EventFlagger();
            ef.setRemovedHouse(true);
            WindowManager winMan = new WindowManager();
            winMan.closeStage(myStage);
            
            /*Checks to see if any of the children in addPar from main window 
                have an address equal to the address entered by the user*/
            for(int i = 0; i < winMan.getAddressTree().getChildren().size(); i++){
                if(addressText.getText().equals(dp.getAddressFromTreeItem(winMan.getAddressTree().getChildren().get(i)))){
                    winMan.getAddressTree().getChildren().remove(i);
                }
            }
            
            
        }else{
            msgLabel.setText("House not found");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
