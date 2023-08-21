package helper;

import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import systemAccount.Model.Restaurant;

public class DataFetchService extends Service<ArrayList<Restaurant>> {
    
    @Override
    protected Task<ArrayList<Restaurant>> createTask() {
        return new Task<ArrayList<Restaurant>>(){
        @Override
        protected ArrayList<Restaurant> call() throws Exception{
            System.out.println("getting data");
            ArrayList<Restaurant> newList = Restaurant.getAll();
            return newList;
        }
        };

    }
    
}
