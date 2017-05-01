
package houseorg;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mitch
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passText;
    @FXML
    private Label msgLabel;
    
    @FXML
    public void onCreateAcc(){
        Stage myStage = (Stage)msgLabel.getScene().getWindow();
        
        WindowManager winMan = new WindowManager();
        winMan.openWindow("CreateAccount.fxml");
        winMan.closeStage(myStage);
    }
    
    @FXML
    public void SubmitSignIn(){
        DatabaseManager dbm = new DatabaseManager();
        msgLabel.setText("");
        if(usernameText.getText().equals("") && passText.getText().equals("")){
            msgLabel.setText("Fields must not be blank");
        }else{
            System.out.println("Submit hit");
            
            msgLabel.setText("");
            ResultSet rs = dbm.selectAllFrom("House.db", "Users");
          
           boolean usernameCorrect = false;
           boolean passwordCorrect = false;
            try{
                while(rs.next()){
                    if(rs.getString("Username").equals(usernameText.getText())){
                        usernameCorrect = true;
                    }
                    if(rs.getString("Password").equals(passText.getText())){
                        passwordCorrect = true;
                    }
                }
                rs.close();
                
                if(usernameCorrect && passwordCorrect){
                    msgLabel.setText("Login successful");
                    DatabaseManager.currentUser = usernameText.getText();
                    
                    Stage myStage = (Stage)msgLabel.getScene().getWindow();
                    WindowManager winMan = new WindowManager();
                    winMan.closeStage(myStage);
                    winMan.openWindow("MainApplicationWindow.fxml");
                }else{
                    msgLabel.setText("invalid username or password");
                }
                
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    
    
}
