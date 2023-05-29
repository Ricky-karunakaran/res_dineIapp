/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.utils.Model;
import java.sql.PreparedStatement;
/**
 *
 * @author Ricky
 */
public class Restaurant extends Model implements Account {
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
    
    public String getEmail(){ return this.restaurant_email; }
    
    public String getName(){ return this.restaurant_name; }
    
    public String getLocation(){ return this.restaurant_location; }
    
    public String getOperationHours(){ return this.restaurant_operation_hours; }
    
    public String getRestaurantId() { return this.restaurant_id;}
    @Override
    public boolean login(String password) throws Exception {
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            System.out.println(String.format("SELECT restaurant_email,restaurant_password from restaurant where restaurant_email='%s' AND restaurant_password ='%s'",this.restaurant_email,password));
            ResultSet rt = st.executeQuery(String.format("SELECT * from restaurant where restaurant_email='%s' AND restaurant_password ='%s'",this.restaurant_email,password));
            if(rt.next()){
                this.restaurant_id = rt.getString("restaurant_id");
                this.restaurant_name = rt.getString("restaurant_name");
                this.restaurant_location = rt.getString("restaurant_location");
                this.restaurant_operation_hours = rt.getString("restaurant_operation_hours");
                System.out.println(this.restaurant_id);
                System.out.println("Login Success");
                return true;
            } else {
                System.out.println("Login Fail");
                return false;
            }
        } catch (Exception e){
            System.out.println(e.toString());
            throw e;
        }
    }

    @Override
    public boolean register(String password) throws Exception {
        try {
            Connection con= dbConnection.getDb();
            Statement st=con.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT restaurant_email from restaurant WHERE restaurant_email ='%s'",this.restaurant_email));
            System.out.println(String.format("SELECT restaurant_email from restaurant WHERE restaurant_email ='%s'",this.restaurant_email));
            if(rs.next()){
                throw new Exception("Account exist");
            } else {
                st.execute(String.format("Insert into restaurant (restaurant_email, restaurant_name, restaurant_password) Values ('%s','%s','%s')",this.restaurant_email,this.restaurant_name,password));
                con.close();
            }
            
            return true;
        } catch (Exception e){
            System.out.println(e);
            throw e;

        }    
    }

    @Override
    public void edit_info() throws Exception {
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String command = String.format(
                    "UPDATE restaurant SET restaurant_name ='%s',restaurant_location = '%s', restaurant_operation_hours = '%s' WHERE restaurant_email = '%s'",
                    this.restaurant_name,
                    this.restaurant_location,
                    this.restaurant_operation_hours,
                    this.restaurant_email);
            System.out.println(command);
            st.execute(command);
            con.close();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public boolean reset_password(String email, String password) {
        try{
            String sql = "UPDATE restaurant set restaurant_password = ? WHERE restaurant_email = ?";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, password);
            pt.setString(2, email);
            pt.execute();
            return true;
        } catch (Exception e){
            
        }
        return false;
    }
    
    public void refreshData(){
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            ResultSet rt = st.executeQuery(String.format("SELECT * from restaurant where restaurant_email='%s' ",this.restaurant_email));
            if(rt.next()){
                this.restaurant_id = rt.getString("restaurant_id");
                this.restaurant_name = rt.getString("restaurant_name");
                this.restaurant_location = rt.getString("restaurant_location");
                this.restaurant_operation_hours = rt.getString("restaurant_operation_hours").replaceAll(",", "\n");
            }
        } catch (Exception e){
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
    
    public void deleteRestaurant() throws Exception{
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String command =String.format("DELETE from restaurant WHERE restaurant_email='%s'",this.restaurant_email);
            st.execute(command);
            con.close(); 
        } catch (Exception e){
            throw  new Exception("Fail to delete restaurant");
        }
    }
    
}
