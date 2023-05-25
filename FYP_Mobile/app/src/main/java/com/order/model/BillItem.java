package com.order.model;

import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillItem {
    private String bill_item_id;
    private String bill_id;
    private String bill_item_name;
    private double bill_item_unit_price;
    private int bill_item_quantity;
    private String bill_item_status;

    // setter
    public void setBillItemId(String bill_item_id) { this.bill_item_id = bill_item_id;}
    public void setBillId(String bill_id) { this.bill_id = bill_id;}
    public void setBillItemName (String bill_item_name) { this.bill_item_name = bill_item_name; }
    public void setBillItemStatus (String bill_item_status) { this.bill_item_status = bill_item_status; }
    public void setBillItemUnitPrice(double bill_item_unit_price) { this.bill_item_unit_price = bill_item_unit_price; }
    public void setBillItemQuantity (int bill_item_quantity) { this.bill_item_quantity = bill_item_quantity;}

    // getter

    public String getBillItemId() { return this.bill_item_id;}
    public String getBillId(){ return this.bill_id;}
    public int getBillItemQuantity() {return this.bill_item_quantity;}
    public String getbillItemName() {return this.bill_item_name;}
    public String getBillItemStatus() { return this.bill_item_status;}
    public double getBillItemUnitPrice() {return this.bill_item_unit_price;}


    // Add
    public void addBillItem(){
        try{
            String sql = "INSERT INTO bill_item " +
                    "(bill_id,bill_item_name,bill_item_quantity,bill_item_unit_price,bill_item_status)" +
                    "Values (?,?,?,?,'UNSOLVED')";
            Connection con = dbConnection.getDb();
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,this.getBillId());
            pt.setString(2, this.getbillItemName());
            pt.setInt(3, this.getBillItemQuantity());
            pt.setDouble(4, this.getBillItemUnitPrice());
            pt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
