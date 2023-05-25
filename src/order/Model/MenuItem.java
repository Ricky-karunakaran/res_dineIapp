package order.Model;

import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MenuItem {
    private String menu_item_id;
    private String menu_item_name;
    private String menu_item_description;
    private double menu_item_price;
    private byte[] menu_item_image;
    private String menu_item_allergy;
    private String menu_id;
    
    
    // Getter
    public String getMenuId(){ return this.menu_item_id; }
    public String getMenuItemName(){ return this.menu_item_name; }
    public String getMenuItemDescription() { return this.menu_item_description; }
    public double getMenuItemPrice() { return this.menu_item_price; }
    public byte[] getMenuItemImage() { return this.menu_item_image; }
    public String getMenuItemAllergy() { return this.menu_item_allergy; }
    public String getMenuItemId() { return this.menu_item_id; }
    // Setter
    public void setMenuItemName(String menuItemName) { this.menu_item_name = menuItemName; }
    public void setMenuItemDescription(String menuItemDescription) { this.menu_item_description = menuItemDescription; }
    public void setMenuItemPrice (double menuItemPrice) { this.menu_item_price = menuItemPrice; }
    public void setMenuItemImage(byte[] menuItemImage) { this.menu_item_image = menuItemImage; }
    public void setMenuItemAllergy(String menuItemAllergy) { this.menu_item_allergy = menuItemAllergy; }
    public void setMenuId (String menuId) { this.menu_id = menuId;}
    public void setMenuItemId(String menuItemId) { this.menu_item_id = menuItemId; }

    public void add_menu_item(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "INSERT INTO menu_item (menu_item_name, menu_item_description,menu_item_price, menu_item_picture,menu_item_allergy,menu_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.menu_item_name);
            pt.setString(2,this.menu_item_description);
            pt.setDouble(3, this.menu_item_price);
            pt.setBytes(4, this.menu_item_image);
            pt.setString(5,this.menu_item_allergy);
            pt.setString(6,this.menu_id);
            System.out.println(pt.executeUpdate());
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void edit_menu_item(){
         try{
            Connection con = dbConnection.getDb();
            String sql = "UPDATE menu_item SET menu_item_name = ?, menu_item_description = ?, menu_item_price = ?, menu_item_picture = ?, menu_item_allergy = ? WHERE menu_item_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.menu_item_name);
            pt.setString(2,this.menu_item_description);
            pt.setDouble(3, this.menu_item_price);
            pt.setBytes(4, this.menu_item_image);
            pt.setString(5,this.menu_item_allergy);
            pt.setString(6,this.menu_item_id);
            System.out.println(pt.executeUpdate());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public void remove_menu_item(){
        try{
            Connection con = dbConnection.getDb();
            String sql = "DELETE FROM menu_item WHERE menu_item_id = ? ";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.menu_item_id);
            System.out.println(pt.executeUpdate());
        }   catch(Exception e){
            System.out.println(e);
        }
    }
    
    public ArrayList<MenuItem> read_menu_item_list(){
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        try{
            Connection con = dbConnection.getDb();
            String sql = "SELECT * FROM menu_item WHERE menu_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.menu_id);
            ResultSet rt = pt.executeQuery();
            
            while(rt.next()){
                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(this.menu_id);
                menuItem.setMenuItemName(rt.getString("menu_item_name"));
                menuItem.setMenuItemDescription(rt.getString("menu_item_description"));
                menuItem.setMenuItemPrice(rt.getDouble("menu_item_price"));
                menuItem.setMenuItemImage(rt.getBytes("menu_item_picture"));
                menuItem.setMenuItemAllergy(rt.getString("menu_item_allergy"));
                menuItem.setMenuItemId(rt.getString("menu_item_id"));
                menuItems.add(menuItem);
            }
            
        }catch(Exception e){
        }
        return menuItems;
    }
    
    @Override
    public String toString(){
        return this.menu_item_name+" - "+this.menu_item_description+" ( RM "+this.menu_item_price+")";
    }
}
