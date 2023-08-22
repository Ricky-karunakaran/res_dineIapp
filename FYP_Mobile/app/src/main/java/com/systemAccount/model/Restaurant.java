package com.systemAccount.model;


import com.utils.CustomException;
import com.utils.Model;
import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Restaurant extends Model {
    private String restaurant_email;
    private String restaurant_name;
    private String restaurant_location;
    private String restaurant_id;
    private String restaurant_operation_hours;

    public Restaurant(){};

    public void setEmail(String email){ this.restaurant_email = email; }

    public void setName(String name){ this.restaurant_name = name; }

    public void setLocation(String location) { this.restaurant_location = location; }

    public void setOperationHours(String operationHour) { this.restaurant_operation_hours = operationHour; }
    public void setRestaurantId(String restaurant_id) { this.restaurant_id = restaurant_id;}

    public String getEmail(){ return this.restaurant_email; }

    public String getName(){ return this.restaurant_name; }

    public String getLocation(){ return this.restaurant_location; }

    public String getOperationHours(){ return this.restaurant_operation_hours; }

    public String getRestaurantId() { return this.restaurant_id;}

    public static void find_restaurant(String restaurant_id) throws Exception {
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT restaurant_id from restaurant where restaurant_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,restaurant_id);
            ResultSet rt = pt.executeQuery();
            if(rt.next()){
                System.out.println(rt.getString(1));
            } else {
                throw new CustomException("No Restaurant can be found.");
            }
        } catch (CustomException e ){
            throw e;

        }catch (Exception e){
            throw new Exception("System error, fail to check restaurant information.");
        }
    }

    public void read_restaurant_by_id() throws Exception {
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT restaurant_id,restaurant_name from restaurant where restaurant_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,this.restaurant_id);
            ResultSet rt = pt.executeQuery();
            if(rt.next()){
                this.restaurant_name = rt.getString("restaurant_name");
            } else {
                throw new CustomException("No Restaurant found");
            }
        } catch (CustomException e ){
            throw e;

        } catch (Exception e){
            System.out.println(e);
            throw new Exception("System error, fail to check restaurant infomation");
        }
    }
    public static ArrayList<Restaurant> getAll(){
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String command ="SELECT restaurant_name, restaurant_email from restaurant";
            ResultSet rt = st.executeQuery(command);
            ArrayList<Restaurant> restaurant_list = new ArrayList<Restaurant>();
            while(rt.next()){
                Restaurant restaurant = new Restaurant();
                restaurant.setEmail(rt.getString("restaurant_email"));
                restaurant.setName(rt.getString("restaurant_name"));
                restaurant_list.add(restaurant);
            }
            return restaurant_list;
        } catch (Exception e){
        }
        return null;
    }

}
