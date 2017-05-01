
package houseorg;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author mitch
 */
public class HouseOrg extends Application {
    static WindowManager winMan = new WindowManager();
    DatabaseManager dm = new DatabaseManager();
    FXMLLoader fxmlLoad = new FXMLLoader();
    @Override
    public void start(final Stage stage) throws Exception {
        
        if(winMan.getOpenWindows() != null && winMan.getOpenWindows().contains(InputManager.windowToOpen)){
            
            if(winMan.getOpenWindows().size()>1){
                for(int i = 0; i < winMan.getOpenWindows().size(); i++){
                    if(winMan.getOpenWindows().get(i).equals(InputManager.windowToOpen) && !InputManager.windowToOpen.equals("MainApplicationWindow.fxml")){
                        Toolkit.getDefaultToolkit().beep();
                        winMan.getStages().get(i).toFront();
                        winMan.getStages().get(i).centerOnScreen();
                    }
                }
            }
            
        }else{
        
        Parent root = FXMLLoader.load(getClass().getResource(InputManager.windowToOpen));
        try{
            fxmlLoad.load(getClass().getResource(InputManager.windowToOpen).openStream());
        }catch(IOException ioe){
            ioe.getMessage();
        }
        
        Scene scene = new Scene(root);
        switch (InputManager.windowToOpen) {
            case "FXMLDocument.fxml":
                
                DatabaseManager.con = dm.connectToDatabase("House.db");
               
                stage.setMinHeight(300);
                stage.setMinWidth(500);
                dm.createTable("House.db", "Users", "(UserID integer primary key autoincrement, "
                    + "Username varchar(255), "
                    + "Password varchar(255))");
                dm.createTable("House.db", "Houses", "(HouseID integer primary key autoincrement, "
                        + "Address varchar(255), "
                    + "user varchar(255)"
                    + ")");
                dm.createTable("House.db", "Images", "(ImageID integer primary key autoincrement, "
                             + "image blob, "
                             + "ImageName varchar(255), "
                             + "Address varchar(255), "
                             + "Importance integer"
                             + ")");
                
                int largest = 0;
                ResultSet rs = dm.selectAllFrom("Houses.db", "Images");
                
                while(rs.next()){
                    int current = rs.getInt("ImageID");
                    if(current > largest){
                        largest = current;
                    }
                }
                rs.close();
                winMan.setTotalImagesAddedSinceDatabaseInit(largest);
                break;
            case "CreateAccount.fxml":
                 stage.setMinHeight(300);
                stage.setMinWidth(500);
                break;
            case "MainApplicationWindow.fxml":
                stage.setMinHeight(600);
                stage.resizableProperty().set(false);
                break;
            case "AddHouse.fxml":
                stage.setMinHeight(500);
                stage.setMinWidth(600);
            case "AddEntry.fxml":
                stage.setTitle("ADD ENTRY HOUSE_ORG");
            case "ViewBy.fxml":
                
        }
        
        if(InputManager.windowToOpen.equals("MainApplicationWindow.fxml")){
            stage.setTitle("Thank you for choosing HOUSE_ORG, " + DatabaseManager.currentUser + "!");
        }else if(InputManager.windowToOpen.equals("HelpPage.fxml")){
             stage.setTitle("HELP");
        }else{
            stage.setTitle("HOUSE_ORG");
        }
        
        stage.setScene(scene);
        stage.show();
        winMan.addDocument(InputManager.windowToOpen);
        winMan.addStage(stage);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(final WindowEvent e){
                if(getControllerString(fxmlLoad.getController().toString()).equals("HelpPageController")){
                    if(DatabaseManager.currentUser == null){
                        winMan.openWindow("FXMLDocument.fxml");
                        winMan.removeDocument("HelpPage.fxml");
                        winMan.removeStage(stage);
                    }else if(DatabaseManager.currentUser != null){
                        winMan.removeDocument("HelpPage.fxml");
                        winMan.removeStage(stage);
                    }
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("ViewByController")){
                    ViewBy viewBy = new ViewBy();
                    viewBy.setSelection(ViewBy.ViewSelection.All);
                    winMan.removeDocument("ViewBy.fxml");
                    winMan.removeStage(stage);
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("AddHouseController")){
                    winMan.removeDocument("AddHouse.fxml");
                    winMan.removeStage(stage);
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("AddEntryController")){
                    winMan.removeDocument("AddEntry.fxml");
                    winMan.removeStage(stage);
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("CreateAccountController")){
                    winMan.removeDocument("CreateAccount.fxml");
                    winMan.removeStage(stage);
                    winMan.openWindow("FXMLDocument.fxml");
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("DeleteAccountController")){
                    winMan.removeDocument("DeleteAccount.fxml");
                    winMan.removeStage(stage);
                    winMan.openWindow("MainApplicationWindow.fxml");
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("FXMLDocumentController")){
                    winMan.removeDocument("FXMLDocument.fxml");
                    winMan.removeStage(stage);
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("MainApplicationWindowController")){
                   if(winMan.getOpenWindows().size()>1){
                       final WarningPopUp warning = new WarningPopUp();
                       warning.setWarningText("This action will close all windows and sign you out.  Continue?");
                       Button cancel = new Button("Cancel");
                       warning.getGrid().add(cancel, 2, 3);
                       warning.show(stage);
                       Toolkit.getDefaultToolkit().beep();
                       warning.getOkayBtn().setOnAction(new EventHandler<ActionEvent>(){
                           @Override
                           public void handle(ActionEvent event) {
                               DatabaseManager.currentUser = null;
                               System.out.println("mainappwin closed");
                               winMan.getOpenWindows().clear();
                               stage.close();
                               for(int i = 0; i < winMan.getStages().size(); i++){
                                   winMan.getStages().get(i).close();
                               }
                               
                               winMan.getStages().clear();
                               winMan.openWindow("FXMLDocument.fxml");
                           }
                           
                       });
                       cancel.setOnAction(new EventHandler<ActionEvent>(){

                           @Override
                           public void handle(ActionEvent event) {
                               warning.hide();
                           }
                           
                       });
                       e.consume();
                   }else{
                       winMan.removeDocument("MainApplicationWindow.fxml");
                       winMan.openWindow("FXMLDocument.fxml");
                       winMan.removeStage(stage);
                   }
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("RemoveRecordController")){
                    winMan.removeDocument("RemoveRecord.fxml");
                    winMan.removeStage(stage);
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("RemoveHouseController")){
                    winMan.removeDocument("RemoveHouse.fxml");
                    winMan.removeStage(stage);
                }else if(getControllerString(fxmlLoad.getController().toString()).equals("ImageViewerController")){
                    winMan.removeDocument("ImageViewer.fxml");
                    winMan.removeStage(stage);
                }
            }
        });
        }
    }
    
    private String getControllerString(String controller){
        controller = controller.substring(controller.indexOf('.')+1, controller.indexOf('@'));
        return controller;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
