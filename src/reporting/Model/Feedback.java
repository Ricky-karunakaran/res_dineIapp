/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reporting.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Feedback {
    private String feedback_id;
    private String feedback_content;
    private String feedback_reply;
    private String feedback_session_id;
    private String feedback_date_time;

    private String feedback_user_name;
    public void setFeedbackId(String feedback_id) { this.feedback_id = feedback_id; }
    public void setFeedbackContent(String feedback_content) { this.feedback_content = feedback_content;}
    public void setFeedbackReply (String feedback_reply) { this.feedback_reply = feedback_reply;}
    public void setFeedbackSessionId(String feedback_session_id) { this.feedback_session_id =feedback_session_id; }
    public void setFeedbackUserName(String feedback_user_name) { this.feedback_user_name = feedback_user_name; }
    public void setFeedbackDateTime(String feedback_date_time) {this.feedback_date_time = feedback_date_time;}
    public String getFeedbackId() { return this.feedback_id; }
    public String getFeedbackContent() { return this.feedback_content; }
    public String getFeedbackReply() { return this.feedback_reply; }
    public String getFeedbackSessionId() { return this.feedback_session_id; }
    public String getFeedbackDateTime() { return this.feedback_date_time; }
    public String getFeedbackUserName() { return this.feedback_user_name; }

    public void add_feedback(){
        try{
            String sql = "INSERT INTO feedback (feedback_content, feedback_session_id) VALUES (?,?)";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.feedback_content);
            pt.setString(2, this.feedback_session_id);
            pt.executeUpdate();

        } catch (Exception e){

        }
    }
    public void update_feedback(){
        try{
            String sql = "UPDATE  feedback SET feedback_content = ?,feedback_reply = ? WHERE  feedback_session_id = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.feedback_content);
            pt.setString(2, this.feedback_reply);
            pt.setString(3, this.feedback_session_id);
            pt.executeUpdate();
        } catch (Exception e){

        }
    }
    public void get_feedback_by_id(){

    }
    public void get_feedback_by_session_id(){
        try{
            String sql = "SELECT * from feedback WHERE feedback_session_id = ?";
            Connection con =  dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.feedback_session_id);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                this.feedback_id=rs.getString("feedback_id");
                this.feedback_content = rs.getString("feedback_content");
                this.feedback_reply = rs.getString("feedback_reply");
                
            }
        }catch(Exception e){

        }
    }
    
    public ArrayList<Feedback> get_feedbacks_by_restaurant_id(String restaurant_id){
        ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
        try{
            String sql = "SELECT * from feedback Inner join session on feedback.feedback_session_id= session.session_id INNER JOIN restaurant on session.session_restaurant_id = restaurant.restaurant_id INNER JOIN user on user.user_email = session.session_user_id WHERE restaurant.restaurant_id = ?";
            Connection con =  dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,restaurant_id );
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getString("feedback_id"));
                feedback.setFeedbackContent(rs.getString("feedback_content"));
                feedback.setFeedbackReply(rs.getString("feedback_reply"));
                feedback.setFeedbackSessionId(rs.getString("feedback_session_id"));
                feedback.setFeedbackDateTime(rs.getString("feedback_date_time"));
                feedback.setFeedbackUserName(rs.getString("user.user_name"));
                feedbacks.add(feedback);
            }
        }catch(Exception e){

        }
        return feedbacks;
    }
}

