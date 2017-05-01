package houseorg;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class CreateAccountController implements Initializable {
    
    
    
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passText;
    @FXML
    private PasswordField verPassText;
    @FXML
    private Label msgLabel;
    
    @FXML
    private void SubmitCreation(ActionEvent event){
        if(usernameText.getText().equals("") || passText.getText().equals("") || verPassText.getText().equals("")){
            msgLabel.setText("Fields must not be blank..");
        }else{
            InputManager im = new InputManager();
            DatabaseManager dbm = new DatabaseManager();
            ResultSet rs;
        
        if(!im.passEqu(passText.getText(), verPassText.getText())){
            msgLabel.setText("Passwords need to match");
        }else{
            rs = dbm.selectAllFrom("House.db", "Users");
            try{
                boolean isTaken = false;
                    while(rs.next()){
                        //System.out.println("In while");
                        if(rs.getString("Username").equals(usernameText.getText())){
                            msgLabel.setText("Username already taken");
                            isTaken = true;
                            break;
                        }else{
                            msgLabel.setText("");
                        }
                    }
                    rs.close();
                    if(!isTaken){
                        dbm.insertInto("House.db", "Users", "(Username, Password)", "(" + "'" + usernameText.getText() + "'" + ", " + "'" + passText.getText() + "'" + ")");
                        Stage myStage = (Stage)msgLabel.getScene().getWindow();
                        WindowManager winMan = new WindowManager();
                        winMan.closeStage(myStage);
                        winMan.openWindow("HelpPage.fxml");
                    }    
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
