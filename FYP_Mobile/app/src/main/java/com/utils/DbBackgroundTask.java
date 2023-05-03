package com.utils;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbBackgroundTask extends AsyncTask<Void, Void, ResultSet> {
    private String statement;
    private ResultSet resultSet;
    private boolean executionStatus;
    public void setStatement(String statement){
        this.statement=statement;
    }
    @Override
    protected ResultSet doInBackground(Void... voids) {
        try{
            this.executionStatus=false;
            System.out.println("Connecting to DB");
            Connection con = dbConnection.getDb();
            Statement st = con.createStatement();

            ResultSet rt = st.executeQuery(statement);
            return rt;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultSet rt) {
        try{
            System.out.println("Hello");
            this.resultSet =  rt;
            this.executionStatus = true;
        } catch(Exception e){

        }

    }
    public boolean getExecutionStatus(){
        return this.executionStatus;
    }
}