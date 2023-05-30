package com.reporting.model;

import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Feedback {
    private String feedback_id;
    private String feedback_content;
    private String feedback_reply;
    private String feedback_session_id;


    public void setFeedbackId(String feedback_id) { this.feedback_id = feedback_id; }
    public void setFeedbackContent(String feedback_content) { this.feedback_content = feedback_content;}
    public void setFeedbackReply (String feedback_reply) { this.feedback_reply = feedback_reply;}
    public void setFeedbackSessionId(String feedback_session_id) { this.feedback_session_id =feedback_session_id; }

    public String getFeedbackId() { return this.feedback_id; }
    public String getFeedbackContent() { return this.feedback_content; }
    public String getFeedbackReply() { return this.feedback_reply; }
    public String getFeedbackSessionId() { return this.feedback_session_id; }

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
            String sql = "UPDATE  feedback SET feedback_content = ? WHERE  feedback_session_id = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.feedback_content);
            pt.setString(2, this.feedback_session_id);
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
            }
        }catch(Exception e){

        }
    }
}
