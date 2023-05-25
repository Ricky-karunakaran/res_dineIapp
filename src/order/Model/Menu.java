package order.Model;

import com.utils.Model;
import com.utils.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Menu extends Model{
    private String menu_id;
    private String menu_restaurant_id;
    private ArrayList<MenuItem> menu_items;
    private String menu_description;

    public void setMenuRestaurantId(String restaurant_id){ this.menu_restaurant_id = restaurant_id; }
    public void setMenuId(String id){ this.menu_id = id; }
    public void setMenuDescription(String menu_description) { this.menu_description = menu_description;}
    public void resetMenuItem () { this.menu_items = null; }
    
    public String getDescription() { return this.menu_description; }
    public String getMenuId() { return this.menu_id; }
    public int getQuantity(){
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String cmd = String.format("SELECT COUNT(*) FROM menu JOIN menu_item ON menu.menu_id = menu_item.menu_id WHERE menu.menu_id='%s';",this.menu_id);
            ResultSet rt = st.executeQuery(cmd);
            if(rt.next()){
                return rt.getInt("COUNT(*)");
            } else
            {
                return 0;
            }
        }catch(Exception e){
            
        }
        return 0;
    }
    
    public void add_menu() throws SQLException, Exception{
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String cmd = String.format("INSERT INTO menu (menu_description,menu_restaurant_id) VALUES('%s','%s')",this.menu_description,this.menu_restaurant_id);
            st.execute(cmd);
        } catch(SQLException e){
            throw e;
        } catch (Exception e){
            throw e;
        }
    }
    public void edit_menu() throws SQLException, Exception {
         try{
            Connection con = dbConnection.getDb();
            String sql = "UPDATE menu SET menu_description = ? WHERE menu_id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, this.menu_description);
            pt.setString(2, this.menu_id);
            pt.executeUpdate();
        } catch(SQLException e){
            throw e;
        } catch (Exception e){
            throw e;
        }
    }
    public void delete_menu() throws SQLException, Exception{
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String cmd = String.format("DELETE FROM menu WHERE menu_id='%s'",this.menu_id);
            st.execute(cmd);
        } catch(SQLException e) {
            throw e;
        } catch (Exception e){
            throw e;
        }
    }

    public static ArrayList<Menu> read_all_menu(String restaurant_id){
        
        ArrayList<Menu> menuList = new ArrayList<Menu>();
        try{
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();
            String cmd = String.format("SELECT * from menu where menu_restaurant_id='%s'",restaurant_id);
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
    
    public ArrayList<MenuItem> getMenuItems(){
        if(this.menu_items == null ){
            MenuItem menuItem = new MenuItem();
            menuItem.setMenuId(this.menu_id);
            this.menu_items = menuItem.read_menu_item_list();
        }
        
        return this.menu_items;
    
    }

}
