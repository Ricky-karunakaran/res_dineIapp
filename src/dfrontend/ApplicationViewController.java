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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ricky
 */
public class ApplicationViewController implements Initializable {
    @FXML private Text mainTitle;
    @FXML private Button adminLoginButton;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Region mainContainer;
    @FXML private ImageView backGround;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.mainTitle.setStyle(String.format("-fx-font-size:%s",WindowSize.getScreenWidth()*0.0312));
        this.backGround.setFitWidth(WindowSize.getScreenWidth());
        this.backGround.setFitHeight(WindowSize.getScreenHeight());

        if(this.adminLoginButton!=null) {
            this.adminLoginButton.setOnAction(event->{
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/systemAccount/View/adminLoginView.fxml"));
                    Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
                    Stage currentStage = (Stage)this.mainContainer.getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if(this.loginButton!=null) { 
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
        }
        if(this.loginButton!=null) { 
            this.registerButton.setOnAction(event->{
                try {
                    SceneChanger.changeScene((Stage)this.mainContainer.getScene().getWindow(), "/systemAccount/View/registerView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        }
    }

    
}
