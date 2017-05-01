/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;

/**
 *
 * @author mitch
 */
public class WarningPopUp extends Popup{
    private GridPane popupGrid = new GridPane();
    private AnchorPane anchor = new AnchorPane();
    private Button okayBtn = new Button("OK");
    private Label warningMsg = new Label();
    public WarningPopUp(){
        popupGrid.setHgap(10);
        popupGrid.setVgap(10);
        popupGrid.add(warningMsg, 1, 1);
        popupGrid.add(okayBtn, 1, 3);
        okayBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                hide();
            }
        });
        anchor.getChildren().add(popupGrid);
        anchor.setStyle("-fx-background-color: white;"
                      + "-fx-border-width: 15px;"
                      + "-fx-border-style: solid;"
                      + "-fx-border-color: lightblue;"
                      + "-fx-border-radius: 5px;");
        anchor.setMinHeight(100);
        anchor.setMinHeight(150);
        getContent().add(anchor);
    }
    
    public void setWarningText(String warning){
        warningMsg.setText(warning);
    }
    
    public String getWarningText(){
        return warningMsg.getText();
    }
    
    public GridPane getGrid(){
        return popupGrid;
    }
    
    public Button getOkayBtn(){
        return okayBtn;
    }
}
