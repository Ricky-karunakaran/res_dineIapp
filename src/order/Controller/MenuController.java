
package order.Controller;

import com.utils.ControllerBase;
import order.Model.Menu;
import com.utils.DataFetchService;
import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
import customerAuthentication.Controller.CheckInController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import systemAccount.Controller.SystemAccountController;
import systemAccount.Model.Restaurant;

/**
 *
 * @author Ricky
 */
public class MenuController extends ControllerBase implements Initializable{
    @FXML TableView tableView;
    @FXML TableColumn menu_description;
    @FXML TableColumn menu_item_quantity;
    @FXML TableColumn menu_detail_cell;
    @FXML TableColumn menu_delete_cell;
    @FXML Region main_container;
    
    @FXML Text edit_menu_description;
    @FXML Text edit_menu_item_quantity;
    
    @FXML TextField menu_title_input;
    @FXML ScrollPane menu_item_pane;
    
    @FXML private Text menuMenuItem;
    @FXML private Text sessionMenuItem;
    @FXML private Text accountMenuItem;
    
    private String file_uploaded;
    private String currentView;
    public void show_add_menu() throws IOException{
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/addMenuView.fxml");
    }
    public void add_menu() throws Exception{
        SessionManager sessionManager = SessionManager.getInstance();
        Restaurant restaurant = (Restaurant) sessionManager.getSession().getAttributes("restaurant");
        
        String menuTitle = menu_title_input.getText();
        Menu menu = new Menu();
        menu.setMenuDescription(menuTitle);
        menu.setMenuRestaurantId(restaurant.getRestaurantId());
        
        try{
            menu.add_menu();
        }catch(SQLException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Fail to create new menu");
            a.setHeaderText("Server Error");
            a.show();
        } catch(Exception e ){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Fail to create new menu");
            a.setHeaderText("System Error");
            a.show();
        }
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/menuView.fxml");
        
    }
    public void edit_menu() {
        SessionManager sessionManager = SessionManager.getInstance();
        Menu menu = (Menu) sessionManager.getSession().getAttributes("menu_viewing");
        String menuTitle = menu_title_input.getText();
        Menu newMenu = new Menu();
        menu.setMenuDescription(menuTitle);
        try {
            menu.edit_menu();
            sessionManager.getSession().setAttributes("menu_viewing", menu);
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/menuDetailView.fxml");
        } catch (Exception ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        this.currentView=view;
        this.setupMenuRoute();
        if(view.equals("menuDetailView.fxml")){
            this.initialize_menu_detail();
        }
        if(view.equals("editMenuView.fxml")){
            this.initialize_menu_edit();
        }
        if (view.equals("menuView.fxml")) {
            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            Restaurant restaurant = (Restaurant)session.getAttributes("restaurant");

            DataFetchService dataFetchService = new DataFetchService();
            ObservableList<Menu> menuList = FXCollections.observableArrayList(Menu.read_all_menu(restaurant.getRestaurantId()));

            menu_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            menu_item_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            tableView.setItems(menuList);
            int numRows = tableView.getItems().size();
            double rowHeight = tableView.getFixedCellSize();
            double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
            tableView.setPrefHeight(prefHeight);
            
            menu_detail_cell.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Edit");
            button.setOnAction(event->{
                Menu menu = (Menu) tableView.getItems().get(tableCell.getIndex());
                try {
                    sessionManager.getSession().setAttributes("menu_viewing",menu);
                    SceneChanger.changeScene((Stage) this.tableView.getScene().getWindow(), "/order/View/menuDetailView.fxml");
                    
                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
            
        menu_delete_cell.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Delete");
            button.setOnAction(event->{
                try {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Are you sure to delete this menu item?");
                    a.setHeaderText("Delete Menu Item Confirmation");
                    Optional<ButtonType> answer = a.showAndWait();
                    if(answer.get() == ButtonType.OK) {
                        Menu menu = (Menu) tableView.getItems().get(tableCell.getIndex());
                        tableView.getItems().remove(menu);
                        try {
                            menu.delete_menu();
                        } catch (Exception ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        SceneChanger.changeScene((Stage) this.tableView.getScene().getWindow(), "/order/View/menuView.fxml");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        }
    }
    
    private void initialize_menu_detail(){
        SessionManager sessionManager = SessionManager.getInstance();
        Menu menu = (Menu) sessionManager.getSession().getAttributes("menu_viewing");
        
        this.edit_menu_description.setText(menu.getDescription());
        this.edit_menu_item_quantity.setText(Integer.toString(menu.getQuantity()));
        
        
        // Setting up menu item
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        this.menu_item_pane.setContent(vbox);
        
        HBox ihbox = new HBox();
        vbox.getChildren().add(ihbox);
        
        for(int i =0;i<menu.getMenuItems().size();i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            
            VBox nameContainer = new VBox();
            nameContainer.setSpacing(10);
            nameContainer.setPadding(new Insets(10));
            
            Label nameLabel = new Label("Name: "+menu.getMenuItems().get(i).getMenuItemName());
            Label descriptionLabel = new Label("Description: " + menu.getMenuItems().get(i).getMenuItemDescription());
            Label priceLabel = new Label("Price: RM"+String.valueOf(menu.getMenuItems().get(i).getMenuItemPrice()));
            
            nameLabel.getStyleClass().add("body-text");
            descriptionLabel.getStyleClass().add("body-text");
            priceLabel.getStyleClass().add("body-text");
            
            nameContainer.getChildren().add(nameLabel);
            nameContainer.getChildren().add(descriptionLabel);
            nameContainer.getChildren().add(priceLabel);
            
            HBox actionContainer = new HBox();
            actionContainer.setSpacing(10);
            actionContainer.setId(String.valueOf(i));
            Button editItemButton = new Button();
            editItemButton.setText("Edit Item");
            editItemButton.getStyleClass().add("action-button");
            editItemButton.setOnAction(event->{
                String index = ( (Button)event.getSource() ).getParent().getId();
                sessionManager.getSession().setAttributes("menu-item-editing", menu.getMenuItems().get(Integer.valueOf(index)));
                try {
                    SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/editMenuItemView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            Button removeItemButton = new Button();
            removeItemButton.setText("Remove Item");
            removeItemButton.getStyleClass().add("action-button");
            removeItemButton.setOnAction(event->{
                try {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Are you sure to delete this menu?");
                    a.setHeaderText("Delete Menu Confirmation");
                    Optional<ButtonType> answer = a.showAndWait();
                    if(answer.get() == ButtonType.OK) {
                        try {
                            String index = ( (Button)event.getSource() ).getParent().getId();
                            MenuItemController.remove_menu_item(menu.getMenuItems().get(Integer.valueOf(index)).getMenuId());
                        } catch (Exception ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/menuDetailView.fxml");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            actionContainer.getChildren().add(editItemButton);
            actionContainer.getChildren().add(removeItemButton);
            
            nameContainer.getChildren().add(actionContainer);
            try{
                double original_whRatio = 0;
                double desired_width = 300.0;
                double desired_height = desired_width/original_whRatio;
                ImageView imageView = new ImageView();
                try{
                    ByteArrayInputStream bis = new ByteArrayInputStream(menu.getMenuItems().get(i).getMenuItemImage());
                    BufferedImage bufferedImage = ImageIO.read(bis);
                    original_whRatio = bufferedImage.getWidth()*1.0/bufferedImage.getHeight()*1.0;
                    desired_width = 300.0;
                    desired_height = desired_width/original_whRatio;
                    imageView = new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));
                } catch(Exception e){
                    
                }

                imageView.setFitHeight(desired_height);
                imageView.setFitWidth(desired_width);
                if(original_whRatio == 0){
                    imageView.setFitHeight(300);
                    imageView.setFitWidth(300);
                }

                hbox.getChildren().add(imageView);
            } catch (Exception e){
                System.out.println(e);
            }
            
            hbox.getChildren().add(nameContainer);
            vbox.getChildren().add(hbox);
        }
        
    }
    
    private void initialize_menu_edit(){
        SessionManager sessionManager = SessionManager.getInstance();
        Menu menu = (Menu) sessionManager.getSession().getAttributes("menu_viewing");
        
        menu_title_input.setText(menu.getDescription());
    }

    public void toAddMenuItem() throws IOException{
        
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/addMenuItemView.fxml");
    }
    
    public void toEditMenu() throws IOException{
        
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/editMenuView.fxml");
    }
    
     public void back_previous() throws IOException{
        String returnView;
        SceneChanger sceneChanger = SceneChanger.getInstance();
        if(this.currentView.equals("editMenuView.fxml")) {
            returnView = "/order/View/menuDetailView.fxml";
        } else if (this.currentView.equals("menuDetailView.fxml")){
            returnView = "/order/View/menuView.fxml";
        }
        else {
            returnView = "/order/View/menuView.fxml";
        }
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), returnView);
    }
     
    
}
