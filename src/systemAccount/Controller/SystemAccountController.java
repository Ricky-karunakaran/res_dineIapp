/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Controller;

import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
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
    @FXML private Region mainContainer;
    
    @FXML private TextField email;
    @FXML private TextField password;
    
    @FXML private TextField restaurant_name;
    @FXML private TextField restaurant_email;
    @FXML private TextField restaurant_location;
    @FXML private TextField restaurant_operation_hours;
    @FXML private TextField restaurant_password;
    @FXML private TextField restaurant_password_confirm;
    
    @FXML private Button loginButton;
    @FXML private Button editProfile;
    @FXML private MenuItem backMenuItem;
    @FXML private ImageView backGround;
    
    @FXML private HBox accountMenuItem;
    @FXML private HBox menuMenuItem;
    
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
        if( res_email.isEmpty() || res_name.isEmpty() || res_password.isEmpty() || res_password_confirm.isEmpty()){
            return;
        }
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
                if(restaurant.register(res_password)){

                    SessionManager session = SessionManager.getInstance();
                    session.createSession();
                    session.getSession().setAttributes("restaurant",restaurant);
                    Parent root = FXMLLoader.load(getClass().getResource("/systemAccount/View/homeView.fxml"));
                    Scene scene = new Scene(root);
                    Stage currentStage = (Stage)this.restaurant_password.getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.show();
                };
                // restaurant.login(res_password);
                
            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText(e.toString());
                a.setHeaderText("System Error");
                a.show();
            }
            
        }
    }
    @FXML
    private void edit_info() {
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
        System.out.println(restaurant.getEmail());
        restaurant.setName(this.restaurant_name.getText());
        restaurant.setLocation(this.restaurant_location.getText());
        restaurant.setOperationHours(this.restaurant_operation_hours.getText());
        try {
            restaurant.edit_info();
            SceneChanger.changeScene((Stage) this.mainContainer.getScene().getWindow(), "/systemAccount/View/homeView.fxml");
        } catch (Exception ex) {
            Logger.getLogger(SystemAccountController.class.getName()).log(Level.SEVERE, null, ex);
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
            SessionManager session = SessionManager.getInstance();
            session.createSession();
            session.getSession().setAttributes("restaurant",restaurant);
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
    private void test() {
        System.out.println("Hi");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
 
        this.mainContainer.setPrefSize(WindowSize.getScreenWidth(),WindowSize.getScreenHeight());
        if(this.backGround!=null) {this.backGround.setFitWidth(WindowSize.getScreenWidth());}
        if(this.backGround!=null) {this.backGround.setFitHeight(WindowSize.getScreenHeight());}
        
        // homeView
        if(this.accountMenuItem != null ){
            
            this.accountMenuItem.setOnMouseClicked(event -> {
            try{
                SceneChanger.getInstance().getRouteHistory().show();
                SceneChanger.changeScene((Stage)this.mainContainer.getScene().getWindow(), "/systemAccount/View/editAccountView.fxml");
            }   catch (IOException ex) {
                    Logger.getLogger(SystemAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        if(this.menuMenuItem != null ){
            
            this.menuMenuItem.setOnMouseClicked(event -> {
            try{
                SceneChanger.changeScene((Stage)this.mainContainer.getScene().getWindow(), "/order/View/menuView.fxml");
            }   catch (IOException ex) {
                    Logger.getLogger(SystemAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
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
        
        //editAccountView
        if(view.equals("editAccountView.fxml")){
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
            restaurant.refreshData();
            System.out.println(restaurant.getLocation());
            if(restaurant.getName()!=null) restaurant_name.setText(restaurant.getName());
            if(restaurant.getLocation()!=null) restaurant_location.setText(restaurant.getLocation());
            if(restaurant.getOperationHours()!=null) restaurant_operation_hours.setText(restaurant.getOperationHours());
            this.editProfile.setOnAction(event->{
                restaurant_name.setEditable(true);
                restaurant_location.setEditable(true);
                restaurant_operation_hours.setEditable(true);
                this.editProfile.setText("Submit");
                this.editProfile.setOnAction(eventt->{this.edit_info();});
            });
            
        }
        
    }
}
