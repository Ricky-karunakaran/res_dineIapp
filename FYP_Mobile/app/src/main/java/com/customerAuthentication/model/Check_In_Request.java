package com.customerAuthentication.model;

import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Check_In_Request {
    private String check_in_request_id;
    private String check_in_user_email;
    private String check_in_restaurant_id;
    private String check_in_request_date_time;

    public void setCheckInRestaurantId(String check_in_restaurant_id) { this.check_in_restaurant_id = check_in_restaurant_id;}
    public void setCheckInUserEmail(String check_in_user_email) { this.check_in_user_email = check_in_user_email;};
    public void setCheckInRequestDateTime (String check_in_request_date_time) { this.check_in_request_date_time = check_in_request_date_time; }

    public void create_check_in_request(){
        try {
            Connection con = dbConnection.getDb();
            String sql = "INSERT INTO check_in_request (check_in_user_email,check_in_restaurant_id,check_in_request_date_time) VALUES (?,?,?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,this.check_in_user_email);
            pt.setString(2,this.check_in_restaurant_id);
            pt.setString(3, this.check_in_request_date_time);
            pt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
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
