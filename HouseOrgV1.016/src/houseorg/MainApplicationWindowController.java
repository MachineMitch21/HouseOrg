
package houseorg;

import java.io.File;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class MainApplicationWindowController implements Initializable {

    ViewBy viewBy = new ViewBy();
    WarningPopUp warn = new WarningPopUp();
    ExpenseReport report = new ExpenseReport();
    HouseImage houseImg = new HouseImage();
    
    @FXML
    private ObservableList<Entry> entries;
    
    public ObservableList<Entry> getEntries(){
        return entries;
    }
    
    @FXML
    private Menu removeMenu;
    
    @FXML
    private TreeView addresses;
    
    @FXML
    private TreeItem addPar;
    
    public TreeItem getAddPar(){
        return this.addPar;
    }
    
    @FXML
    private TableView houseTable;
    
    @FXML
    private Label tableLabel;
    
    @FXML
    private MenuItem houseAdd;
    @FXML
    private MenuItem entryAdd;
    @FXML
    private MenuItem houseRemove;
    @FXML
    private MenuItem recordRemove;
    @FXML
    private MenuItem viewAll;
    @FXML
    private MenuItem viewDate;
    @FXML
    private MenuItem viewTenant;
    @FXML
    private MenuItem viewCosts;
    @FXML
    private MenuItem signOut;
    @FXML
    private MenuItem deleteAccount;
    @FXML
    private GridPane centerGrid;
    
    @FXML
    public void addHouse(){
        WindowManager winMan = new WindowManager();
       addresses.getSelectionModel().select(addPar);
       updateTable();
        winMan.openWindow("AddHouse.fxml");
    }
    
    
    @FXML
    public void removeHouse(){
        WindowManager winMan = new WindowManager();
       addresses.getSelectionModel().select(addPar);
       updateTable();
        winMan.openWindow("RemoveHouse.fxml");
    }
    
    @FXML
    public void removeRecord(){
        WindowManager winMan = new WindowManager();
       addresses.getSelectionModel().select(addPar);
       updateTable();
        winMan.openWindow("RemoveRecord.fxml");
    }

    @FXML
    public void editExisting(){
        WindowManager winMan = new WindowManager();
       addresses.getSelectionModel().select(addPar);
       updateTable();
        winMan.openWindow("AddEntry.fxml");
    }
    
    public TreeView fillAddressTree(TreeView tempAddresses, TreeItem tempAddPar){
        DatabaseManager dm = new DatabaseManager();
        DataParse dp = new DataParse();
        addPar = tempAddPar;
        addresses = tempAddresses;
        if(addPar.getChildren().size() > 0){
            addPar.getChildren().clear();
        }
        ResultSet rs = dm.selectAllFrom("House.db", "Houses");
        try{
            while(rs.next()){
               if(rs.getString("user").equals(DatabaseManager.currentUser)){
                   String address = dp.getReadableFormat(rs.getString("Address"));                       

                   TreeItem<String> currentItem = new TreeItem(address);
                   
                   addPar.getChildren().add(currentItem);
               } 
            }
            if(addPar.getChildren().size()>0){
                addresses.getSelectionModel().select(addPar.getChildren().get(0));
                updateTable();
            }else{
                addresses.getSelectionModel().select(addPar);
                updateTable();
            }
            
            rs.close();
        }catch(Exception e){
            System.out.println("Trouble populating address tree");
        }
       return addresses;
    }
    
    public void refreshAddressList(){
        addresses = fillAddressTree(addresses, addPar);
    }
    
    public void fillTableForSelected(boolean addParSelected, TreeView tempAddresses, TreeItem tempAddPar, ObservableList<Entry> tempEntries){
        DatabaseManager dm = new DatabaseManager();
        DataParse dp = new DataParse();
       entries = tempEntries;
       addresses = tempAddresses;
       addPar = tempAddPar;
       
        if(addParSelected == false){
        tableLabel.setVisible(true);
        houseImg.setVisible(true);
        
        entries.clear();
        
        String address = getAddressFromTree();
        address = dp.getTableNameFormat(address);
        ResultSet rs;
        
        rs = dm.selectFromWhere("House.db", "Images", "Importance", "1");
        try{
            while(rs.next()){
                if(address.equals(rs.getString("Address"))){
                    File file = dm.getImageFile(rs.getString("imageName"));
                    Image image = new Image(file.toURI().toString());
                    changeHouseImage(image);
                }
                
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        ResultSet rs1;
        
        if(viewBy.getSelection() != ViewBy.ViewSelection.All && viewBy.getSelection() != ViewBy.ViewSelection.UpkeepCost){
            rs1 = dm.selectFromWhere("Houses.db", address, viewBy.getStringSelection(), viewBy.getCurrentlySelected());
        }else{
            rs1 = dm.selectAllFrom("Houses.db", address);
        }
        
           
        tableLabel.setText("Records For: " + dp.getReadableFormat(address));
        try{
            while(rs1.next()){
                if(viewBy.getSelection() == ViewBy.ViewSelection.UpkeepCost){
                    if((double)rs1.getInt("upkeepCost") >= viewBy.getCostSelection() && (double)rs1.getInt("upkeepCost") <= viewBy.getEndCostSelection()){
                        entries.add(new Entry(rs1.getInt("RecordID"), rs1.getString("DateOfEntry"), rs1.getString("Tenant"), rs1.getDouble("Rent"), rs1.getDouble("RentPaid"), rs1.getDouble("upkeepCost")));
                    }
                }else{
                    entries.add(new Entry(rs1.getInt("RecordID"), rs1.getString("DateOfEntry"), rs1.getString("Tenant"), rs1.getDouble("Rent"), rs1.getDouble("RentPaid"), rs1.getDouble("upkeepCost")));
                }
            }
            rs1.close();
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        }else{
            tableLabel.setVisible(false);
            houseImg.setVisible(false);
        }
    }
    
    private void changeHouseImage(Image image){
        
            houseImg.setFitHeight(100);
            houseImg.setFitWidth(100);
            houseImg.setHouseImage(image);
        
    }
    
    @FXML
    public void displayContextMenu(final MouseEvent e){
        final DatabaseManager dm = new DatabaseManager();
        final DataParse dp = new DataParse();
        if(entries.size()>0){
               final String address = getAddressFromTree();
        if(e.getButton() == MouseButton.SECONDARY){
            final Entry currentEntry = entries.get(houseTable.getSelectionModel().getSelectedIndex());
            final double x = e.getScreenX();
            final double y = e.getScreenY();
            final ContextMenu deleteRecordMenu = new ContextMenu();
            deleteRecordMenu.setX(x);
            deleteRecordMenu.setY(y);
            deleteRecordMenu.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent mEv){
                    deleteRecordMenu.hide();
                    mEv.consume();
                }
            });
            MenuItem deleteRecord = new MenuItem("Delete Record " + currentEntry.getEntryID());
            MenuItem expenseReport = new MenuItem("View Report for record " + currentEntry.getEntryID());
            deleteRecordMenu.show((Stage)houseTable.getScene().getWindow());
            deleteRecord.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent ev){
                    
                    dm.deleteRecord(dp.getTableNameFormat(address), "RecordID", Integer.toString(currentEntry.getEntryID()));
                    updateTable();
                    ev.consume();
                }
            });
            expenseReport.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent ev){
                    ResultSet rs = dm.selectFromWhere("House.db", dp.getTableNameFormat(address), "RecordID", Integer.toString(currentEntry.getEntryID()));
                    try{
                        while(rs.next()){
                            report.setReportText(rs.getString("ExpenseReport"));
                        }
                        rs.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    report.setX(e.getScreenX());
                    report.setY(e.getScreenY());
                    report.setReportSize(150, 150);
                    report.show(tableLabel.getScene().getWindow());
                    ev.consume();
                }
            });
            deleteRecordMenu.getItems().addAll(deleteRecord, expenseReport);
        }
        }
    }
     
    @FXML
    private void houseManip(MouseEvent e){
            updateTable();
    }
    
    private void updateTable(){
        if(addPar.getChildren().size() == 0 || addresses.getSelectionModel().getSelectedItem().equals(addPar) || addresses.getSelectionModel().getSelectedItem() == null){
            entries.clear();
            tableLabel.setText("");
            fillTableForSelected(true, addresses, addPar, entries);
            if(removeMenu.getItems().size()>=3){
                removeMenu.getItems().remove(2);
            }
        }else if(addPar.getChildren().size() > 0){
            if(removeMenu.getItems().size()>=3){
                removeMenu.getItems().remove(2);
            }
            fillTableForSelected(false, addresses, addPar, entries);
            Menu removeCurrent = new Menu();
            removeCurrent.setText("Remove current house");
            MenuItem removeCurrentHouse = new MenuItem();
            final String addressFromTree = getAddressFromTree();
            removeCurrentHouse.setText("Delete " + addressFromTree + " (Action is final)");
            removeCurrentHouse.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent e){
                    DatabaseManager dm = new DatabaseManager();
                    DataParse dp = new DataParse();
                    ResultSet rs = dm.selectFromWhere("House.db", "Images", "Address", dp.getTableNameFormat(addressFromTree));
                    
                    List<String> addresses = new ArrayList();
                    try{
                        while(rs.next()){
                            addresses.add(rs.getString("Address"));
                        }
                        rs.close();
                    }catch(SQLException ex){
                        ex.printStackTrace();
                    }
                    dm.deleteTable("House.db", dp.getTableNameFormat(addressFromTree));
                    dm.deleteRecord("houses", "Address", dp.getTableNameFormat(addressFromTree));
                    for(int i = 0; i < addresses.size(); i++){
                        dm.deleteRecord("images", "Address", addresses.get(i));
                    }
                    refreshAddressList();
                    e.consume();
                }
            });
            removeCurrent.getItems().add(removeCurrentHouse);
            removeMenu.getItems().add(removeCurrent);
        }
        
    }
    
    @FXML
    public void userSignOut(){
        WindowManager winMan = new WindowManager();
        Stage myStage = (Stage) tableLabel.getScene().getWindow();
        if(winMan.getOpenWindows().size()>1){
            warn.setWarningText("Exit all other windows before exiting or signing out.");
            warn.setX(tableLabel.getScene().getWindow().getWidth()/2);
            warn.setY(tableLabel.getScene().getWindow().getHeight()/2);
            warn.show(tableLabel.getScene().getWindow());
        }else{
        try{
            DatabaseManager.con.close();
            System.out.println("Closed connection..");
        }catch(SQLException e){
            e.getMessage();
        }
        winMan.openWindow("FXMLDocument.fxml");
        winMan.closeStage(myStage);
        DatabaseManager.currentUser = null;
        }
        
    }
    
    @FXML
    public void getHelp(){
        WindowManager winMan = new WindowManager();
        winMan.openWindow("HelpPage.fxml");
    }
    
    @FXML
    public void deleteAccount(){
        WindowManager winMan = new WindowManager();
        Stage myStage = (Stage) tableLabel.getScene().getWindow();
        if(winMan.getOpenWindows().size()>1){
            warn.setWarningText("Exit all other windows before moving forward.");
            warn.setX(tableLabel.getScene().getWindow().getWidth()/2);
            warn.setY(tableLabel.getScene().getWindow().getHeight()/2);
            warn.show(tableLabel.getScene().getWindow());
        }else{
            winMan.openWindow("DeleteAccount.fxml");
            winMan.closeStage(myStage);
        }
    }
    
    @FXML
    public void allSelection(){
        viewBy.setSelection(ViewBy.ViewSelection.All);
    }
    
    @FXML
    public void dateSelection(){
        WindowManager winMan = new WindowManager();
        viewBy.setSelection(ViewBy.ViewSelection.Date);
        winMan.openWindow("ViewBy.fxml");
    }
    
    @FXML
    public void tenantSelection(){
        WindowManager winMan = new WindowManager();
        viewBy.setSelection(ViewBy.ViewSelection.Tenant);
        InputManager.windowToOpen = "ViewBy.fxml";
        winMan.openWindow("ViewBy.fxml");
    }
    
    @FXML
    public void upkeepSelection(){
        WindowManager winMan = new WindowManager();
        viewBy.setSelection(ViewBy.ViewSelection.UpkeepCost);
        winMan.openWindow("ViewBy.fxml");
    }
    
    public TreeView getMainAddressTree(){
        return addresses;
    }
    
    private String getAddressFromTree(){
        DataParse dp = new DataParse();
        String address = dp.getAddressFromTreeItem(addresses.getSelectionModel().getSelectedItem());
        return address;
    }
    
    private void setAccelerators(){
        houseAdd.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        entryAdd.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN));
        houseRemove.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN));
        recordRemove.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN));
        viewAll.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHIFT_DOWN));
        viewDate.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHIFT_DOWN));
        viewTenant.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.SHIFT_DOWN));
        viewCosts.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN));
        signOut.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        deleteAccount.setAccelerator(new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN));
    }
    
    private void initPositions(){
        centerGrid.add(houseImg, 4, 0);
        centerGrid.add(houseImg.getActionLabel(), 4,0);
        tableLabel.setTextAlignment(TextAlignment.LEFT);
        houseImg.setTranslateX(-80);
        tableLabel.setTranslateX(50);
        houseTable.setTranslateX(20);
    }
    
    private void initAddressTree(){
        WindowManager winMan = new WindowManager();
        winMan.setAddressTree(addPar);
        winMan.setAddressContainer(addresses);
        warn.setAutoFix(true);
        
        addresses.setRoot(addPar);
        addresses = fillAddressTree(addresses, addPar);
        addPar.setValue("My Houses");
        addPar.setExpanded(true);
        
        if(addPar.getChildren().size() > 0){
            addresses.getSelectionModel().select(addPar.getChildren().get(0));
            fillTableForSelected(false, addresses, addPar, entries);
        }
    }
    
    private void initCursor(){
        houseTable.setCursor(Cursor.HAND);
        addresses.setCursor(Cursor.HAND);
        houseImg.setCursor(Cursor.HAND);
    }
    
    private void initStyles(){
        tableLabel.setStyle("-fx-font-size: 30px;"
                          + "-fx-text-fill: white;"
                          + "-fx-background-color: grey;"
                          + "-fx-border-width: 1px;"
                          + "-fx-border-color: black;"
                          + "-fx-border-style: solid;"
                          + "-fx-border-radius: 5px;"
                          + "-fx-padding: 15px;"
                          + "-fx-opacity: .9;");
        
        
    }
    
    private void initPage(){
        houseImg.addOpenImageViewerEvent();
        initAddressTree();
        initPositions();
        setAccelerators();
        initCursor();
        initStyles();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initPage();
       
    }    
}

