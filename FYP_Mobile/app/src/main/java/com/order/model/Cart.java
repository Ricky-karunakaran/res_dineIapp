package com.order.model;

import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Cart {

    private String cart_id;
    private String cart_session_id;
    private ArrayList<CartItem> cart_items;


    public void setCartSessionId(String sessionId) { this.cart_session_id = sessionId; }
    public void setCartId(String cart_id) { this.cart_id = cart_id; }

    public String getCartId(){return this.cart_id;}
    public ArrayList<CartItem> getCartItems(){
        return this.cart_items;
    }

    public void add_cart(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "INSERT INTO cart (cart_session_id) VALUES(?)";
            PreparedStatement pt =con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1, this.cart_session_id);
            int affedctedRows = pt.executeUpdate();
            if(affedctedRows == 0 ){
                throw new Exception("Failed to check in");
            }

            try(ResultSet generatedKeys = pt.getGeneratedKeys()){
                System.out.println("Cart_ID");
                if(generatedKeys.next()){
                    System.out.println("Cart_ID");
                    this.cart_id = String.valueOf(generatedKeys.getInt(1));
                } else {
                    throw new Exception("Fail to check in");
                }
            }

        } catch(Exception e){

        }
    }

    public void read_all_cart_item(){
        CartItem cartItem = new CartItem();
        cartItem.setCartId(this.cart_id);
        this.cart_items = cartItem.get_all_cart_item();
    }
}
