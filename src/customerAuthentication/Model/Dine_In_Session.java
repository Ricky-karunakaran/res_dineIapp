
package customerAuthentication.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


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

    private String session_user_name;

    public void setSessionStatus(String session_status){ this.session_status = session_status; }
    public void setSessionStartTime(String session_start_time) {this.session_start_time = session_start_time; }
    public void setSessionEndTime(String session_end_time) { this.session_end_time = session_end_time; }
    public void setSessionId(String session_id) { this.session_id = session_id; }
    public void setSessionUserEmail(String session_user_email) { this.session_user_email = session_user_email; }
    public void setSessionUserName(String session_user_name) { this.session_user_name = session_user_name;}
    public void setSessionBillId(String session_bill_id) { this.session_bill_id = session_bill_id; }
    public void setSessionTableId(String session_table_id) { this.session_table_id = session_table_id;}
    
    public String getSessionId() { return this.session_id; }
    public String getSessionRestaurantId() { return this.session_restaurant_id; }
    public String getSessionCartId() {return this.session_cart_id; }
    public String getSessionBillId() {return this.session_bill_id; }
    public String getSessionUserName() { return this.session_user_name;}
    public String getSessionStatus() { return this.session_status;} 
    public String getSessionStartTime() { return this.session_start_time;}
    public String getSessionTableId() { return this.session_table_id;} 
    public Dine_In_Session(){}
    
    public void get_session_by_id(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * from session INNER JOIN user on session.session_user_id= user.user_email WHERE session_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.session_id);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                this.setSessionId(rs.getString("session_id"));
                this.setSessionUserName(rs.getString("user_name"));
                this.setSessionBillId(rs.getString("session_bill_id"));
                this.setSessionStatus(rs.getString("session_status"));
                this.setSessionTableId(rs.getString("session_table_id"));
            }
            con.close();
        } catch(Exception e){
        }
    }
    public void check_out_session(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "UPDATE session SET session_status = 'CLOSED' where session_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.session_id);
            pt.execute();
            con.close();
        } catch(Exception e){
        }
    }
    public  ArrayList<Dine_In_Session> get_session_by_restaurant_id(String restaurantId){
        ArrayList<Dine_In_Session> sessionList = new ArrayList<Dine_In_Session>();
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * from session INNER JOIN user on session.session_user_id= user.user_email WHERE session_restaurant_id = ? AND session_status != 'CLOSED'";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, restaurantId);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                Dine_In_Session session = new Dine_In_Session();
                session.setSessionId(rs.getString("session_id"));
                session.setSessionUserName(rs.getString("user_name"));
                session.setSessionBillId(rs.getString("session_bill_id"));
                session.setSessionStartTime(rs.getString("session_start_time"));
                session.setSessionStatus(rs.getString("session_status"));
                session.setSessionTableId(rs.getString("session_table_id"));
                sessionList.add(session);
            }
            con.close();
        } catch(Exception e){
        }
        return sessionList;
    }
    
}
