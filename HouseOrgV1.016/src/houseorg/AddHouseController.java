/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class AddHouseController implements Initializable {

    @FXML
    TextField addressText;
    @FXML
    TextArea imageText;
    @FXML
    Label msgLabel;
    @FXML
    GridPane addHouseGrid;
    @FXML
    Button browseBtn;
    @FXML
    TextField mainImgSelection;
    
    List<File> files = new ArrayList();
    
    char badChars[] = {'.', '`', ',', '~', '\'', '*', '(', ')', '!'};
    
    
    @FXML
    public void browseFiles(){
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                                             new FileChooser.ExtensionFilter("PNG", "*.png"),
                                             new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));
        files = chooser.showOpenMultipleDialog(msgLabel.getScene().getWindow());
        
        if(files != null){
            for(int i = 0; i < files.size(); i++){
                imageText.appendText(files.get(i).getPath() + "\n");
            }
        }
    }
    
    @FXML
    public void SubmitAdd(){
        WindowManager winMan = new WindowManager();
        DatabaseManager dm = new DatabaseManager();
        DataParse dp = new DataParse();
        if(addressText.getText().equals("") || imageText.getText().equals("")){
            msgLabel.setText("Fields must be filled out..");
        }else{
            boolean containsBad = false;
            for(int i = 0; i < addressText.getText().length(); i++){
                for(int j = 0; j < badChars.length; j++){
                    if(addressText.getText().charAt(i) == badChars[j]){
                        containsBad = true;
                        msgLabel.setText("Must not contain punctuation. \n Ex: 101 west example st");
                        break;
                    }
                }
                if(containsBad){
                    break;
                }
            }
            if(!containsBad){
                ResultSet rs = dm.selectFromWhere("Houses.db", "houses", "user", DatabaseManager.currentUser);
            
            boolean exists = false;
            try{
                while(rs.next()){
                    
                    if(rs.getString("Address").equals(dp.getTableNameFormat(addressText.getText()))){
                        exists = true;
                        break;
                    }else{
                        exists = false;
                    }
                }
                rs.close();
            }catch(Exception e){
                System.out.println("Trouble fetching houses");
            }
           
            if(exists){
                msgLabel.setText("House already exists");
            }else{
                String tableName = dp.getTableNameFormat(addressText.getText());
                dm.insertInto("House.db", "houses", "(Address, user)", "(" 
                    + "'" + tableName + "'" + ", " 
                    + "'" + DatabaseManager.currentUser + "'"
                    + ")");
                
                for(int i = 0; i < files.size(); i++){
                    winMan.setTotalImagesAddedSinceDatabaseInit(winMan.getTotalImagesAddedSinceDatabaseInit() + 1);
                    
                    int importance = 0;
                    FileManip fileManip = new FileManip();
                    if(fileManip.checkEquality(mainImgSelection.getText(), files.get(i))){
                        importance = 1;
                    }else{
                        importance = 0;
                    }
                    
                    fileManip.setFile(files.get(i));
                    
                    dm.insertIntoImages("House.db", "(image, ImageName, Address, Importance)", "(" + null + ", "
                                                                                                + "'" + files.get(i).getName() + "'" + ", "
                                                                                                + "'" + tableName + "'" + ", "
                                                                                                        + Integer.toString(importance)+")", files.get(i).getName());
                }
                
                dm.createTable("House.db", tableName,
                        " (RecordID integer primary key autoincrement, "
                        + "DateOfEntry varchar(255), "
                        + "Tenant varchar(255), "
                        + "Rent int, "
                        + "RentPaid int, "
                        + "upKeepCost int, "
                        + "ExpenseReport varchar(255))");
                msgLabel.setText("House added");
                Stage myStage = (Stage)msgLabel.getScene().getWindow();
                winMan.closeStage(myStage);
                winMan.getAddressTree().getChildren().add(new TreeItem(addressText.getText()));
                EventFlagger ef = new EventFlagger();
                ef.setHouseAdded(true);
            }
            }else{
                msgLabel.setText("Address must contain only letters and spaces.");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
