package com.customerAuthentication.model;

import com.utils.CustomException;
import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dine_In_Session {

    private String session_id;
    private String session_status;
    private String session_start_time;
    private String session_end_time;

    private String session_user_email;
    private String session_restaurant_id;
    private String session_cart_id;
    private String session_bill_id;



    public void setSessionStatus(String session_status){ this.session_status = session_status; }
    public void setSessionStartTime(String session_start_time) {this.session_start_time = session_start_time; }
    public void setSessionEndTime(String session_end_time) { this.session_end_time = session_end_time; }
    public void setSessionId(String session_id) { this.session_id = session_id; }

    public String getSessionId() { return this.session_id; }
    public String getSessionRestaurantId() { return this.session_restaurant_id; }

    public Dine_In_Session(String session_user_email, String session_restaurant_id) throws Exception {
        this.session_user_email = session_user_email;
        this.session_restaurant_id = session_restaurant_id;

        try{
            Connection con = dbConnection.getDb();
            String sql = "INSERT INTO session (session_user_id,session_restaurant_id) VALUES (?,?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1,this.session_user_email);
            pt.setString(2,this.session_restaurant_id);

            int affedctedRows = pt.executeUpdate();
            if(affedctedRows == 0 ){
                throw new Exception("Failed to check in");
            }

            try(ResultSet generatedKeys = pt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    this.session_id = String.valueOf(generatedKeys.getInt(1));
                } else {
                    throw new Exception("Fail to check in");
                }
            }

            // create cart



        } catch(Exception e){
            throw e;
        }


    }
    public Dine_In_Session(){}

    // read dine in session based on id
    public void read_session_by_id() throws CustomException {
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * FROM session WHERE session_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,this.session_id);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                this.session_restaurant_id = rs.getString("session_restaurant_id");
            }
        } catch (SQLException e) {
            throw new CustomException("System Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
