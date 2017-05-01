/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import javafx.scene.paint.Color;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author mitch
 */
public abstract class Arrow extends ImageView{
    
    private Image arrow;
    private EventHandler actionHandler;
    
    public Arrow(Image arrowImg, EventHandler<MouseEvent> mouseClickedHandler){
        arrow = arrowImg;
        initArrowHandlers();
        setImage(arrow);
        setFitHeight(200);
        setFitWidth(200);
        setMouseClickedHandler(mouseClickedHandler);
    }
    
    private void initArrowHandlers(){
        addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent enter){
                setStyle("-fx-opacity: .5;");
                setEffect(new DropShadow(30, Color.BLACK));
                setCursor(Cursor.HAND);
                addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent exit){
                        setStyle("-fx-opacity: 1;");
                        setEffect(null);
                    }
                });
            }
        });
    }
    
    private void setMouseClickedHandler(EventHandler<MouseEvent> actionHandler){
        this.setOnMouseClicked(actionHandler);
    }
}
