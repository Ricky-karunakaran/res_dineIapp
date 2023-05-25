/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order.Model;

import com.utils.CustomException;
import com.utils.JDateTime;
import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class WaiterCall {
    private String waiter_call_id;
    private String waiter_call_status;
    private String waiter_call_content;
    private String waiter_call_createdAt;

    private String waiter_call_session_id;
    private String waiter_call_restaurant_id;
    private String waiter_call_user_email;


    public void setWaiterCallId(String waiter_call_id) {this.waiter_call_id = waiter_call_id; }
    public void setWaiterCallStatus(String waiter_call_status) {this.waiter_call_status = waiter_call_status;}
    public void setWaiterCallContent(String waiter_call_content) {this.waiter_call_content = waiter_call_content;}
    public void setWaiterCallSessionId(String waiter_call_session_id) {this.waiter_call_session_id = waiter_call_session_id;}
    public void setWaiterCallRestaurantId(String waiter_call_restaurant_id) {this.waiter_call_restaurant_id = waiter_call_restaurant_id;}
    public void setWaiterCallUserEmail(String waiter_call_user_email) {this.waiter_call_user_email = waiter_call_user_email;}
    public void setWaiterCallCreatedAt(String waiter_call_createdAt) {this.waiter_call_createdAt = waiter_call_createdAt; }

    public String getWaiterCallId() {return this.waiter_call_id;}
    public String getWaiterCallStatus(){return this.waiter_call_status;}
    public String getWaiterCallContent() { return this.waiter_call_content; }
    public String getWaiterCallCreatedAt() {return this.waiter_call_createdAt;}
    public String getWaiterCallSessionId() { return this.waiter_call_session_id; }
    public String getWaiterCallRestaurantId() { return this.waiter_call_restaurant_id; }
    public String getWaiterCallUserEmail() { return this.waiter_call_user_email; }

    public void add_new_waiter_call ()  throws CustomException{
        try{
            this.waiter_call_status = "UNREAD";
            this.waiter_call_createdAt = JDateTime.getCurrentDateTime();
            Connection con = dbConnection.getDb();
            String sql = "INSERT INTO waiter_call (waiter_call_status,waiter_call_content,waiter_call_createdAt,waiter_call_session_id,waiter_call_restaurant_id,waiter_call_user_email) Values (?,?,?,?,?,?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,this.waiter_call_status);
            pt.setString(2,this.waiter_call_content);
            pt.setString(3,this.waiter_call_createdAt);
            pt.setString(4, this.waiter_call_session_id);
            pt.setString(5,this.waiter_call_restaurant_id);
            pt.setString(6,this.waiter_call_user_email);
            pt.executeUpdate();
        } catch(Exception e){
            throw new CustomException("Error to call waiter");
        }
    }

    public boolean check_unread_call() throws CustomException{
        try{
            String sql = "SELECT * from waiter_call WHERE waiter_call_status = 'UNREAD' AND waiter_call_restaurant_id = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.waiter_call_restaurant_id);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                return true;
            } else {
                return false;
            }

        } catch(Exception e){
            throw new CustomException("Fail to get unread call");
        }
    }

    public void update_waiter_call() throws CustomException{
        try{
            String sql ="UPDATE waiter_call set waiter_call_status = ? WHERE waiter_call_id = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.waiter_call_status);
            pt.setString(2, this.waiter_call_id);
            int result = pt.executeUpdate();
        } catch(Exception e) {
            throw new CustomException("Fail to update call");
        }
    }

    public ArrayList<WaiterCall> read_all_waiter_calls() throws CustomException{
        ArrayList<WaiterCall> waiter_calls = new ArrayList<WaiterCall>();
        try{
            String sql = "SELECT * from waiter_call WHERE waiter_call_status = 'UNREAD' AND waiter_call_restaurant_id = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.waiter_call_restaurant_id);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                WaiterCall waiter_call = new WaiterCall();
                waiter_call.setWaiterCallId(rs.getString("waiter_call_id"));
                waiter_call.setWaiterCallContent(rs.getString("waiter_call_content"));
                waiter_call.setWaiterCallStatus(rs.getString("waiter_call_status"));
                waiter_call.setWaiterCallCreatedAt((rs.getString("waiter_call_createdAt")));
                waiter_call.setWaiterCallSessionId(rs.getString("waiter_call_session_id"));
                waiter_call.setWaiterCallRestaurantId(rs.getString("waiter_call_restaurant_id"));
                waiter_call.setWaiterCallUserEmail(rs.getString("waiter_call_user_email"));
                waiter_calls.add(waiter_call);
            }
            return waiter_calls;
        } catch(Exception e){
            throw new CustomException("Fail to get waiter call");
        }
    }
}
