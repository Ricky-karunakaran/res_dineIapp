/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package systemAccount.Controller;

import com.utils.SceneChanger;
import com.utils.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import systemAccount.Model.Admin;
import systemAccount.Model.Restaurant;

/**
 * FXML Controller class
 *
 * @author Ricky
 */
public class AdminAccountController implements Initializable {

    @FXML
    private AnchorPane mainContainer;
    @FXML
    private ImageView backGround;
    @FXML
    private MenuItem backMenuItem;
    @FXML
    private Button loginButton;
    @FXML private TextField admin_email;
    @FXML private TextField admin_password;
    @FXML private TextField admin_name;
    @FXML private TextField admin_number;

    @FXML HBox manage_restaurant;
    /**
     * Initializes the controller class.
     */
    
    @FXML public void CreateAdminAccount(){
        String adminEmail = admin_email.getText();
        String adminPassword = admin_password.getText();
        String adminName = admin_name.getText();
        String adminNumber = admin_number.getText();
        System.out.println(adminNumber==null);
         System.out.println(adminNumber.isEmpty());
        if(adminEmail.isEmpty()||adminPassword.isEmpty()|adminName.isEmpty()||adminNumber.isEmpty()) {
            return;
        }
        Admin admin = new Admin(adminEmail,adminPassword,adminName,adminNumber);
        try{
            admin.register(adminPassword);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Create new admin account success");
            a.setHeaderText("Operation Success");
            a.show();
        } catch(Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Operation Fail");
            a.setContentText(e.getMessage());
            a.show();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(manage_restaurant != null){
            System.out.println("Not Null");
            manage_restaurant.setOnMouseClicked(event->{
                System.out.println("Clicked");
            try {
                SceneChanger.changeScene((Stage) this.manage_restaurant.getScene().getWindow(), "/systemAccount/View/manageRestaurantView.fxml");
            } catch (IOException ex) {
                Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        }
    }    

    @FXML
    private void login_admin(ActionEvent event) {
        try{
        String adm_email = admin_email.getText();
        String adm_pass = admin_password.getText();
        Admin admin = new Admin();
        admin.setEmail(adm_email);

        if(!admin.login_account(adm_pass)){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Login failed. Incorrect email or password");
            a.setHeaderText("Login Fail");
            a.show();
        } else {
            SceneChanger.changeScene((Stage)this.mainContainer.getScene().getWindow(), "/systemAccount/View/createAdminAccountView.fxml");
            SessionManager session = SessionManager.getInstance();
            session.createSession();
            session.getSession().setAttributes("admin",admin);
            SceneChanger.getHistory();
//            Parent root = FXMLLoader.load(getClass().getResource("/systemAccount/View/homeView.fxml"));
//            Scene scene = new Scene(root);
//            Stage currentStage = (Stage)this.mainContainer.getScene().getWindow();
//            currentStage.setScene(scene);
//            currentStage.show();
        }        
        } catch(Exception e) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText(e.toString());
            a.show();
            System.out.println(e);
        }
    }
    
}
