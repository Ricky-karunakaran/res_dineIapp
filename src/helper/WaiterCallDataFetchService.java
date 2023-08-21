
package helper;

import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import order.Model.WaiterCall;

public class WaiterCallDataFetchService extends Service<ArrayList<WaiterCall>> {
    private String restaurant_id;
    public WaiterCallDataFetchService(String restaurant_id){
        this.restaurant_id = restaurant_id;
    }
    
    @Override
    protected Task<ArrayList<WaiterCall>> createTask() {
        return new Task<ArrayList<WaiterCall>>(){
        @Override
        protected ArrayList<WaiterCall> call() throws Exception{
            System.out.println("Getting Session"+ restaurant_id);
            WaiterCall waiter_call = new WaiterCall();
            waiter_call.setWaiterCallRestaurantId(restaurant_id);
            ArrayList<WaiterCall> newList = waiter_call.read_all_waiter_calls();
            return newList;
        }
        };

    }
    
}
