/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author mitch
 */
public class HouseImage extends ImageView{
    private Image houseImg;
    private Label actionMsg = new Label();
    
    public HouseImage(){
        this.setCursor(Cursor.HAND);
        actionMsg.setVisible(false);
        actionMsg.setText("Click to view house images");
        actionMsg.setStyle("-fx-background-color: black;"
                         + "-fx-text-fill: white;"
                         + "-fx-font-size: 13px;"
                         + "-fx-border-style: dotted;"
                         + "-fx-border-width: 1px;"
                         + "-fx-border-color: white;"
                         + "-fx-border-radius: 1px;");
        actionMsg.setMinSize(25, 20);
        addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                setStyle("-fx-opacity: .6;");
                actionMsg.setVisible(true);
                e.consume();
                addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        setStyle("-fx-opacity: 1;");
                        actionMsg.setVisible(false);
                        e.consume();
                    }
                });
            }
        });

    }
    
    public void addOpenImageViewerEvent(){
                this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent e){
               WindowManager winMan = new WindowManager();
               winMan.openWindow("ImageViewer.fxml");
               e.consume();
           }
        });
    }
    
    public void setHouseImage(Image image){
        houseImg = image;
        setImage(houseImg);
    }
    
    public Image getHouseImage(){
        return houseImg;
    }
    
    public void clearHouseImg(){
        houseImg = null;
    }
    
    public void setActionText(String text){
        actionMsg.setText(text);
    }
    
    public Label getActionLabel(){
        return actionMsg;
    }
}
