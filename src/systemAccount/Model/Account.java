/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package systemAccount.Model;

/**
 *
 * @author Ricky
 */
public interface Account extends Model {
    public boolean login(String password )throws Exception;
    public boolean register(String password);
    public void edit_info();
    public boolean reset_password(String email,String password);
}
