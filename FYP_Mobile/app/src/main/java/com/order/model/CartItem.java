package com.order.model;

import com.utils.dbConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItem {
    private String cart_id;
    private String cart_item_id;

    private String cart_item_menu_id;
    private int cart_item_quantity;
    private double cart_item_unit_price;
    private String cart_item_status;
    private String cart_item_name;

    public void setCartId(String cart_id){ this.cart_id = cart_id; }
    public void setCartItemId(String cart_item_id){ this.cart_item_id = cart_item_id; }
    public void setCartItemName(String cart_item_name) {this.cart_item_name = cart_item_name; }
    public void setCartItemQuantity(int cart_item_quantity) { this.cart_item_quantity = cart_item_quantity; }
    public void setCartItemUnitPrice(double cart_item_unit_price) { this.cart_item_unit_price = cart_item_unit_price; }
    public void setCartItemMenuId(String cart_item_menu_id) { this.cart_item_menu_id = cart_item_menu_id; }
    public void setCartItemStatus(String cart_item_status) { this.cart_item_status = cart_item_status;}


    public String getCartItemName () { return this.cart_item_name; }
    public int getCartItemQuantity() {return this.cart_item_quantity; }
    public double getCartItemUnitPrice() {return this.cart_item_unit_price; }
    public String getCartItemId() {return this.cart_item_id; }
    public String getCartItemStatus() { return this.cart_item_status; }

    public void add_cart_item() {
        try{
            Connection con = dbConnection.getDb();
            String preSql = "SELECT * FROM cart_item where cart_id = ? AND cart_item_menu_id = ? AND cart_item_status = 'ORDERED'";
            PreparedStatement prePt = con.prepareStatement(preSql);
            prePt.setString(1,this.cart_id);
            prePt.setString(2,this.cart_item_menu_id);
            ResultSet preResult = prePt.executeQuery();
            if(preResult.next()) {
                this.cart_item_id = preResult.getString("cart_item_id");
                this.increase_cart_item_quantity();
            } else {
                String sql = "INSERT INTO cart_item (cart_id, cart_item_menu_id, cart_item_name, cart_item_quantity, cart_item_unit_price, cart_item_status) VALUES (?,?,?,?,?,?)";
                PreparedStatement pt = con.prepareStatement(sql);
                pt.setString(1, this.cart_id);
                pt.setString(2, this.cart_item_menu_id);
                pt.setString(3, this.cart_item_name);
                pt.setInt(4, 1);
                pt.setDouble(5, this.cart_item_unit_price);
                pt.setString(6,"ORDERED");
                pt.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public  ArrayList<CartItem> get_all_cart_item(){
        ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
        try{

            Connection con = dbConnection.getDb();
            String sql = "SELECT *, SUM(cart_item_quantity) as cart_item_quantity FROM cart_item WHERE cart_id = ? AND cart_item_status = 'ORDERED' AND cart_item_quantity != 0   GROUP BY cart_item_menu_id";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.cart_id);
            ResultSet rs  = pt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("cart_item_name"));
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(rs.getString("cart_item_id"));
                cartItem.setCartItemName(rs.getString("cart_item_name"));
                cartItem.setCartItemQuantity(rs.getInt("cart_item_quantity"));
                cartItem.setCartItemUnitPrice(rs.getInt("cart_item_unit_price"));
                cartItem.setCartItemStatus(rs.getString("cart_item_status"));
                cartItems.add(cartItem);
            }
        } catch (Exception e){
                System.out.println(e);
        }
        return cartItems;

    }

    public void submit_cart_item(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "Update  cart_item SET cart_item_status = 'SUBMITTED' WHERE cart_item_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.cart_item_id);
            pt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void increase_cart_item_quantity(){
        ;
        try{
            Connection con = dbConnection.getDb();
            String sql = "UPDATE cart_item SET cart_item_quantity = cart_item_quantity + 1 WHERE cart_item_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.cart_item_id);
            pt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void decrease_cart_item_quantity(){
        ;
        try{
            Connection con = dbConnection.getDb();
            String sql = "UPDATE cart_item SET cart_item_quantity = cart_item_quantity -1 WHERE cart_item_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.cart_item_id);
            pt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void remove_cart_item(){
        ;
        try{
            Connection con = dbConnection.getDb();
            String sql = "DELETE FROM cart_item WHERE cart_item_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.cart_item_id);
            pt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
