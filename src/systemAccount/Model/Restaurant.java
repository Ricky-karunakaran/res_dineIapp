/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Model;

import com.utils.CustomException;
import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.utils.Model;
import java.sql.PreparedStatement;
import reporting.Model.Sale_Report;
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
            if(rs.next()){
                throw new CustomException("The email has been registered");
            } else {
                st.execute(String.format("Insert into restaurant (restaurant_email, restaurant_name, restaurant_password) Values ('%s','%s','%s')",this.restaurant_email,this.restaurant_name,password));
                con.close();
            }
            return true;
        } catch (Exception e){
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
    
    public ArrayList<Sale_Report> generate_sales_report(String dateFrom, String dateTo){
        String sql =    "SELECT\n" +
                        "restaurant.restaurant_id,\n" +
                        "bill_item.bill_item_name,\n" +
                        "bill_item.bill_item_quantity,\n" +
                        "bill_item.bill_item_unit_price,\n" +
                        "bill_item.bill_id,\n" +
                        "SUM(bill_item.bill_item_quantity) as total_quantity,\n" +
                        "SUM(bill_item.bill_item_quantity*bill_item.bill_item_unit_price) as total_sales,\n" +
                        "bill.bill_id,\n" +
                        "session.session_restaurant_id,\n" +
                        "session.session_bill_id,\n" +
                        "user.user_email,\n" +
                        "user.user_age\n" +
                        "from restaurant\n" +
                        "INNER JOIN session on session.session_restaurant_id = restaurant.restaurant_id\n" +
                        "INNER JOIN bill on bill.bill_id = session.session_bill_id\n" +
                        "INNER JOIN user on session.session_user_id = user.user_email\n" +
                        "INNER JOIN bill_item on bill_item.bill_id = bill.bill_id\n" +
                        "WHERE restaurant.restaurant_email = ? AND session.session_start_time <= ? AND session.session_start_time >= ?\n" +
                        "GROUP BY bill_item.bill_item_name";
        ArrayList<Sale_Report> sales = new ArrayList<Sale_Report>();
        try{
            Connection con = dbConnection.getDb();
            PreparedStatement pt= con.prepareStatement(sql);
            pt.setString(1,this.restaurant_email);
            pt.setString(2, dateTo);
            pt.setString(3, dateFrom);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                Sale_Report sale= new Sale_Report();
                System.out.println("GET ! DATA");
                sale.setItem(rs.getString("bill_item_name"));
                sale.setQuantity(rs.getString("total_quantity"));
                sale.setSales(rs.getString("total_sales"));
                sales.add(sale);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return sales;
    }
    
    public ArrayList<Sale_Report> generate_specific_sales_report(String dateFrom, String dateTo, int ageFrom, int ageTo){
        String sql =    "SELECT\n" +
                        "restaurant.restaurant_id,\n" +
                        "bill_item.bill_item_name,\n" +
                        "bill_item.bill_item_quantity,\n" +
                        "bill_item.bill_item_unit_price,\n" +
                        "bill_item.bill_id,\n" +
                        "SUM(bill_item.bill_item_quantity) as total_quantity,\n" +
                        "SUM(bill_item.bill_item_quantity*bill_item.bill_item_unit_price) as total_sales,\n" +
                        "bill.bill_id,\n" +
                        "session.session_restaurant_id,\n" +
                        "session.session_bill_id,\n" +
                        "user.user_email,\n" +
                        "user.user_age\n" +
                        "from restaurant\n" +
                        "INNER JOIN session on session.session_restaurant_id = restaurant.restaurant_id\n" +
                        "INNER JOIN bill on bill.bill_id = session.session_bill_id\n" +
                        "INNER JOIN user on session.session_user_id = user.user_email\n" +
                        "INNER JOIN bill_item on bill_item.bill_id = bill.bill_id\n" +
                        "WHERE restaurant.restaurant_email = ? AND session.session_start_time <= ? AND session.session_start_time >= ? AND user.user_age>=? AND user.user_age<=?\n" +
                        "GROUP BY bill_item.bill_item_name";
        ArrayList<Sale_Report> sales = new ArrayList<Sale_Report>();
        try{
            Connection con = dbConnection.getDb();
            PreparedStatement pt= con.prepareStatement(sql);
            pt.setString(1,this.restaurant_email);
            pt.setString(2, dateTo);
            pt.setString(3, dateFrom);
            pt.setInt(4,ageFrom);
            pt.setInt(5,ageTo);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                Sale_Report sale= new Sale_Report();
                System.out.println("GET ! DATA");
                sale.setItem(rs.getString("bill_item_name"));
                sale.setQuantity(rs.getString("total_quantity"));
                sale.setSales(rs.getString("total_sales"));
                sales.add(sale);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return sales;
    }
    
}
