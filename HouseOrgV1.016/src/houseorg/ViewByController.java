
package houseorg;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mitch
 */
public class ViewByController implements Initializable {

    ViewBy viewBy = new ViewBy();
    char[] numbers = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    @FXML
    Label Heading;
    
    @FXML
    TextField viewByText;
    
    @FXML
    Label viewByLabel;
    
    @FXML
    Label endRangeLabel;
    
    @FXML
    TextField endRangeText;
    
    @FXML
    public void SubmitSelection(){
        if(viewBy.getSelection() == ViewBy.ViewSelection.UpkeepCost){
            if(verifyNumber(viewByText.getText()) == true && verifyNumber(endRangeText.getText()) == true){
                viewBy.setCostSelection(Double.parseDouble(viewByText.getText()));
                viewBy.setEndCostSelection(Double.parseDouble(endRangeText.getText()));
            }
        }else if(viewBy.getSelection() == ViewBy.ViewSelection.Date){
            viewBy.setDateSelection(viewByText.getText());
        }else if(viewBy.getSelection() == ViewBy.ViewSelection.Tenant){
            viewBy.setTenantSelection(viewByText.getText());
        }
        System.out.println(viewByText.getText());
        Scene scene = viewByText.getScene();
        Stage stage = (Stage)scene.getWindow();
        WindowManager winMan = new WindowManager();
        winMan.closeStage(stage);
    }
    
    public boolean verifyNumber(String s){
        boolean isDigit = false;
        for(int i = 0; i < s.length(); i++){
                for(int j = 0; j < numbers.length; j++){
                    if(s.charAt(i) == numbers[j]){
                        isDigit = false;
                        break;
                    }else{
                        isDigit = true;
                    }
                }
            }
        return isDigit;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(viewBy.getSelection() == ViewBy.ViewSelection.UpkeepCost){
            Heading.setText("View By UpkeepCost");
            viewByLabel.setText("From: ");
            endRangeLabel.setVisible(true);
            endRangeText.setVisible(true);
        }else{
            Heading.setText("View By " + viewBy.getStringSelection());
            endRangeLabel.setVisible(false);
            endRangeText.setVisible(false);
            viewByLabel.setText("View By " + viewBy.getStringSelection() + ": ");
        }
        
    }    
    
}
