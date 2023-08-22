package com.order.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.order.model.Bill;
import com.order.model.BillItem;
import com.order.view.StripPaymentActivity;
import com.utils.Session;
import com.utils.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BillController {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }

    public void fetch_bill_item(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String bill_id =(String) session.getAttributes("session_bill_id");

        Bill bill = new Bill();
        bill.setBillId(bill_id);
        bill.read_bill_by_id();

        ArrayList<BillItem> bill_list= bill.getBillItems();

        DecimalFormat df = new DecimalFormat("#0.00");
        double total_price = 0;
        for(int i=0;i<bill_list.size();i++){

            BillItem bill_item = bill_list.get(i);
            total_price+=bill_item.getBillItemUnitPrice()*bill_item.getBillItemQuantity();
            String bill_total_item_price = df.format(bill_item.getBillItemUnitPrice()*bill_item.getBillItemQuantity());

            ((StripPaymentActivity) currentView).add_bill_row(
                    bill_item.getbillItemName(),
                    String.valueOf(bill_item.getBillItemQuantity()),
                    bill_total_item_price,
                    false
                    );
        }

        ((StripPaymentActivity) currentView).add_bill_row(
                "Total",
                "",
                "RM"+df.format(total_price),
                true
        );
    }


}
