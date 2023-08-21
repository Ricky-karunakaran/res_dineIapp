package helper;

import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import order.Model.Bill;
import order.Model.BillItem;


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
