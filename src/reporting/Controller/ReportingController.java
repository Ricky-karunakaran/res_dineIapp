package reporting.Controller;

import com.utils.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class ReportingController implements Initializable{
    @FXML Region main_container;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void to_overall_report(){
        try {
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/feedbackDetailReplyView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void to_specific_report(){
        
    }
    
}
