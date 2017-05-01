/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class HelpPageController implements Initializable {

    @FXML
    TextArea helpInfo;
    
    public void initializeHelpInfo(){
        Path help = Paths.get("Help.txt");
        try (InputStream in = Files.newInputStream(help);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                helpInfo.appendText(line + "\n");
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        helpInfo.editableProperty().set(false);
        helpInfo.setPrefHeight(750);
        helpInfo.setPrefWidth(1000);
        helpInfo.scrollTopProperty().set(0);
        initializeHelpInfo();
        helpInfo.setCursor(Cursor.HAND);
        
    }    
    
}
