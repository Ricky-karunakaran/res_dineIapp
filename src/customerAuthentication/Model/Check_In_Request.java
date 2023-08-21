/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customerAuthentication.Model;

import com.utils.FileLogger;
import com.utils.JDateTime;
import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Ricky
 */
public class Check_In_Request {
    private String check_in_request_id;
    private String check_in_user_email;
    private String check_in_restaurant_id;
    private String check_in_request_date_time;
    private String check_in_request_status;

    public void setCheckInRestaurantId(String check_in_restaurant_id) { this.check_in_restaurant_id = check_in_restaurant_id;}
    public void setCheckInUserEmail(String check_in_user_email) { this.check_in_user_email = check_in_user_email;};
    public void setCheckInRequestDateTime (String check_in_request_date_time) { this.check_in_request_date_time = check_in_request_date_time; }
    public void setCheckInRequestId(String check_in_request_id) { this.check_in_request_id = check_in_request_id;}
    public void setCheckInRequestStatus(String check_in_request_status) { this.check_in_request_status = check_in_request_status;}
    
    public String getCheckInUserEmail() { return this.check_in_user_email;}
    public String getCheckInRequestDateTime() {return this.check_in_request_date_time;}

    public ArrayList<Check_In_Request> getCheckInRequests(){
        ArrayList<Check_In_Request> requests = new ArrayList<Check_In_Request>();
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * FROM check_in_request Where check_in_restaurant_id = ? AND check_in_request_status IS NULL";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.check_in_restaurant_id);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                
                Check_In_Request request = new Check_In_Request();
                request.setCheckInRequestId(rs.getString("check_in_request_id"));
                request.setCheckInUserEmail(rs.getString("check_in_user_email"));
                request.setCheckInRequestDateTime(rs.getString("check_in_request_date_time"));
                request.setCheckInRestaurantId(rs.getString("check_in_restaurant_id"));
                requests.add(request);
            }
            con.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return requests;
        
    }
    
    public void update_check_in_request(){
        try{
            Connection con =  dbConnection.getDb();
            String sql = "Update check_in_request SET check_in_request_status = ? WHERE check_in_request_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.check_in_request_status);
            pt.setString(2, this.check_in_request_id);
            pt.execute();
            con.close();
        } catch(Exception e){
            FileLogger.logFile(e.getMessage());
        }
    }
    public void close_check_in_request(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * FROM check_in_request WHERE check_in_user_email = ? ORDER BY check_in_request_date_time DESC LIMIT 1";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.check_in_user_email);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                this.check_in_request_id = rs.getString("check_in_request_id");
                sql = "Update check_in_request Set check_in_request_status = 'COMPLETED' WHERE check_in_request_id = ?";
                pt = con.prepareStatement(sql);
                pt.setString(1,this.check_in_request_id);
                pt.executeUpdate();
            }

            con.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
