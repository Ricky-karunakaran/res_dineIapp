/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order.Controller;

import com.utils.Session;
import com.utils.SessionManager;
import helper.NotificationDataFetchService;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import order.Model.Notification;
import order.Model.WaiterCall;
import systemAccount.Model.Restaurant;

/**
 *
 * @author Ricky
 */
public class WaiterCallController implements Initializable {
    @FXML TableView tableView;
    @FXML TableColumn notification_time;
    @FXML TableColumn notification_type;
    @FXML TableColumn notification_content;
    @FXML TableColumn notification_user_name;
    
    @FXML TableColumn waiter_call_time;
    @FXML TableColumn waiter_call_table_no;
    @FXML TableColumn waiter_call_user_name;
    @FXML TableColumn waiter_call_content;
    @FXML TableColumn waiter_call_read;

    private Timeline notification_timeline;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        
        // this.setupMenuRoute();
        if(view.equals("notificationView.fxml")){
            this.initialize_notification_view();
        } else if(view.equals("callListView.fxml")){
            this.initialize_call_list_view();
        }
    }
    
    private void initialize_notification_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
        try{
            Notification notification = new Notification();
            notification.setNotificationRestaurantId(restaurant.getRestaurantId());
            ObservableList<Notification> notifications = FXCollections.observableArrayList(notification.get_all_notification());
            tableView.setItems(notifications);
            tableView.setRowFactory(row -> new TableRow<Notification>(){
                @Override
                protected void updateItem(Notification notification, boolean empty){
                    super.updateItem(notification,empty);
                    if(notification!=null) {
                        if(notification.getNotificationStatus().equals("UNREAD")){
                            
                        setStyle("-fx-background-color: lightpink;");
                        String audioFile = "/assets/sticky.mp3";
                        AudioClip audioClip = new AudioClip(getClass().getResource(audioFile).toString());
                        audioClip.play();
//                        audioClip = new AudioClip(getClass().getResource("/assets/sticky.mp3").toString());
//                        audioClip.play();
                        }
                        else {
                            setStyle("");
                        }
                    }
                    
                }
            });
            int numRows = tableView.getItems().size();
            double rowHeight = tableView.getFixedCellSize();
            double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
            tableView.setPrefHeight(prefHeight);
            notification_time.setCellValueFactory(new PropertyValueFactory<>("notificationDateTime"));
            notification_type.setCellValueFactory(new PropertyValueFactory<>("notificationType"));
            notification_content.setCellValueFactory(new PropertyValueFactory<>("notificationContent"));
            notification_user_name.setCellValueFactory(new PropertyValueFactory<>("notificationUserName"));
            
            NotificationDataFetchService dataFetchService = new NotificationDataFetchService(restaurant.getRestaurantId());
            notification_timeline = new Timeline(
                new KeyFrame(Duration.seconds(5),event->{
                    if(dataFetchService.getState() == Service.State.READY) {
                        dataFetchService.start();
                    } else if(dataFetchService.getState() == Service.State.SUCCEEDED){
                        ArrayList<Notification> newList = dataFetchService.getValue();
                        tableView.getItems().setAll(newList);
                        int temp_numRows = tableView.getItems().size();
                        double temp_rowHeight = tableView.getFixedCellSize();
                        double temp_prefHeight = temp_numRows * (temp_rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                        tableView.setPrefHeight(temp_prefHeight);
                        dataFetchService.reset();
                    }
                })
            );
            notification_timeline.setCycleCount(Timeline.INDEFINITE);
            notification_timeline.play();
        } catch(Exception e){
            
        }
        
    }
    private void initialize_call_list_view(){
       SessionManager sessionManager = SessionManager.getInstance();
       Session session = sessionManager.getSession();
       Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
       
       try{
           WaiterCall waiter_call = new WaiterCall();
           waiter_call.setWaiterCallRestaurantId(restaurant.getRestaurantId());
           ObservableList<WaiterCall> waiter_calls = FXCollections.observableArrayList(waiter_call.read_all_waiter_calls());  
           tableView.setItems(waiter_calls);
           waiter_call_time.setCellValueFactory(new PropertyValueFactory<>("waiterCallCreatedAt"));
           waiter_call_table_no.setCellValueFactory(new PropertyValueFactory<>("waiterCallTableNo"));
           waiter_call_content.setCellValueFactory(new PropertyValueFactory<>("waiterCallContent"));
           waiter_call_user_name.setCellValueFactory(new PropertyValueFactory<>("waiterCallUserEmail"));
           waiter_call_read.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Mark as Read");
            button.setOnAction(event->{
                try {
                    WaiterCall selected_waiter_call = (WaiterCall) tableView.getItems().get(tableCell.getIndex());
                    selected_waiter_call.setWaiterCallStatus("SEEN");
                    selected_waiter_call.update_waiter_call();
                    tableView.getItems().remove(tableCell.getIndex());
                    int temp_numRows = tableView.getItems().size();
                    double temp_rowHeight = tableView.getFixedCellSize();
                    double temp_prefHeight = temp_numRows * (temp_rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                    tableView.setPrefHeight(temp_prefHeight);
                } catch (Exception ex) {
                }  
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
           
       } catch (Exception e){
       }
       
       
    }
}
