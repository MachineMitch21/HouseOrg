/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package houseorg;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class ImageViewerController implements Initializable {
    
    @FXML
    FlowPane imagesHolder;
    
    @FXML
    ImageView mainImageHolder;
    
    @FXML
    Menu housesMenu;
    
    @FXML
    Label imageLabel;
    
    @FXML
    GridPane centerGrid;
    
    @FXML
    ImageView rightArrow;
    
    @FXML
    ImageView leftArrow;
    
    @FXML
    GridPane rightGrid;
    
    @FXML
    GridPane leftGrid;
    List<MenuItem> menuItems = new ArrayList();
    
    @FXML
    public void changeStyle(){
        
    }
    
    @FXML
    public void changeImage(){
        
    }
    
    private void initMenus(){
        housesMenu.getItems().clear();
        DatabaseManager dm = new DatabaseManager();
        DataParse dp = new DataParse();
        ResultSet rs = dm.selectFromWhere("House.db", "houses", "user", DatabaseManager.currentUser);
        
        try{
            while(rs.next()){
                String address = dp.getReadableFormat(rs.getString("Address"));
                MenuItem house = new MenuItem();
                house.setText(address);
                house = setItemHandler(house);
                menuItems.add(house);
                housesMenu.getItems().add(house);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private MenuItem setItemHandler(final MenuItem mi){
        final WindowManager winMan = new WindowManager();
        
        mi.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                winMan.getImages().clear();
                imagesHolder.getChildren().clear();
                DatabaseManager dm = new DatabaseManager();
                DataParse dp = new DataParse();
                WindowManager winMan = new WindowManager();
                ResultSet rs = dm.selectFromWhere("House.db", "images", "Address", dp.getTableNameFormat(mi.getText()));
                try{
                   while(rs.next()){
                       File imageFile = dm.getImageFile(rs.getString("imageName"));
                       Image image = new Image(imageFile.toURI().toString());
                       if(rs.getString("importance").equals(Integer.toString(1))){
                           
                            mainImageHolder.setImage(image);
                            
                       }
                       final HouseImage imageView = new HouseImage();
                       imageView.setTranslateX(10);
                       imageView.setFitHeight(90);
                       imageView.setFitWidth(90);
                       imageView.setActionText("View image");
                       winMan.addImage(image);
                       imageView.setImage(image);
                       imagesHolder.getChildren().add(imageView);
                       imagesHolder.getChildren().add(imageView.getActionLabel());
                       imageView.getActionLabel().setLayoutX(imageView.getX());
                       imageView.getActionLabel().setLayoutX(imageView.getY());
                       
                       imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                           @Override
                           public void handle(MouseEvent e){
                               mainImageHolder.setImage(imageView.getImage());
                           }
                       });
                   }
                   initArrows();
                   rs.close();
                }catch(SQLException sqlE){
                    sqlE.printStackTrace();
                }
            }
        });
        
        return mi;
    }
    
    private void initImages(){
        File imageFile = new File("house.jpeg");
        Image mainImage = new Image(imageFile.toURI().toString());
        mainImageHolder.setImage(mainImage);
        mainImageHolder.setFitHeight(500);
        mainImageHolder.setFitWidth(500);
    }
    
    private void initArrows(){
        final WindowManager winMan = new WindowManager();
        
        System.out.println("There are " + winMan.getImages().size() + " images in winMan images list.");
        File leftImageFile = new File("Images/ImageController/LeftArrow.png");
        File rightImageFile = new File("Images/ImageController/RightArrow.png");
        Image leftArrowImg = new Image(leftImageFile.toURI().toString());
        Image rightArrowImg = new Image(rightImageFile.toURI().toString());
        
        
        LeftArrow leftArrowObj = new LeftArrow(leftArrowImg, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                System.out.println("Clicked Left Arrow");
                for(int i = 0; i < winMan.getImages().size(); i++){
                    if(winMan.getImages().get(i) == mainImageHolder.getImage()){
                        System.out.println("Equality of images");
                        if(i - 1 >= 0){
                            mainImageHolder.setImage(winMan.getImages().get(i - 1));
                            break;
                        }else{
                            mainImageHolder.setImage(winMan.getImages().get(winMan.getImages().size()-1));
                            break;
                        }
                        
                    }
                }
            }
        });
        
        RightArrow rightArrowObj = new RightArrow(rightArrowImg, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                System.out.println("Clicked Right Arrow");
                for(int i = 0; i < winMan.getImages().size(); i++){
                    if(winMan.getImages().get(i) == mainImageHolder.getImage()){
                        System.out.println("Equality of images");
                        if(i + 1 <= winMan.getImages().size() - 1){
                            mainImageHolder.setImage(winMan.getImages().get(i + 1));
                            break;
                        }else{
                            mainImageHolder.setImage(winMan.getImages().get(0));
                            break;
                        }
                        
                    }
                }
            }
        });
        
        leftGrid.add(leftArrowObj, 1, 1);
        rightGrid.add(rightArrowObj, 1, 1);
    }
    
    private void initStyles(){
        mainImageHolder.setEffect(new DropShadow(20, Color.GREY));
        imagesHolder.setStyle("-fx-background-color: grey;"
                            + "-fx-border-radius: 5px;"
                            + "-fx-border-style: solid;"
                            + "-fx-border-width: 1px;"
                            + "-fx-border-color: black;");
        imagesHolder.setHgap(20);
        imagesHolder.setVgap(20);
        imagesHolder.setMinWidth(200);
        imagesHolder.setPrefHeight(200);
        imagesHolder.setMaxWidth(200);
        imageLabel.setStyle("-fx-font-size: 30px;"
                          + "-fx-text-fill: white;"
                          + "-fx-background-color: grey;"
                          + "-fx-border-width: 1px;"
                          + "-fx-border-color: black;"
                          + "-fx-border-style: solid;"
                          + "-fx-border-radius: 5px;"
                          + "-fx-padding: 15px;"
                          + "-fx-opacity: .9;");
        centerGrid.setStyle("-fx-background-color: lightgrey;"
                + "-fx-border-width: 1px;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: black;");
        rightGrid.setStyle("-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 2px;");
        
        leftGrid.setStyle("-fx-background-color: lightgrey;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 2px;");
        
        
    }
    
    private void initPage(){
        initMenus();
        initImages();
        initStyles();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPage();
    }    
    
}
