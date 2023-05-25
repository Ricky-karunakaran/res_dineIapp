/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import customerAuthentication.Model.Dine_In_Session;
import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import systemAccount.Model.Restaurant;

/**
 *
 * @author Ricky
 */
public class SessionDataFetchService extends Service<ArrayList<Dine_In_Session>> {
    private String restaurant_id;
    public SessionDataFetchService(String restaurant_id){
        this.restaurant_id = restaurant_id;
    }
    
    @Override
    protected Task<ArrayList<Dine_In_Session>> createTask() {
        return new Task<ArrayList<Dine_In_Session>>(){
        @Override
        protected ArrayList<Dine_In_Session> call() throws Exception{
            System.out.println("Getting Session"+ restaurant_id);
            Dine_In_Session dine_in_session = new Dine_In_Session();
            ArrayList<Dine_In_Session> newList = dine_in_session.get_session_by_restaurant_id(restaurant_id);
            return newList;
        }
        };

    }
    
}
