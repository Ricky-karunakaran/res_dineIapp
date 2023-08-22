package com.customerAuthentication.model;

import com.order.model.Bill;
import com.order.model.Cart;
import com.utils.CustomException;
import com.utils.JDateTime;
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
    private String session_table_id;



    public void setSessionStatus(String session_status){ this.session_status = session_status; }
    public void setSessionStartTime(String session_start_time) {this.session_start_time = session_start_time; }
    public void setSessionEndTime(String session_end_time) { this.session_end_time = session_end_time; }
    public void setSessionId(String session_id) { this.session_id = session_id; }
    public void setSessionTableId(String session_table_id) { this.session_table_id = session_table_id; }

    public String getSessionId() { return this.session_id; }
    public String getSessionRestaurantId() { return this.session_restaurant_id; }
    public String getSessionCartId() {return this.session_cart_id; }
    public String getSessionBillId() {return this.session_bill_id; }
    public String getSessionStartTime() { return this.session_start_time;}
    public Dine_In_Session(String session_user_email, String session_restaurant_id,String session_table_id) throws Exception {
        this.session_user_email = session_user_email;
        this.session_restaurant_id = session_restaurant_id;
        this.session_table_id = session_table_id;
        try{
            Connection con = dbConnection.getDb();
            String sql = "INSERT INTO session (session_user_id,session_restaurant_id,session_status,session_start_time,session_table_id) VALUES (?,?,'OPEN',?,?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1,this.session_user_email);
            pt.setString(2,this.session_restaurant_id);
            pt.setString(3, JDateTime.getCurrentDateTime());
            pt.setString(4,this.session_table_id);

            int affedctedRows = pt.executeUpdate();
            if(affedctedRows == 0 ){
                throw new Exception("Failed to check in");
            }

            try(ResultSet generatedKeys = pt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    this.session_id = String.valueOf(generatedKeys.getInt(1));
                    // create cart
                    Cart cart = new Cart();
                    cart.setCartSessionId(this.session_id);
                    cart.add_cart();
                    String cart_id = cart.getCartId();
                    this.session_cart_id = cart_id;

                    //create Bill
                    Bill bill = new Bill();
                    bill.setBillSessionId(this.session_id);
                    bill.create_bill();
                    String bill_id = bill.getBillId();
                    this.session_bill_id = bill_id;

                    sql = "UPDATE  session set session_cart_id = ?, session_bill_id = ?  WHERE session_id = ?";
                    PreparedStatement ptUpdate = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ptUpdate.setString(1,cart_id);
                    ptUpdate.setString(2,bill_id);
                    ptUpdate.setString(3,this.session_id);
                    ptUpdate.executeUpdate();

                } else {
                    throw new Exception("Fail to check in");
                }
            }




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
                this.session_cart_id = rs.getString("session_cart_id");
                this.session_bill_id = rs.getString("session_bill_id");
            }
        } catch (SQLException e) {
            throw new CustomException("System Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void update_session_status_by_id() throws CustomException{
        try{
            Connection con = dbConnection.getDb();
            String sql = "Update session set session_status = ?  WHERE session_id = ?";

            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.session_status);
            pt.setString(2,this.session_id);
            pt.executeUpdate();

        } catch (SQLException e) {
            throw new CustomException("System Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void create_cart(){}
    private void create_bill(){}
}
