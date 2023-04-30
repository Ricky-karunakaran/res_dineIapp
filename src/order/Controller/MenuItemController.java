/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order.Controller;

import com.utils.SceneChanger;
import com.utils.SessionManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import order.Model.Menu;
import order.Model.MenuItem;

/**
 *
 * @author Ricky
 */
public class MenuItemController implements Initializable {
    @FXML Region main_container;
    @FXML Text input_file_name;
    public String file_uploaded;
    private byte[] file;
    
    @FXML TextField menu_item_name_input;
    @FXML TextField menu_item_description_input;
    @FXML TextField menu_item_price_input;
    @FXML TextField menu_item_allergy_input;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);

        if(view.equals("editMenuItemView.fxml")){
            this.initialize_menu_item_edit();
        }
    }
    
    public void showFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog((Stage) this.main_container.getScene().getWindow());
        
        if (selectedFile != null) {
            input_file_name.setText(selectedFile.getName());
            file = new byte[(int) selectedFile.length()];
        try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
            inputStream.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    public void add_menu_item() throws IOException{
        SessionManager sessionManager = SessionManager.getInstance();
        Menu menu = (Menu) sessionManager.getSession().getAttributes("menu_viewing");

        String menu_item_name = this.menu_item_name_input.getText();
        String menu_item_description = this.menu_item_description_input.getText();
        String menu_item_price = this.menu_item_price_input.getText();
        String menu_item_allergy = this.menu_item_allergy_input.getText();
        
        MenuItem menuItem = new MenuItem();
        
        menuItem.setMenuId(menu.getMenuId());
        menuItem.setMenuItemName(menu_item_name);
        menuItem.setMenuItemDescription(menu_item_description);
        menuItem.setMenuItemPrice(Double.parseDouble(menu_item_price));
        menuItem.setMenuItemAllergy(menu_item_allergy);
        menuItem.setMenuItemImage(file);
        
        menuItem.add_menu_item();
        menu.resetMenuItem();
        sessionManager.getSession().setAttributes("menu_viewing", menu);
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/menuDetailView.fxml");
    }
    public void edit_menu_item(){
        SessionManager sessionManager = SessionManager.getInstance();
        MenuItem menuItem = (MenuItem) sessionManager.getSession().getAttributes("menu-item-editing");
        MenuItem newMenuItem = menuItem;
        
        String menu_item_name = this.menu_item_name_input.getText();
        String menu_item_description = this.menu_item_description_input.getText();
        String menu_item_price = this.menu_item_price_input.getText();
        String menu_item_allergy = this.menu_item_allergy_input.getText();
        
        
        newMenuItem.setMenuItemName(menu_item_name);
        newMenuItem.setMenuItemDescription(menu_item_description);
        newMenuItem.setMenuItemPrice(Double.parseDouble(menu_item_price));
        newMenuItem.setMenuItemAllergy(menu_item_allergy);
        if(file!=null) {
            newMenuItem.setMenuItemImage(file);
        }
        newMenuItem.edit_menu_item();
        
        Menu menu = (Menu) sessionManager.getSession().getAttributes("menu_viewing");
        menu.resetMenuItem();
        sessionManager.getSession().setAttributes("menu_viewing", menu);
        try {
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/order/View/menuDetailView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MenuItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void back_previous() throws IOException{
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.getRouteHistory().pop();
        SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), (String) sceneChanger.getRouteHistory().pop());
    }

    public static void remove_menu_item(String menuItemId){
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(menuItemId);
        try{
            menuItem.remove_menu_item();
            SessionManager sessionManager = SessionManager.getInstance();
            Menu menu = (Menu) sessionManager.getSession().getAttributes("menu_viewing");
            menu.resetMenuItem();
            sessionManager.getSession().setAttributes("menu_viewing", menu);
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public void initialize_menu_item_edit(){
        SessionManager sessionManager = SessionManager.getInstance();
        MenuItem menuItem = (MenuItem) sessionManager.getSession().getAttributes("menu-item-editing");
        menu_item_name_input.setText(menuItem.getMenuItemName());
        menu_item_description_input.setText(menuItem.getMenuItemDescription());
        menu_item_price_input.setText(String.valueOf(menuItem.getMenuItemPrice()));
        menu_item_allergy_input.setText(menuItem.getMenuItemAllergy());
        if(menuItem.getMenuItemImage()!= null ){
            input_file_name.setText("Uploaded_picture");
        }
    }
}
