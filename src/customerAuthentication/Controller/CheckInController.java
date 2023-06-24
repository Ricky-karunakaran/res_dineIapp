/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customerAuthentication.Controller;

import com.utils.ControllerBase;
import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
import customerAuthentication.Model.Check_In_Request;
import customerAuthentication.Model.Dine_In_Session;
import helper.SessionDataFetchService;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import systemAccount.Controller.RestaurantAccountController;
import systemAccount.Model.Restaurant;
import systemAccount.Model.User;

/**
 *
 * @author Ricky
 */
public class CheckInController extends ControllerBase implements Initializable {
    @FXML TableView tableView;
    @FXML TableColumn session_user_name;
    @FXML TableColumn session_status;
    @FXML TableColumn session_detail;
    @FXML TableColumn session_id;
    @FXML TableColumn session_start_time;
    
    
    @FXML TableColumn customer_email;
    @FXML TableColumn check_in_time;
    @FXML TableColumn accept;
    @FXML TableColumn reject;
    
    Timeline sessionTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        
        this.setupMenuRoute();
        System.out.println(view);
        if(view.equals("sessionView.fxml")){
            this.initialize_home_view();
        }
        else if(view.equals("checkInRequestView.fxml")){
            this.initialize_check_in_request_view();
        }
        
        

    }
    
    // fetch session list
    public void initialize_home_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
        
        SessionDataFetchService dataFetchService = new SessionDataFetchService(restaurant.getRestaurantId());
            Dine_In_Session dine_in_session = new Dine_In_Session();
            ObservableList<Dine_In_Session> sessionList = FXCollections.observableArrayList(dine_in_session.get_session_by_restaurant_id(restaurant.getRestaurantId()));
            
            sessionTimeline = new Timeline(
               new KeyFrame(Duration.seconds(3),event->{
                   if(dataFetchService.getState() == Service.State.READY) {
                       dataFetchService.start();
                   } else if(dataFetchService.getState() == Service.State.SUCCEEDED){
                       ArrayList<Dine_In_Session> newList = dataFetchService.getValue();
                       System.out.println(newList.size());
                       tableView.getItems().setAll(newList);
                       int numRows = tableView.getItems().size();
                        double rowHeight = tableView.getFixedCellSize();
                        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                        tableView.setPrefHeight(prefHeight);
                        dataFetchService.reset();
                   }
               })
        );
        sessionTimeline.setCycleCount(Timeline.INDEFINITE);
        sessionTimeline.play();
        tableView.setItems(sessionList);
        int numRows = tableView.getItems().size();
        double rowHeight = tableView.getFixedCellSize();
        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
        tableView.setPrefHeight(prefHeight);
        
        session_user_name.setCellValueFactory(new PropertyValueFactory<>("sessionUserName"));
        session_status.setCellValueFactory(new PropertyValueFactory<>("sessionStatus"));
        session_id.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        session_start_time.setCellValueFactory(new PropertyValueFactory<>("sessionStartTime"));
        session_detail.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Detail");
            button.setOnAction(event->{
                try {
                    Dine_In_Session view_session = (Dine_In_Session) tableView.getItems().get(tableCell.getIndex());
                    sessionManager.getSession().setAttributes("viewing_session", view_session);
                     SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/sessionDetailView.fxml");
                    sessionTimeline.stop();
                } catch (Exception ex) {
                    Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        
    }
    
    // fetch check_in_request
    public void initialize_check_in_request_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
        Check_In_Request request = new Check_In_Request();
        request.setCheckInRestaurantId(restaurant.getRestaurantId());
        ObservableList<Check_In_Request> requests = FXCollections.observableArrayList(request.getCheckInRequests());
        
        
        
        customer_email.setCellValueFactory(new PropertyValueFactory<>("checkInUserEmail"));
        check_in_time.setCellValueFactory(new PropertyValueFactory<>("checkInRequestDateTime"));
        
        accept.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Approve");
            button.setOnAction(event->{
                Check_In_Request check_in_request = (Check_In_Request) tableView.getItems().get(tableCell.getIndex());
                check_in_request.setCheckInRequestStatus("ACCEPTED");
                check_in_request.update_check_in_request();
                tableView.getItems().remove(tableCell.getIndex());
                int numRows = tableView.getItems().size();
                double rowHeight = tableView.getFixedCellSize();
                double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                tableView.setPrefHeight(prefHeight);

            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        
        reject.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Reject");
            button.setOnAction(event->{
                Check_In_Request check_in_request = (Check_In_Request) tableView.getItems().get(tableCell.getIndex());
                check_in_request.setCheckInRequestStatus("REJECTED");
                check_in_request.update_check_in_request();
                tableView.getItems().remove(tableCell.getIndex());
                int numRows = tableView.getItems().size();
                double rowHeight = tableView.getFixedCellSize();
                double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                tableView.setPrefHeight(prefHeight);
                User user = new User();
                user.setUserEmail(check_in_request.getCheckInUserEmail());
                user.check_out_by_email();
                
                
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        tableView.setItems(requests);
        int numRows = tableView.getItems().size();
        double rowHeight = tableView.getFixedCellSize();
        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
        tableView.setPrefHeight(prefHeight);
    }
    
    @Override
    public void beforeRoute(){
        if(sessionTimeline != null) { sessionTimeline.stop(); }
    }
    
}
