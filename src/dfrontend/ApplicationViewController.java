/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dfrontend;

import com.utils.SceneChanger;
import com.utils.WindowSize;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ricky
 */
public class ApplicationViewController implements Initializable {
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private AnchorPane mainContainer;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.loginButton.setOnAction(event->{
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/systemAccount/View/loginView.fxml"));
                Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
                Stage currentStage = (Stage)this.loginButton.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        this.registerButton.setOnAction(event->{
            try {
                SceneChanger.changeScene((Stage)this.mainContainer.getScene().getWindow(), "/systemAccount/View/registerView.fxml");

                // root = FXMLLoader.load(getClass().getResource("/systemAccount/View/registerView.fxml"));
//                Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
//                Stage currentStage = (Stage)this.registerButton.getScene().getWindow();
//                currentStage.setScene(scene);
//                currentStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }

    
}
