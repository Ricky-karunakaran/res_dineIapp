package com.order.model;

import com.utils.CustomException;
import com.utils.JDateTime;
import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Notification {
    private String notification_id;
    private String notification_type;
    private String notification_status;
    private String notification_content;
    private String notification_session_id;
    private String notification_date_time;


    //Joined Data
    private String notification_restaurant_id;

    //Setter
    public void setNotificationId(String notification_id){ this.notification_id = notification_id; }
    public void setNotificationType(String notification_type){ this.notification_type = notification_type; }
    public void setNotificationStatus(String notification_status){ this.notification_status = notification_status; }
    public void setNotificationContent(String notification_content){ this.notification_content = notification_content; }
    public void setNotificationSessionId(String notification_session_id){ this.notification_session_id = notification_session_id; }
    public void setNotificationDateTime(String notification_date_time){ this.notification_date_time = notification_date_time; }

    public String getNotificationId(){ return this.notification_id; }
    public String getNotificationType(){ return this.notification_type; }
    public String getNotificationStatus(){ return this.notification_status; }
    public String getNotificationContent(){ return this.notification_content; }
    public String getNotificationSessionId(){ return this.notification_session_id; }
    public String getNotificationDateTime(){ return this.notification_date_time; }

    public void add_notification() throws CustomException{
        try{
            this.notification_status="UNREAD";
            this.notification_date_time = JDateTime.getCurrentDateTime();
            String sql = "INSERT INTO notification (notification_type, notification_status, notification_content, notification_session_id, notification_date_time) values (?,?,?,?,?)";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.notification_type);
            pt.setString(2, this.notification_status);
            pt.setString(3, this.notification_content);
            pt.setString(4, this.notification_session_id);
            pt.setString(5, this.notification_date_time);

            pt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new CustomException("Fail to notify restaurant");
        }
    }



}
