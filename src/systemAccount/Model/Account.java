/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package systemAccount.Model;

/**
 *
 * @author Ricky
 */
public interface Account {
    public boolean login(String password )throws Exception;
    public boolean register(String password) throws Exception;
    public void edit_info() throws Exception;
    public boolean reset_password(String email,String password);
}
