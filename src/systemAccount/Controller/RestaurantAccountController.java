/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Controller;

import com.utils.DataFetchService;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import systemAccount.Model.Restaurant;
import com.utils.ActionCell;
import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import systemAccount.Model.Model;

/**
 *
 * @author Ricky
 */
public class RestaurantAccountController implements Initializable{
    @FXML TableView tableView;
    @FXML TableColumn restaurant_name;
    @FXML TableColumn restaurant_email;
    @FXML TableColumn restaurant_delete;
    @FXML TableColumn restaurant_detail;
    @FXML HBox create_admin_account;
    @FXML Text detail_email;
    @FXML Text detail_name;
    @FXML Text detail_location;
    @FXML Text detail_operation_hour;
    
    public void deleteRestaurant(String email){
        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        if(tableView != null) {
            DataFetchService dataFetchService = new DataFetchService();
            ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList(Restaurant.getAll());
            
            Timeline timeline = new Timeline(
               new KeyFrame(Duration.seconds(3),event->{
                   if(dataFetchService.getState() == Service.State.READY) {
                       dataFetchService.start();
                   } else if(dataFetchService.getState() == Service.State.SUCCEEDED){
                       ArrayList<Restaurant> newList = dataFetchService.getValue();
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
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        restaurant_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        restaurant_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        
        
        
         restaurant_detail.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Detail");
            button.setOnAction(event->{
                Restaurant restaurant = (Restaurant) tableView.getItems().get(tableCell.getIndex());
                try {
                    SessionManager session = SessionManager.getInstance();
                    session.createSession();
                    session.getSession().setAttributes("restaurant",restaurant);
                    SceneChanger.changeScene((Stage) this.tableView.getScene().getWindow(), "/systemAccount/View/restaurantDetailView.fxml");
                    
                    timeline.stop();
                } catch (IOException ex) {
                    Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(restaurant.getEmail());
                System.out.println(restaurant.getName());
                System.out.println(restaurant.getLocation());
                System.out.println(restaurant.getOperationHours());
                
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
         
        restaurant_delete.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Delete");
            button.setOnAction(event->{
                timeline.pause();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Are you sure to delete this restaurant?");
                a.setHeaderText("Delete Restaurant Confirmation");
                Optional<ButtonType> answer = a.showAndWait();
                if(answer.get() == ButtonType.OK) {
                    Restaurant restaurant = (Restaurant) tableView.getItems().get(tableCell.getIndex());
                    tableView.getItems().remove(restaurant);
                    try {
                        restaurant.deleteRestaurant();
                    } catch (Exception ex) {
                        Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int numRows = tableView.getItems().size();
                double rowHeight = tableView.getFixedCellSize();
                double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                tableView.setPrefHeight(prefHeight);
                }
                timeline.play();
                
            });
            tableCell.setGraphic(button);
            return tableCell;
        });

        tableView.setItems(restaurantList);
        int numRows = tableView.getItems().size();
        double rowHeight = tableView.getFixedCellSize();
        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
        tableView.setPrefHeight(prefHeight);
        create_admin_account.setOnMouseClicked(event->{
            try {
                SceneChanger.changeScene((Stage) this.create_admin_account.getScene().getWindow(), "/systemAccount/View/createAdminAccountView.fxml");
                timeline.stop();
            } catch (IOException ex) {
                Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        }
        
        
        if(view.equals("restaurantDetailView.fxml")){
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            Restaurant restaurant =(Restaurant) session.getAttributes("restaurant");
            detail_email.setText(restaurant.getEmail());
            detail_name.setText(restaurant.getName());
            detail_location.setText(restaurant.getLocation());
            detail_operation_hour.setText(restaurant.getOperationHours());
        }
        
        
        
    }
    
}
