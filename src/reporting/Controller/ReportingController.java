package reporting.Controller;

import com.utils.ControllerBase;
import com.utils.CustomException;
import com.utils.DesktopAlert;
import com.utils.PDFPrinter;
import com.utils.SceneChanger;
import com.utils.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import reporting.Model.Sale_Report;
import systemAccount.Model.Restaurant;


public class ReportingController extends ControllerBase implements Initializable{
    String currentView;
    
    @FXML HBox menu_row;
    @FXML Region main_container;
    @FXML DatePicker report_date_selection_from;
    @FXML DatePicker report_date_selection_to;
    
    @FXML TableView tableView;
    @FXML TableColumn item_name_column;
    @FXML TableColumn item_quantity_column;
    @FXML TableColumn total_sale_column;
    @FXML Text date_from_text;
    @FXML Text date_to_text;
    @FXML Button print_button;
    
    @FXML Button report_date_selection_button;
    
    
    @FXML TextField report_attribute_selection_to;
    @FXML TextField report_attribute_selection_from;
    @FXML Text age_to_text;
    @FXML Text age_from_text;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        this.setupMenuRoute();
        if(view.equals("saleReportView.fxml")){
            this.generate_overall_sales_report();
        } else if(view.equals("reportSpecificSalesView.fxml")){
            this.generate_specific_sales_report();
        }
    }
    
    public void to_overall_report(){
        try {
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/reportDateSelectionView.fxml");
        } catch (IOException ex) {
            DesktopAlert.showError("System error","Fail to change screen",ex);
        }
    }
    
    
    public void to_specific_sales_report(){
        try {
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/reportSpecificDateSelectionView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // specificReport
    public void to_attribute_select_form(){
        try {
            String dateFrom = this.report_date_selection_from.getValue().toString();
            String dateTo = report_date_selection_to.getValue().toString();
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.getSession().setAttributes("dateFrom", dateFrom);
            sessionManager.getSession().setAttributes("dateTo", dateTo);
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/reportAttributeSelectionView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void to_generate_specific_sales_report(){
        try{
            String ageFrom = this.report_attribute_selection_from.getText().toString();
            String ageTo = report_attribute_selection_to.getText().toString();
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.getSession().setAttributes("ageFrom", ageFrom);
            sessionManager.getSession().setAttributes("ageTo", ageTo);
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/reportSpecificSalesView.fxml");
        } catch(Exception e){
            
        }
    }
    // overall report
    public void to_generate_overall_sale_report(){
        try{
            String dateFrom = this.report_date_selection_from.getValue().toString();
            String dateTo = this.report_date_selection_to.getValue().toString();
            if(this.report_date_selection_from.getValue()==null|| this.report_date_selection_to.getValue() ==null){
                throw new CustomException("Invalid input on date range");
            }
            
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.getSession().setAttributes("dateFrom", dateFrom);
            sessionManager.getSession().setAttributes("dateTo", dateTo);
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/saleReportView.fxml");
        } catch(CustomException e){
            DesktopAlert.showAlert("Invalid input", e.getMessage());
        } 
        catch(Exception e){
            DesktopAlert.showError("System error","Fail to change screen",e);
        }
    }
    
    // overall report
    public void generate_overall_sales_report(){
        try{
            SessionManager sessionManager = SessionManager.getInstance();
            Restaurant restaurant = (Restaurant) sessionManager.getSession().getAttributes("restaurant");
            
            String dateFrom = (String) sessionManager.getSession().getAttributes("dateFrom");
            String dateTo = (String) sessionManager.getSession().getAttributes("dateTo");
            this.date_from_text.setText(dateFrom);
            this.date_to_text.setText(dateTo);
            ObservableList<Sale_Report> sales = FXCollections.observableArrayList(restaurant.generate_sales_report(dateFrom, dateTo));

            System.out.println("count"+sales.get(0).getItem());
            this.tableView.setItems((ObservableList) sales);
            
            this.item_name_column.setCellValueFactory(new PropertyValueFactory<>("item"));
            this.item_quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            this.total_sale_column.setCellValueFactory(new PropertyValueFactory<>("sales"));
            
            int numRows = tableView.getItems().size();
            double rowHeight = tableView.getFixedCellSize();
            double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
            tableView.setPrefHeight(prefHeight);
        } catch(Exception e){
        }
    }

    public void generate_specific_sales_report(){
        try{
            SessionManager sessionManager = SessionManager.getInstance();
            Restaurant restaurant = (Restaurant) sessionManager.getSession().getAttributes("restaurant");
            
            String dateFrom = (String) sessionManager.getSession().getAttributes("dateFrom");
            String dateTo = (String) sessionManager.getSession().getAttributes("dateTo");
            String ageFrom = (String) sessionManager.getSession().getAttributes("ageFrom");
            String ageTo = (String) sessionManager.getSession().getAttributes("ageTo");
            this.date_from_text.setText(dateFrom);
            this.date_to_text.setText(dateTo);
            this.age_from_text.setText(ageFrom);
            this.age_to_text.setText(ageTo);
            ObservableList<Sale_Report> sales = FXCollections.observableArrayList(restaurant.generate_specific_sales_report(dateFrom, dateTo, Integer.valueOf(ageFrom), Integer.valueOf(ageTo)));

            this.tableView.setItems((ObservableList) sales);
            
            this.item_name_column.setCellValueFactory(new PropertyValueFactory<>("item"));
            this.item_quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            this.total_sale_column.setCellValueFactory(new PropertyValueFactory<>("sales"));
            
            int numRows = tableView.getItems().size();
            double rowHeight = tableView.getFixedCellSize();
            double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
            tableView.setPrefHeight(prefHeight);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



    public void print_pdf(){
        if(this.menu_row!=null ) { this.menu_row.setVisible(false);}
        Scene currentScene = this.main_container.getScene();
        Stage currentStage = (Stage)this.main_container.getScene().getWindow();
        double oriWidth = currentStage.getWidth();
        double oriHeight = currentStage.getHeight();
        currentStage.setMaxWidth(565.65);
        currentStage.setMaxHeight(800);
        print_button.setVisible(false);
        PDFPrinter.printSceneAsPDF(this.main_container.getScene(), (Stage)this.main_container.getScene().getWindow());
        currentStage.setMaxWidth(oriWidth);
        currentStage.setMaxHeight(oriHeight);
        currentStage.setWidth(oriWidth);
        currentStage.setHeight(oriHeight);
        if(this.menu_row!=null ) { this.menu_row.setVisible(true);}
    }
    
    
    public void setupMenuRoute(){
        super.setupMenuRoute();
    }
    
    @Override
    public void setupBackRoute(){
        if(backMenuItem!=null){
            backMenuItem.setOnMouseClicked(event -> {
            try{
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/reporting/View/reportView.fxml");
            }  catch (Exception ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
            }});
        }
    }
}
