/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import order.Model.Bill;
import order.Model.BillItem;

/**
 *
 * @author Ricky
 */
public class SessionOrderFetchService extends Service<ArrayList<BillItem>>{
    private String bill_id;
    public SessionOrderFetchService(String bill_id){
        this.bill_id = bill_id;
    }
    
    @Override
    protected Task<ArrayList<BillItem>> createTask() {
        return new Task<ArrayList<BillItem>>(){
        @Override
        protected ArrayList<BillItem> call() throws Exception{
            Bill bill = new Bill();
            bill.setBillId(bill_id);
            ArrayList<BillItem> newList = bill.getBillItems();
            return newList;
        }
        };

    }
    
}
