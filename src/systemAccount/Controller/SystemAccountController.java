/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Controller;

import com.utils.SceneChanger;
import com.utils.WindowSize;
import dfrontend.ApplicationViewController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import systemAccount.Model.Restaurant;
import systemAccount.Model.User;

/**
 *
 * @author Ricky
 */
public class SystemAccountController implements Initializable{
    private Stage stage;
    private Scene scene;
    @FXML private BorderPane mainContainer;
    
    @FXML private TextField email;
    @FXML private TextField password;
    
    @FXML private TextField restaurant_name;
    @FXML private TextField restaurant_email;
    @FXML private TextField restaurant_password;
    @FXML private TextField restaurant_password_confirm;
    
    @FXML private Button loginButton;
    @FXML private MenuItem backMenuItem;
    
    @FXML
    private void register_restaurant() throws IOException  {
//        Stage secondStage = new Stage();
//        StackPane secondWindowLayout = new StackPane();
//        secondStage.setScene(new Scene(secondWindowLayout,250,150));
//        secondStage.show();
        String res_email = restaurant_email.getText();
        String res_name = restaurant_name.getText();
        String res_password = restaurant_password.getText();
        String res_password_confirm = restaurant_password_confirm.getText();

        if(!res_password.equals(res_password_confirm)){

            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Passowrd unmatched.");
            a.setHeaderText("Password unmatched");
            a.show();

        } else {

            Restaurant restaurant = new Restaurant ();
            restaurant.setEmail(res_email);
            restaurant.setName(res_name);
            try{
                restaurant.register(res_password);
                Parent root = FXMLLoader.load(getClass().getResource("/systemAccount/View/homeView.fxml"));
                Scene scene = new Scene(root);
                Stage currentStage = (Stage)this.restaurant_password.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("Please try again later");
                a.setHeaderText("System Error");
            }
            
        }
    }
    @FXML
    private void login_restaurant() throws IOException  {
        try{
        String res_email = restaurant_email.getText();
        String res_password = restaurant_password.getText();
        Restaurant restaurant = new Restaurant();
        restaurant.setEmail(res_email);
        if(!restaurant.login(res_password)){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Login failed. Incorrect email or password");
            a.setHeaderText("Login Fail");
            a.show();
        } else {
            SceneChanger.changeScene((Stage)this.mainContainer.getScene().getWindow(), "/systemAccount/View/homeView.fxml");
            SceneChanger.getHistory();
//            Parent root = FXMLLoader.load(getClass().getResource("/systemAccount/View/homeView.fxml"));
//            Scene scene = new Scene(root);
//            Stage currentStage = (Stage)this.mainContainer.getScene().getWindow();
//            currentStage.setScene(scene);
//            currentStage.show();
        }        
        } catch(Exception e) {
            Alert a = new Alert(AlertType.NONE);
            a.setAlertType(AlertType.ERROR);
            a.setContentText(e.toString());
            a.show();
            System.out.println(e);
        }
    }

    @FXML
    private void login() throws IOException{

//        ((Node)event.getSource()).getScene().setCursor(Cursor.WAIT);
//        Parent root = FXMLLoader.load(getClass().getResource("/systemAccount/View/registerForm.fxml"));
//        Scene scene = new Scene(root);
//        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        currentStage.setScene(scene);
//        currentStage.show();
        try{
        String user_email = email.getText();
        String user_password = password.getText();
        System.out.println(user_email);
        System.out.println(user_password);
        User user = new User(user_email);
        if(!user.login(user_password)){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Login failed. Incorrect email or password");
            a.setHeaderText("Login Fail");
            a.show();
        } else {
//            SceneChanger.changeScene(stage, user_email);
//            Parent root = FXMLLoader.load(getClass().getResource("/systemAccount/View/homeView.fxml"));
//            Scene scene = new Scene(root);
//            Stage currentStage = (Stage)this.password.getScene().getWindow();
//            currentStage.setScene(scene);
//            currentStage.show();
        }        
        } catch(Exception e) {
            Alert a = new Alert(AlertType.NONE);
            a.setAlertType(AlertType.ERROR);
            a.setContentText(e.toString());
            a.show();
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.backMenuItem != null){
            this.backMenuItem.setOnAction(event->{
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/dfrontend/applicationView.fxml"));
                Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
                Stage currentStage = (Stage)this.mainContainer.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        }
        
    }
}
