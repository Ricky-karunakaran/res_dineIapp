package com.systemAccount.model;

public interface Account {
    public boolean login(String password )throws Exception;
    public boolean register(String password) throws Exception;
    public void edit_info() throws Exception;
    public boolean reset_password(String email,String password);
}
