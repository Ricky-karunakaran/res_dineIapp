/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reporting.Controller;

import com.utils.ControllerBase;
import com.utils.DesktopAlert;
import com.utils.SceneChanger;
import com.utils.Session;
import com.utils.SessionManager;
import customerAuthentication.Model.Dine_In_Session;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import reporting.Model.Feedback;
import systemAccount.Model.Restaurant;

public class FeedbackController extends ControllerBase implements Initializable {
    @FXML Region main_container;
    @FXML Text feedback_date_time;
    @FXML Text feedback_session_id;
    @FXML Text feedback_user_name;
    @FXML Text feedback_content;
    @FXML Text feedback_reply;
    @FXML Button feedback_reply_button;
    
    @FXML TextArea feedback_reply_input;
    
    @FXML TableView tableView;
    @FXML TableColumn c_feedback_date_time;
    @FXML TableColumn c_feedback_session_id;
    @FXML TableColumn c_feedback_user_name;
    @FXML TableColumn c_feedback_content;
    @FXML TableColumn c_feedback_reply;
    @FXML TableColumn c_feedback_detail_button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String view = location.toString().substring(location.toString().lastIndexOf("/")+1);
        this.setupMenuRoute();
        if(view.equals("feedbackView.fxml")){
            this.initialize_feedback_view();
        } else if(view.equals("feedbackDetailView.fxml")){
            this.initialize_feedback_detail_view();
        } else if(view.equals("feedbackDetailReplyView.fxml")){
            this.intialize_feedback_detail_reply_view();
        }
    }
    
    
    private void initialize_feedback_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        Restaurant restaurant = (Restaurant) session.getAttributes("restaurant");
        Feedback feedback = new Feedback();
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList(feedback.get_feedbacks_by_restaurant_id(restaurant.getRestaurantId()));
        tableView.setItems((ObservableList) feedbacks);
        this.c_feedback_date_time.setCellValueFactory(new PropertyValueFactory<>("feedbackDateTime"));
        this.c_feedback_session_id.setCellValueFactory(new PropertyValueFactory<>("feedbackSessionId"));
        this.c_feedback_user_name.setCellValueFactory(new PropertyValueFactory<>("feedbackUserName"));
        this.c_feedback_content.setCellValueFactory(new PropertyValueFactory<>("feedbackContent"));
        this.c_feedback_reply.setCellValueFactory(new PropertyValueFactory<>("feedbackReply"));
        
        this.c_feedback_detail_button.setCellFactory(param -> {
            TableCell tableCell = new TableCell();
            Button button = new Button("Detail");
            button.setOnAction(event->{
                try {
                    Feedback selected_feedback = (Feedback) tableView.getItems().get(tableCell.getIndex());
                    sessionManager.getSession().setAttributes("viewing_feedback_session_id", selected_feedback.getFeedbackSessionId());
                    SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/feedbackDetailView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tableCell.setGraphic(button);
            return tableCell;
        });
        
        int numRows = tableView.getItems().size();
        double rowHeight = tableView.getFixedCellSize();
        double prefHeight = numRows * (rowHeight + 1.0) + 26.0; // 26.0 is the default table header height
        tableView.setPrefHeight(prefHeight);
    }
    private void initialize_feedback_detail_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String feedback_session_id = (String) session.getAttributes("viewing_feedback_session_id");
        
        Feedback feedback = new Feedback();
        feedback.setFeedbackSessionId(feedback_session_id);
        feedback.get_feedback_by_session_id();
        Dine_In_Session feedback_session = new Dine_In_Session();
        feedback_session.setSessionId(feedback.getFeedbackSessionId());
        feedback_session.get_session_by_id();
        this.feedback_date_time.setText(feedback.getFeedbackDateTime());
        this.feedback_content.setText(feedback.getFeedbackContent());
        this.feedback_session_id.setText(feedback.getFeedbackSessionId());
        this.feedback_user_name.setText(feedback_session.getSessionUserName());
        if(feedback.getFeedbackReply()==null){
            this.feedback_reply_button.setVisible(true);
            this.feedback_reply.setVisible(false);
            
        }else {
            this.feedback_reply_button.setVisible(false);
            this.feedback_reply.setVisible(true);
            this.feedback_reply.setText(feedback.getFeedbackReply());
        }
    }
    private void intialize_feedback_detail_reply_view(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String feedback_session_id = (String) session.getAttributes("viewing_feedback_session_id");
        
        Feedback feedback = new Feedback();
        feedback.setFeedbackSessionId(feedback_session_id);
        feedback.get_feedback_by_session_id();
        Dine_In_Session feedback_session = new Dine_In_Session();
        feedback_session.setSessionId(feedback.getFeedbackSessionId());
        feedback_session.get_session_by_id();
        this.feedback_date_time.setText(feedback.getFeedbackDateTime());
        this.feedback_content.setText(feedback.getFeedbackContent());
        this.feedback_session_id.setText(feedback.getFeedbackSessionId());
        this.feedback_user_name.setText(feedback_session.getSessionUserName());
    }
    
    public void to_reply_feedback(){
        try {
            SceneChanger.changeScene((Stage) this.main_container.getScene().getWindow(), "/reporting/View/feedbackDetailReplyView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void save(){
        String reply = this.feedback_reply_input.getText();
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String feedback_session_id = (String) session.getAttributes("viewing_feedback_session_id");
        Feedback feedback = new Feedback();
        feedback.setFeedbackSessionId(feedback_session_id);
        feedback.get_feedback_by_session_id();
        feedback.setFeedbackReply(reply);
        feedback.update_feedback();
        
    }
    
    @Override 
    public void setupMenuRoute(){
        if(backMenuItem!=null){
            backMenuItem.setOnMouseClicked(event -> {
            try{
                SceneChanger.changeScene((Stage)this.main_container.getScene().getWindow(), "/reporting/View/feedbackView.fxml");
            }  catch (Exception ex) {
                    DesktopAlert.showError("System error","Fail to change screen",ex);
            }});
        }
        super.setupMenuRoute();
    }
}
