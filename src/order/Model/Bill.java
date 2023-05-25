package order.Model;

import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Bill {
    private String bill_id;
    private double bill_amount;
    private String bill_status;
    private String bill_session_id;
    private ArrayList<BillItem> bill_items;
    public void setBillSessionId(String bill_session_id) { this.bill_session_id = bill_session_id; }
    public void setBillId(String bill_id) { this.bill_id = bill_id; }

    public String getBillId() {return this.bill_id; }
    public String getBillStatus() { return this.bill_status; }
    public double getBillAmount() { return this.bill_amount; }
    
    public void read_bill_by_id(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * from bill where bill_id = ?";
            PreparedStatement pt =con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1, this.bill_id);
            ResultSet rs = pt.executeQuery();
            if(rs.next()){
                this.bill_session_id = rs.getString("bill_session_id");
                this.bill_status = rs.getString("bill_status");
                this.bill_amount = rs.getDouble("bill_amount");
            }
        } catch(Exception e){

        }
    }
    public void update_amount(double value){
        try{
            Connection con = dbConnection.getDb();
            String sql = "Update  bill SET bill_amount = bill_amount + ? WHERE bill_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setDouble(1, value);
            pt.setString(2, this.bill_id);
            pt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    
    public ArrayList<BillItem> getBillItems(){
        if(this.bill_items == null ){
            BillItem bill_item = new BillItem();
            bill_item.setBillId(this.bill_id);
            this.bill_items = bill_item.read_all_bill_item();
            }
        
        return this.bill_items;
    }
    
    public double getBillPrice(){
        double price=0;
        for(int i=0;i<this.bill_items.size();i++){
            price = price + this.bill_items.get(i).getBillItemPrice();
        }
        return price;
    }
}
