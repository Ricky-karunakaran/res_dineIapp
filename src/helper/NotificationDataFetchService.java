package helper;

import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import order.Model.Notification;

public class NotificationDataFetchService extends Service<ArrayList<Notification>> {
    private String restaurant_id;
    public NotificationDataFetchService(String restaurant_id){
        this.restaurant_id = restaurant_id;
    }
    
    @Override
    protected Task<ArrayList<Notification>> createTask() {
        return new Task<ArrayList<Notification>>(){
        @Override
        protected ArrayList<Notification> call() throws Exception{
            System.out.println("Getting Session"+ restaurant_id);
            Notification notification = new Notification();
            notification.setNotificationRestaurantId(restaurant_id);
            ArrayList<Notification> newList = notification.get_all_notification();
            return newList;
        }
        };

    }
    
}