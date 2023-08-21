
package systemAccount.Model;

import com.utils.CustomException;
import com.utils.JDateTime;
import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class Reset_Password_Request {
    private String reset_password_id ;
    private String reset_password_user_email;
    private String reset_password_random_code;
    private String reset_password_expired_date;
    
    public String getResetPasswordId() {
        return reset_password_id;
    }

    public void setResetPasswordId(String reset_password_id) {
        this.reset_password_id = reset_password_id;
    }

    public String getResetPasswordUserEmail() {
        return reset_password_user_email;
    }

    public void setResetPasswordUserEmail(String reset_password_user_email) {
        this.reset_password_user_email = reset_password_user_email;
    }

    public String getResetPasswordRandomCode() {
        return reset_password_random_code;
    }

    public void setResetPasswordRandomCode(String reset_password_random_code) {
        this.reset_password_random_code = reset_password_random_code;
    }

    public String getResetPasswordExpiredDate() {
        return reset_password_expired_date;
    }

    public void setResetPasswordExpiredDate(String reset_password_expired_date) {
        this.reset_password_expired_date = reset_password_expired_date;
    }
    
    public void add_request(){
        try{
            Random random = new Random();
            int code = random.nextInt(900000) + 100000;
            this.reset_password_random_code = String.valueOf(code);
            this.reset_password_expired_date=JDateTime.getCurrentDateTime();
            
            String sql = "INSERT INTO RESET_PASSWORD_REQUEST (reset_password_user_email, reset_password_random_code, reset_password_expired_date) VALUES (?,?,?)";
            
            Connection con =  dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.reset_password_user_email);
            pt.setString(2, this.reset_password_random_code);
            pt.setString(3, this.reset_password_expired_date);
            pt.executeUpdate();
            
        } catch(Exception e){
        }
    }
    
    public void get_request_by_user_email() throws CustomException{
        try{

            String sql = "SELECT * from reset_password_request WHERE reset_password_user_email = ? ORDER BY reset_password_id DESC LIMIT 1";
            
            Connection con =  dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.reset_password_user_email);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                this.setResetPasswordRandomCode(rs.getString("reset_password_random_code"));
            } else {
                throw new CustomException("No such data found");
            }
            
        } catch(CustomException e){
            throw new CustomException("No reset password request found");
        }
        catch(Exception e){
        }
    }
    
}
