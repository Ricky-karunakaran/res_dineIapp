/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemAccount.Model;

import com.utils.dbConnection;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Ricky
 */
public class User implements Account {
    private String user_email;
    private String user_name;
    private String user_phone_number;
    private int user_age;
    private Image user_profile_image;
    private String user_active_session;
    private String user_allergy;
    
    
    public User(){};
    public User(String email, String name,String allergy,String phone,int age, Image profile ) {
        this.user_email = email;
        this.user_name = name;
        this.user_allergy = allergy;
        this.user_phone_number = phone;
        this.user_age = age;
        this.user_profile_image = profile;
    }
    
    public User(String email){
        this.user_email = email;
    }
    public int getUserAge(){
        return this.user_age;
    }
    public String getUserEmail(){
        return this.user_email;
    }
    public void setUserName(String user_name){
        this.user_name = user_name;
    }
    
    public void setUserEmail(String user_email){ this.user_email = user_email;}
    
    @Override
    public boolean login(String password) throws Exception {
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            
            ResultSet rt = st.executeQuery(String.format("SELECT user_email,user_password from user where user_email='%s' AND user_password ='%s'",this.user_email,password));
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
            ResultSet rs = st.executeQuery(String.format("SELECT user_email from user WHERE user_email ='%s'",this.user_email));
            if(rs.next()){
                throw new Exception("Account exist");
            } else {
                st.execute(String.format("Insert into user (user_email) Values ('%s')",this.user_email));
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
    
    public void check_out(String user_active_session){
         try {
            Connection con= dbConnection.getDb();
            String sql = "Update User SET user_active_session = NULL WHERE user_active_session = ? ";
            PreparedStatement pt=con.prepareCall(sql);
            System.out.println("user session "+user_active_session);
            pt.setString(1,user_active_session);
            pt.execute();
 
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void check_out_by_email(){
        try {
            Connection con= dbConnection.getDb();
            String sql = "Update User SET user_active_session = NULL WHERE user_email = ? ";
            PreparedStatement pt=con.prepareCall(sql);
            pt.setString(1,this.user_email);
            pt.execute();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
}
