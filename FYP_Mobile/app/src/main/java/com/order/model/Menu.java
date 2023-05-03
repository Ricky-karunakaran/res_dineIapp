package com.order.model;

import com.utils.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Menu {
    private String menu_restaurant_id;
    private String menu_id;
    private String menu_description;
    private ArrayList<MenuItem> menu_items;

    public void setMenuRestaurantId(String restaurant_id){ this.menu_restaurant_id = restaurant_id; }
    public void setMenuId(String id){ this.menu_id = id; }
    public void setMenuDescription(String menu_description) { this.menu_description = menu_description;}
    public void setMenuItem(ArrayList<MenuItem> menu_items) { this.menu_items = menu_items; }

    public void resetMenuItem () { this.menu_items = null; }

    public String getDescription() { return this.menu_description; }
    public String getMenuId() { return this.menu_id; }

    public ArrayList<MenuItem> getMenuItems() { return this.menu_items; }

    public  ArrayList<Menu> read_all_menu(){

        ArrayList<Menu> menuList = new ArrayList<Menu>();
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String cmd = String.format("SELECT * from menu where menu_restaurant_id='%s'",this.menu_restaurant_id);
            ResultSet rt = st.executeQuery(cmd);
            while(rt.next()){
                Menu menu = new Menu();
                System.out.println(rt.getString("menu_description"));
                menu.setMenuDescription(rt.getString("menu_description"));
                menu.setMenuId(rt.getString("menu_id"));
                menuList.add(menu);
            }
        } catch(Exception e){
            System.out.println(e);

        }
        return menuList;
    }
}
