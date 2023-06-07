package com.systemAccount.model;


import android.media.Image;

import com.reporting.model.Consumption;
import com.reporting.model.HistoryVisit;
import com.utils.CustomException;
import com.utils.DbBackgroundTask;
import com.utils.dbConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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



    // setter
    public void setUserEmail(String user_email) { this.user_email= user_email; }
    public void setUserName(String user_name){
        this.user_name = user_name;
    }
    public void setUserAge(int user_age) { this.user_age = user_age; }
    public void setUserPhone(String user_phone_number) { this.user_phone_number = user_phone_number; }
    public void setUserAllergy(String user_allergy) { this.user_allergy = user_allergy; }

    // getter
    public String getUserEmail(){
        return this.user_email;
    }
    public String getUserName() { return this.user_name; }
    public int getUserAge(){
        return this.user_age;
    }
    public String getUserPhoneNumber() { return this.user_phone_number; }
    public String getUserActiveSession() { return this.user_active_session; }

    @Override
    public boolean login(String password) throws Exception {
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String command = String.format("SELECT * from user where user_email='%s' AND user_password ='%s'",this.user_email,password);
            System.out.println(command);
            ResultSet rt = st.executeQuery(command);
            if(rt.next()){
                this.user_name = rt.getString("user_name");
                this.user_age = rt.getInt("user_age");
                this.user_phone_number = rt.getString("user_phone_number");
                this.user_active_session = rt.getString("user_active_session");
                con.close();
                return true;
            } else {
                con.close();
                throw new CustomException("Wrong Password or email");
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
            ResultSet rs = st.executeQuery(String.format("SELECT user_email from user WHERE user_email ='%s'",this.user_email));
            String command="";
            if(rs.next()){
                throw new CustomException("Account exist");
            } else {

                command=String.format(
                        "Insert into user (user_email,user_password,user_name,user_age,user_phone_number) Values ('%s','%s','%s','%s','%s')",
                        this.user_email,
                        password,
                        this.user_name,
                        this.user_age,
                        this.user_phone_number
                );
                st.execute(command);
            }
            con.close();
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public void edit_info() {
        try {
            Connection con= dbConnection.getDb();
            Statement st=con.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT user_email from user WHERE user_email ='%s'",this.user_email));
            String command="";
            if(!rs.next()){
                throw new Exception("Not Account found");
            } else {

                command=String.format(
                        "UPDATE  user SET user_name = '%s', user_phone_number ='%s', user_age = '%s', user_allergy = '%s' WHERE user_email ='%s'",
                        this.user_name,
                        this.user_phone_number,
                        Integer.toString(this.user_age),
                        this.user_allergy,
                        this.user_email
                );
                st.execute(command);
            }
            con.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public boolean reset_password(String email, String password) {
        try {
            Connection con= dbConnection.getDb();
            String sql = "UPDATE user set user_password = ? WHERE user_email = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,password);
            pt.setString(2, email);
            pt.executeUpdate();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean check_active_session() throws SQLException, ClassNotFoundException {
        try {
                Connection con= dbConnection.getDb();
                Statement st=con.createStatement();
                ResultSet rs = st.executeQuery(String.format("SELECT user_email,user_active_session from user WHERE user_email ='%s' AND user_active_session IS NOT NULL",this.user_email));
                String command="";
                if(rs.next()){
                    this.user_active_session = rs.getString("user_active_session");
                    return true;
                } else {
                    return false;
                }
        } catch (Exception e){
            throw e;
        }
    }

    public void update_active_session(String session_id) throws SQLException, ClassNotFoundException {
        try {
            Connection con= dbConnection.getDb();
            String sql = "UPDATE user set user_active_session = ? WHERE user_email = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,session_id);
            pt.setString(2, this.user_email);
            pt.executeUpdate();
        } catch (Exception e){
            throw e;
        }
    }

    public ArrayList<HistoryVisit> get_visited_restaurant() throws CustomException{
        ArrayList<HistoryVisit> histories = new ArrayList<HistoryVisit>();
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * from user Inner join session on user.user_email = session.session_user_id Inner join restaurant on restaurant.restaurant_id = session.session_restaurant_id Where user.user_email = ? ORDER BY session.session_id DESC;";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.user_email);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){

                HistoryVisit history = new HistoryVisit();

                history.getRestaurant().setName(rs.getString("restaurant.restaurant_name"));
                history.getRestaurant().setRestaurantId(rs.getString("restaurant.restaurant_id"));
                history.getRestaurant().setLocation("restaurant.restaurant_location");
                history.getSession().setSessionId(rs.getString("session.session_id"));
                System.out.println(history.getSession().getSessionId());
                history.getSession().setSessionStartTime(rs.getString("session.session_start_time"));
                histories.add(history);

            }
            return histories;
        } catch (Exception e ){
            System.out.println(e.getMessage());
            throw new CustomException("Fail to get visit history");
        }
    }

    public ArrayList<Consumption> get_consumption() throws CustomException{
        ArrayList<Consumption> consumptions = new ArrayList<Consumption>();
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT user.user_email,restaurant.restaurant_name,session.session_id,session.session_start_time,bill.bill_amount,bill.bill_status,bill.bill_id from user INNER JOIN session on session.session_user_id = user.user_email INNER JOIN restaurant on restaurant.restaurant_id = session.session_restaurant_id INNER JOIN bill on bill.bill_id = session.session_bill_id WHERE user.user_email = ? AND bill.bill_status = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.user_email);
            pt.setString(2, "SOLVED");
            ResultSet rs = pt.executeQuery();
            while(rs.next()){

                Consumption consumption = new Consumption();

                consumption.getRestaurant().setName(rs.getString("restaurant.restaurant_name"));
                consumption.getSession().setSessionId(rs.getString("session.session_id"));
                consumption.getSession().setSessionStartTime(rs.getString("session.session_start_time"));
                consumption.getBill().setBillAmount(rs.getDouble("bill.bill_amount"));
                consumptions.add(consumption);

            }
            return consumptions;
        } catch (Exception e ){
            System.out.println(e.getMessage());
            throw new CustomException("Fail to get visit history");
        }
    }

}
