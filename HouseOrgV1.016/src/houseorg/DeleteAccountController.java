
package houseorg;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class DeleteAccountController implements Initializable {

    @FXML
    TextField usernameText, passwordText;
    @FXML
    Label msgLabel;
   
    
    @FXML
    public void cancelAction(){
        Stage myStage = (Stage)msgLabel.getScene().getWindow();
        WindowManager winMan = new WindowManager();
        winMan.closeStage(myStage);
        winMan.openWindow("MainApplicationWindow.fxml");
    }
    
    @FXML
    public void deleteAccount(){
        DatabaseManager dm = new DatabaseManager();
        if(usernameText.getText().equals("")||passwordText.getText().equals("")){
            msgLabel.setText("Fields must not be blank..");
        }else{
            ResultSet rs1 = dm.selectFromWhere("House.db", "users", "username", usernameText.getText());
            boolean passwordCorrect = false;
            boolean usernameFound = false;
            List<String> addressList = new ArrayList();
        try{
            while(rs1.next()){
                if(rs1.getString("username").equals(usernameText.getText())){
                    usernameFound = true;
                    if(rs1.getString("Password").equals(passwordText.getText())){
                        passwordCorrect = true;
                    }else{
                        passwordCorrect = false;
                    }
                }else{
                    usernameFound = false;
                    msgLabel.setText("The username you entered was not found..");
                }
            }
            rs1.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        if(passwordCorrect && usernameFound){
            ResultSet rs2 = dm.selectFromWhere("House.db", "houses", "user", usernameText.getText());
            
            try{
                while(rs2.next()){
                    addressList.add(rs2.getString("Address"));
                }
                
                rs2.close();
                dm.deleteRecord("users", "username", usernameText.getText());
                dm.deleteRecord("houses", "user", usernameText.getText());
                for(int i = 0; i < addressList.size(); i++){
                    dm.deleteTable("House.db", addressList.get(i));
                }
                 System.out.println("End of try two");
            }catch(SQLException e){
                e.printStackTrace();
            }
            Stage myStage = (Stage)msgLabel.getScene().getWindow();
            WindowManager winMan = new WindowManager();
            winMan.closeStage(myStage);
            winMan.openWindow("FXMLDocument.fxml");
        }else if(usernameFound && !passwordCorrect){
            msgLabel.setText("Password is incorrect");
        }
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
