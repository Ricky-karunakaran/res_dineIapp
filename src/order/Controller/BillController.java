/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order.Controller;

import com.utils.ControllerBase;
import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
import customerAuthentication.Controller.CheckOutController;
import customerAuthentication.Model.Dine_In_Session;
import helper.SessionOrderFetchService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import order.Model.Bill;
import order.Model.BillItem;
import order.Model.Menu;
import order.Model.MenuItem;
import systemAccount.Controller.RestaurantAccountController;
import systemAccount.Model.Restaurant;

/**
 *
 * @author Ricky
 */
public class BillController extends ControllerBase implements Initializable {
    @FXML Region main_container;
    @FXML private Text customer_name;
    @FXML TableView tableView;
    @FXML TableColumn order_item;
    @FXML TableColumn order_quantity;
    @FXML TableColumn order_total_price;
    
    @FXML TableColumn increase_quantity;
    @FXML TableColumn decrease_quantity;
    @FXML TableColumn remove_item;
    
    @FXML ChoiceBox orderItemSelect;
    @FXML TextField order_item_quantity;
    
    @FXML Text backMenuItem;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        if(view.equals("sessionDetailEditView.fxml")){
            this.initialize_session_detail_edit();
        }
        if(view.equals("addOrderView.fxml")){
            this.initialize_add_order_view();
        }
    }
    
    public void initialize_session_detail_edit(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Dine_In_Session viewing_session = (Dine_In_Session) session.getAttributes("viewing_session");
        Bill bill = new Bill();
        bill.setBillId(viewing_session.getSessionBillId());
        this.customer_name.setText(viewing_session.getSessionUserName());
        
        ObservableList<BillItem> billLists = FXCollections.observableArrayList(bill.getBillItems());
        tableView.setItems(billLists);
        SessionOrderFetchService dataFetchService = new SessionOrderFetchService(bill.getBillId());
        int numRows = tableView.getItems().size();
        double rowHeight = tableView.getFixedCellSize();
        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
        tableView.setPrefHeight(prefHeight);
        
        order_item.setCellValueFactory(new PropertyValueFactory<>("billItemName"));
        order_quantity.setCellValueFactory(new PropertyValueFactory<>("billItemQuantity"));
        order_total_price.setCellValueFactory(new PropertyValueFactory<>("billItemPrice"));
        
        increase_quantity.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("+");
            button.setOnAction(event->{
                try {
                    BillItem bill_item = (BillItem) tableView.getItems().get(tableCell.getIndex());
                    bill_item.updateQuantity(1);
                    bill_item.updateBillItem();
                    SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/sessionDetailEditView.fxml");
                } catch (Exception ex) {
                    Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        decrease_quantity.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("-");
            button.setOnAction(event->{
                try {
                    BillItem bill_item = (BillItem) tableView.getItems().get(tableCell.getIndex());
                    bill_item.updateQuantity(-1);
                    bill_item.updateBillItem();
                    SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/sessionDetailEditView.fxml");
                } catch (Exception ex) {
                    Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        remove_item.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Remove");
            button.setOnAction(event->{
                try {
                    BillItem bill_item = (BillItem) tableView.getItems().get(tableCell.getIndex());
                    
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Confirmation");
                    confirmationDialog.setHeaderText("Remove Order Item");
                    confirmationDialog.setContentText("Are you sure to remove this order item?");
                    Optional<ButtonType> result = confirmationDialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        bill_item.updateQuantity(bill_item.getBillItemQuantity()*-1);
                        bill_item.updateBillItem();
                    } else {

                    }
                    SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/order/View/sessionDetailEditView.fxml");
                } catch (Exception ex) {
                    Logger.getLogger(RestaurantAccountController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        
    }
    public void initialize_add_order_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
        ArrayList<Menu> menus = new ArrayList<Menu>();
        menus= Menu.read_all_menu(restaurant.getRestaurantId());
        ArrayList<MenuItem> menu_items =new ArrayList<MenuItem>();
        for(int i =0;i<menus.size();i++){
            ArrayList<MenuItem> menu_item = menus.get(i).getMenuItems();
            menu_items.addAll(menu_item);
        }
        for(int i =0;i<menu_items.size();i++){
            orderItemSelect.getItems().add(menu_items.get(i));
        }
        if(this.backMenuItem != null ){
            this.backMenuItem.setOnMouseClicked(event -> {
            try{
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/sessionDetailView.fxml");
            }   catch (IOException ex) {
                }
            });
        }
    }
    public void add_order_item(){
        try{
            if (this.order_item_quantity.getText() == null ) { throw new Exception("No Input for quantity");}
            Double.parseDouble(this.order_item_quantity.getText());
        } catch(Exception  e){
            return;
        }
        MenuItem menuItem = (MenuItem) orderItemSelect.getValue();
        int quantity = Integer.parseInt(this.order_item_quantity.getText());
        
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Dine_In_Session viewing_session = (Dine_In_Session) session.getAttributes("viewing_session");
        
        BillItem bill_item = new BillItem();
        bill_item.setBillId(viewing_session.getSessionBillId());
        bill_item.setBillItemName(menuItem.getMenuItemName());
        bill_item.setBillItemQuantity(quantity);
        bill_item.setBillItemUnitPrice(menuItem.getMenuItemPrice());
        bill_item.addBillItem();
        try {
            SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/sessionDetailView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void exit_edit_order(){
        try {
            SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/customerAuthentication/View/sessionDetailView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
