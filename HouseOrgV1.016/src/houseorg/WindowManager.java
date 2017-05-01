
package houseorg;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author mitch
 */
public class WindowManager {
    
    private static List<String> Documents = new ArrayList();
    private static List<Stage> stages = new ArrayList();
    private static List<Entry> entries = new ArrayList();
    private static TreeItem addressTree = new TreeItem();
    private static TreeView addressContainer = new TreeView();
    private static List<Image> imageControllerImgs = new ArrayList();
    DatabaseManager dm = new DatabaseManager();
    private static int totalImagesSinceDatabaseInit;
    
    public void setTotalImagesAddedSinceDatabaseInit(int total){
        totalImagesSinceDatabaseInit = total;
    }
    
    public int getTotalImagesAddedSinceDatabaseInit(){
        return totalImagesSinceDatabaseInit;
    }
    
    public void openWindow(String window){
        InputManager.windowToOpen = window;
        HouseOrg h = new HouseOrg();
        Stage stage = new Stage();
        try{
            h.start(stage);
        }catch(Exception e){
            System.out.println("Unable to open page..");
            e.printStackTrace();
        }
    }
    
    //Close the stage passed in the params and remove correct elements from DocumentsList and stagesList
    public void closeStage(Stage stage){
        for(int i = 0; i < Documents.size(); i++){
            if(stage.equals(stages.get(i))){
                stages.get(i).close();
                stages.remove(i);
                Documents.remove(i);
            }
        }
    }
    
    public void setAddressContainer(TreeView addressContainer){
        this.addressContainer = addressContainer;
    }
    
    public TreeView getAddressContainer(){
        return addressContainer;
    }
    
    public void setAddressTree(TreeItem addressTree){
        this.addressTree = addressTree;
    }
    
    public TreeItem getAddressTree(){
        return addressTree;
    }
    
    public void addDocument(String fxmlDoc){
        Documents.add(fxmlDoc);
    }
    
    public void removeDocument(String fxmlDoc){
        Documents.remove(fxmlDoc);
    }
    
    public void addStage(Stage stage){
        stages.add(stage);
    }
    
    public void removeStage(Stage stage){
        stages.remove(stage);
    }
    
    public void setEntryList(List<Entry> entries){
        this.entries = entries;
    }
    
    public List<Entry> getEntryList(){
        return entries;
    }
    
    public List<String> getOpenWindows(){
        return Documents;
    }
    
    public List<Stage> getStages(){
        return stages;
    }
    
    public void addImage(Image image){
        imageControllerImgs.add(image);
    }
    
    public List<Image> getImages(){
        return imageControllerImgs;
    }
    
}
