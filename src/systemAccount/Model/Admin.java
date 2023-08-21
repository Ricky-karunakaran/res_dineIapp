
package systemAccount.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin  {
    private String admin_email;
    private String admin_password;
    private String admin_name;
    private String admin_number;
    
    public Admin() {}
    public Admin(String email,String password, String name, String number){
        this.admin_email = email;
        this.admin_name = name;
        this.admin_password = password;
        this.admin_number = number;
    }
    public void setEmail(String email) { this.admin_email = email; }
    
    public void setname(String name) { this.admin_name = name; }
    
    public void setNumber(String number) { this.admin_number = number; }
    
    public String getEmail() { return this.admin_email; }
    
    public String getName() { return this.admin_name; }
    
    public String getNumber() { return this.admin_number; }
    
    public boolean login_account(String password) {
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            ResultSet rt = st.executeQuery(String.format("SELECT * from admin where admin_email='%s' AND admin_password ='%s'",this.admin_email,password));
            if(rt.next()){
                this.admin_email = rt.getString("admin_email");
                this.admin_name = rt.getString("admin_name");
                this.admin_number = rt.getString("admin_number");
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean register(String password) throws Exception {
        try {
            Connection con= dbConnection.getDb();
            Statement st=con.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT admin_email from admin WHERE admin_email ='%s'",this.admin_email));
            if(rs.next()){
                throw new Exception("Email has been used");
            } else {
                st.execute(String.format("Insert into admin (admin_email, admin_name,admin_number, admin_password) Values ('%s','%s','%s','%s')",this.admin_email,this.admin_name,this.admin_number,password));
                con.close();
            }
            
            return true;
        } catch (Exception e){
            throw e;
        }    
    }
}
