
package houseorg;

import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;

/**
 *
 * @author mitch
 */
public class ExpenseReport extends Popup{
    private AnchorPane anchor = new AnchorPane();
    private GridPane grid = new GridPane();
    private Button exitReport = new Button("Close Report");
    private TextArea report = new TextArea();
    
    public ExpenseReport(){
        exitReport.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                hide();
            }
        });
        report.setMaxWidth(100);
        report.setMinSize(100, 50);
        report.setEditable(false);
        report.wrapTextProperty().set(true);
        grid.add(report, 0, 0);
        grid.add(exitReport, 0, 1);
        grid.setAlignment(Pos.CENTER);
        anchor.getChildren().add(grid);
        anchor.setStyle("-fx-background-color: grey;");
        anchor.setEffect(new DropShadow(25, Color.BLACK));
        anchor.setMinSize(200, 200);
        getContent().add(anchor);
        setAutoHide(true);
    }
    
    public void setReportText(String report){
        this.report.setText(report);
    }
    
    public String getReportText(){
        return report.getText();
    }
    
    public void setReportSize(double width, double height){
        report.setMinSize(width, height);
    }
    
    public double[] getReportSize(){
        double size[] = {report.getWidth(), report.getHeight()};
        return size;
    }
}
