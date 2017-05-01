/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author mitch
 */
public class RightArrow extends Arrow{
    
    public RightArrow(Image arrowImg, EventHandler<MouseEvent> mouseClicked) {
        super(arrowImg, mouseClicked);
    }
    
}
