/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customerAuthentication.Controller;

import com.utils.ControllerBase;
import com.utils.FileLogger;
import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
import customerAuthentication.Model.Dine_In_Session;
import helper.SessionOrderFetchService;
import java.io.IOException;
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
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import order.Model.Bill;
import order.Model.BillItem;
import systemAccount.Controller.RestaurantAccountController;
import systemAccount.Controller.SystemAccountController;
import systemAccount.Model.User;

/**
 *
 * @author Ricky
 */
public class CheckOutController extends ControllerBase implements Initializable{
    @FXML TableView tableView;
    @FXML private Text menuMenuItem;
    @FXML private Text sessionMenuItem;
    @FXML private Text accountMenuItem;
    @FXML private Text customer_name;
    @FXML private Text session_detail_price;
    
    @FXML TableColumn order_item;
    @FXML TableColumn order_quantity;
    @FXML TableColumn order_total_price;
    
    @FXML TableColumn increase_quantity;
    @FXML TableColumn decrease_quantity;
    @FXML TableColumn remove_item;
    
    Timeline order_time_line;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        this.setupMenuRoute();
        if(view.equals("sessionDetailView.fxml")){
            this.initialize_session_detail();
        }
        
        
    }
    
    public void initialize_session_detail(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Dine_In_Session viewing_session = (Dine_In_Session) session.getAttributes("viewing_session");
        Bill bill = new Bill();
        bill.setBillId(viewing_session.getSessionBillId());
        this.customer_name.setText(viewing_session.getSessionUserName());
        
        ObservableList<BillItem> billLists = FXCollections.observableArrayList(bill.getBillItems());
        tableView.setItems(billLists);
        SessionOrderFetchService dataFetchService = new SessionOrderFetchService(bill.getBillId());
        order_time_line = new Timeline(
               new KeyFrame(Duration.seconds(3),event->{
                   if(dataFetchService.getState() == Service.State.READY) {
                       dataFetchService.start();
                   } else if(dataFetchService.getState() == Service.State.SUCCEEDED){
                       ArrayList<BillItem> newList = dataFetchService.getValue();
                       tableView.getItems().setAll(newList);
                       int numRows = tableView.getItems().size();
                        double rowHeight = tableView.getFixedCellSize();
                        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
                        tableView.setPrefHeight(prefHeight);
                        dataFetchService.reset();
                   }
               })
        );
        order_time_line.setCycleCount(Timeline.INDEFINITE);
        order_time_line.play();
        int numRows = tableView.getItems().size();
        double rowHeight = tableView.getFixedCellSize();
        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
        tableView.setPrefHeight(prefHeight);
        
        order_item.setCellValueFactory(new PropertyValueFactory<>("billItemName"));
        order_quantity.setCellValueFactory(new PropertyValueFactory<>("billItemQuantity"));
        order_total_price.setCellValueFactory(new PropertyValueFactory<>("billItemPrice"));
        try{
            session_detail_price.setText("RM: "+String.valueOf(bill.getBillPrice()));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void checkOut() throws IOException{
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Dine_In_Session viewing_session = (Dine_In_Session) session.getAttributes("viewing_session");
        viewing_session.check_out_session();
        User user = new User();
        user.check_out(viewing_session.getSessionId());
        SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/sessionView.fxml");
    }
    public void setupMenuRoute(){
        super.setupMenuRoute();
    }
    public void editOrder(){
        order_time_line.stop();
         try {
            SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/sessionDetailEditView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void displayAddOrderForm(){
        try {
            SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/addOrderView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
