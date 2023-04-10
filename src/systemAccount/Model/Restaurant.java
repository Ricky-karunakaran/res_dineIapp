/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Ricky
 */
public class Restaurant implements Account{
    
    private String restaurant_email;
    private String restaurant_name;
    private String restaurant_location;
    private String restaurant_id;
    private String[] restaurant_operation_hours;
    
    public Restaurant(){};
    
    public void setEmail(String email){
        this.restaurant_email = email;
    }
    public void setName(String name){
        this.restaurant_name = name;
    }

    @Override
    public boolean login(String password) throws Exception {
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            System.out.println(String.format("SELECT restaurant_email,restaurant_password from restaurant where restaurant_email='%s' AND restaurant_password ='%s'",this.restaurant_email,password));
            ResultSet rt = st.executeQuery(String.format("SELECT restaurant_email,restaurant_password from restaurant where restaurant_email='%s' AND restaurant_password ='%s'",this.restaurant_email,password));
            if(rt.next()){
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
    public boolean register(String password) {
        try {
            Connection con= dbConnection.getDb();
            Statement st=con.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT restaurant_email from restaurant WHERE restaurant_email ='%s'",this.restaurant_email));
            if(rs.next()){
                throw new Exception("Account exist");
            } else {
                st.execute(String.format("Insert into restaurant (restaurant_email, restaurant_password, restaurant_name) Values ('%s','%s','%s')",this.restaurant_email,this.restaurant_name,password));
            }
            
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }    
    }

    @Override
    public void edit_info() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean reset_password(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
