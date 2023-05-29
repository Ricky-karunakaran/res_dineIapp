/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Ricky
 */
public class Notification {
    private String notification_id;
    private String notification_type;
    private String notification_status;
    private String notification_content;
    private String notification_session_id;
    private String notification_date_time;


    //Joined Data
    private String notification_restaurant_id;
    private String notification_user_name;

    //Setter
    public void setNotificationId(String notification_id){ this.notification_id = notification_id; }
    public void setNotificationType(String notification_type){ this.notification_type = notification_type; }
    public void setNotificationStatus(String notification_status){ this.notification_status = notification_status; }
    public void setNotificationContent(String notification_content){ this.notification_content = notification_content; }
    public void setNotificationSessionId(String notification_session_id){ this.notification_session_id = notification_session_id; }
    public void setNotificationDateTime(String notification_date_time){ this.notification_date_time = notification_date_time; }
    public void setNotificationRestaurantId(String notification_restaurant_id) { this.notification_restaurant_id = notification_restaurant_id; }
    
    public void setNotificationUserName(String notification_user_name) { this.notification_user_name = notification_user_name;}
    public String getNotificationId(){ return this.notification_id; }
    public String getNotificationType(){ return this.notification_type; }
    public String getNotificationStatus(){ return this.notification_status; }
    public String getNotificationContent(){ return this.notification_content; }
    public String getNotificationSessionId(){ return this.notification_session_id; }
    public String getNotificationDateTime(){ return this.notification_date_time; }
    
    public String getNotificationUserName() {return this.notification_user_name;}
    
    public ArrayList<Notification> get_all_notification() throws Exception{
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        try{
            String sql = "SELECT * from notification Inner join session on notification.notification_session_id = session.session_id Inner join user on session.session_user_id = user.user_email WHERE  session.session_restaurant_id=? ORDER BY notification.notification_date_time DESC ;";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.notification_restaurant_id);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                Notification notification = new Notification();
                notification.setNotificationId(rs.getString("notification_id"));
                notification.setNotificationDateTime(rs.getString("notification_date_time"));
                notification.setNotificationSessionId(rs.getString("notification_session_id"));
                notification.setNotificationContent(rs.getString("notification_content"));
                notification.setNotificationType(rs.getString("notification_type"));
                notification.setNotificationStatus(rs.getString("notification_status"));
                notification.setNotificationUserName(rs.getString("user.user_name"));
                notifications.add(notification);
            }
            con.close();
            this.batch_update_notification_status(notifications);
            
            return notifications;
        }catch(Exception e){
            System.out.println(e);
            throw new Exception("Error to read notification");
        }
    }
    public void update_notification_status() throws Exception{
        try{
            String sql = "UPDATE notification set notification_status = 'SEEN' WHERE notification_id = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.notification_id);
            pt.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
            throw new Exception("Error to update notification status");
        }
    }
    public void batch_update_notification_status(ArrayList<Notification> notifications){
        for(int i=0;i<notifications.size();i++){
            try{
                Notification notification = notifications.get(i);
                notification.update_notification_status();
            } catch(Exception e){
            
            }
        }
    }
}
