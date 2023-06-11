package com.order.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.customerAuthentication.view.HomeView;
import com.order.model.Bill;
import com.order.model.BillItem;
import com.order.model.Cart;
import com.order.model.CartItem;
import com.order.model.MenuItem;
import com.order.model.Notification;
import com.order.model.WaiterCall;
import com.order.view.CartView;
import com.order.view.SessionView;
import com.utils.Dialog;
import com.utils.Session;
import com.utils.SessionManager;

import java.util.ArrayList;

public class CartController  {
    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }

    public void add_item(String menu_item_id){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        System.out.println(menu_item_id);

        // get session_id
        // get cart_id
        String cart_id =(String) session.getAttributes("session_cart_id");
        // add cart_item
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(menu_item_id);
        try {
            menuItem.read_menu_item_by_id();
            CartItem cartItem = new CartItem();
            cartItem.setCartItemName(menuItem.getMenuItemName());
            cartItem.setCartItemMenuId(menuItem.getMenuItemId());
            cartItem.setCartItemUnitPrice(menuItem.getMenuItemPrice());
            cartItem.setCartId(cart_id);
            cartItem.add_cart_item();

            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("Item Added To Cart");
            builder.setMessage(menuItem.getMenuItemName()+ " is added.");
            builder.setCancelable(false); // set whether the dialog is cancelable or not
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    // do something when the positive button is clicked
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void fetchCartItem(LinearLayout linear_layour){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String cart_id = (String) session.getAttributes("session_cart_id");

        Cart cart = new Cart();
        cart.setCartId(cart_id);
        cart.read_all_cart_item();
        ArrayList<CartItem> cart_items = cart.getCartItems();
        System.out.println(cart_id);
        System.out.println(cart_items.size());
        if(cart_items.size()==0) { ((CartView)this.currentView).disable_submit_button();}
        for(int i=0;i<cart_items.size();i++){

            LinearLayout item_linear_layout = new LinearLayout(currentView);


            item_linear_layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            horizontal_layout_params.setMargins(20,10,20,10);
            item_linear_layout.setPadding(20,20,20,20);
            item_linear_layout.setLayoutParams(horizontal_layout_params);

            TextView cartItemTitle = new TextView(currentView);
            cartItemTitle.setText("Item Name: "+cart_items.get(i).getCartItemName());
            cartItemTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            cartItemTitle.setTypeface(null, Typeface.BOLD);
            cartItemTitle.setTextColor(Color.BLACK);




            TextView cartItemPrice = new TextView(currentView);
            double price = cart_items.get(i).getCartItemQuantity()*cart_items.get(i).getCartItemUnitPrice();
            cartItemPrice.setText("Total Price: RM "+String.valueOf(price));
            cartItemPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            cartItemPrice.setTextColor(Color.BLACK);

            TextView cartItemQuantity = new TextView(currentView);
            cartItemQuantity.setText("Quantity: "+String.valueOf(cart_items.get(i).getCartItemQuantity()) );
            cartItemQuantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            cartItemQuantity.setTextColor(Color.BLACK);

            LinearLayout add_remove_linear_layout = new LinearLayout(currentView);
            add_remove_linear_layout.setOrientation(LinearLayout.HORIZONTAL);
            add_remove_linear_layout.setId(Integer.parseInt(cart_items.get(i).getCartItemId()));
            LinearLayout.LayoutParams add_remove_linear_layout_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            add_remove_linear_layout.setLayoutParams(add_remove_linear_layout_params);

            GradientDrawable actionBorder = new GradientDrawable();
            actionBorder.setShape(GradientDrawable.RECTANGLE);
            actionBorder.setStroke(1, Color.GRAY);
            actionBorder.setColor(Color.BLACK);

            LinearLayout.LayoutParams action_button_layour = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            action_button_layour.setMargins(50,0,0,0);

            TextView addButton = new TextView(currentView);
            addButton.setText("+1");
            addButton.setPadding(10,0,10,0);
            addButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            addButton.setTextColor(Color.WHITE);
            addButton.setBackground(actionBorder);
            addButton.setLayoutParams(action_button_layour);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parent =(View) v.getParent();
                    ((CartView)currentView).increase_cart_item(parent.getId());
                }
            });
            TextView minusButton = new TextView(currentView);
            minusButton.setText("-1");
            minusButton.setPadding(10,0,10,0);
            minusButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            minusButton.setTextColor(Color.WHITE);
            minusButton.setBackground(actionBorder);
            minusButton.setLayoutParams(action_button_layour);
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parent =(View) v.getParent();
                    ((CartView)currentView).decrease_cart_item(parent.getId());
                }
            });

            TextView removeButton = new TextView(currentView);
            removeButton.setText("Remove");
            removeButton.setPadding(10,0,10,0);
            removeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            removeButton.setTextColor(Color.WHITE);
            removeButton.setBackground(actionBorder);
            removeButton.setLayoutParams(action_button_layour);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parent =(View) v.getParent();
                    ((CartView)currentView).remove_cart_item(parent.getId());
                }
            });

            add_remove_linear_layout.addView(cartItemQuantity);
            add_remove_linear_layout.addView(addButton);
            add_remove_linear_layout.addView(minusButton);
            add_remove_linear_layout.addView(removeButton);


            item_linear_layout.addView(cartItemTitle);
            item_linear_layout.addView(cartItemPrice);
            item_linear_layout.addView(add_remove_linear_layout);

            GradientDrawable border = new GradientDrawable();
            border.setShape(GradientDrawable.RECTANGLE);
            border.setStroke(1, Color.GRAY);
            item_linear_layout.setBackground(border);


            linear_layour.addView(item_linear_layout);

        }
    }

    public void submit_cart(){
        try{

            SessionManager sessionManager = SessionManager.getInstance();
            Session session = sessionManager.getSession();
            String cart_id = (String) session.getAttributes("session_cart_id");
            String bill_id = (String) session.getAttributes("session_bill_id");
            Cart cart = new Cart();
            cart.setCartId(cart_id);
            cart.read_all_cart_item();
            ArrayList<CartItem> cart_items = cart.getCartItems();
            double totalAmount = 0;
            for(int i=0;i< cart_items.size();i++){
                if(!cart_items.get(i).getCartItemStatus().equals("SUBMITTED")) {
                    cart_items.get(i).submit_cart_item();
                    totalAmount += cart_items.get(i).getCartItemQuantity() * cart_items.get(i).getCartItemUnitPrice();

                    BillItem bill_item = new BillItem();
                    bill_item.setBillId(bill_id);
                    bill_item.setBillItemName(cart_items.get(i).getCartItemName());
                    bill_item.setBillItemQuantity(cart_items.get(i).getCartItemQuantity());
                    bill_item.setBillItemUnitPrice(cart_items.get(i).getCartItemUnitPrice());
                    bill_item.addBillItem();
                }
            }
            Bill bill = new Bill();
            bill.setBillId(bill_id);
            bill.update_amount(totalAmount);

            AlertDialog.Builder builder = new AlertDialog.Builder(this.currentView);
            builder.setTitle("Submit cart success");
            builder.setMessage("Your cart item has been submitted");
            builder.setCancelable(false); // set whether the dialog is cancelable or not
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    // do something when the positive button is clicked
                }
            });
            builder.show();
            String session_id = (String) session.getAttributes("session_id");
            this.notify_restaurant("You have received new order from session "+session_id,"New Order Received",session_id);
            Intent intent = new Intent(currentView, SessionView.class);
            currentView.startActivity(intent);
        } catch(Exception e){

        }


    }

    public void increase_cart_item(int cart_item_id){
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(String.valueOf(cart_item_id));
        cartItem.increase_cart_item_quantity();
    }

    public void decrease_cart_item(int cart_item_id){
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(String.valueOf(cart_item_id));
        cartItem.decrease_cart_item_quantity();
    }

    public void remove_cart_item(int cart_item_id){
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(String.valueOf(cart_item_id));
        cartItem.remove_cart_item();
    }

    public void notify_restaurant(String content, String type, String session_id){
        try{
            Notification notification = new Notification();
            notification.setNotificationContent(content);
            notification.setNotificationType(type);
            notification.setNotificationSessionId(session_id);
            notification.add_notification();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Dialog.dialog(this.currentView,"Fail To Notify Restaurant","Fail to send notification to restaurat, please contact restaurant staff", false);
        }
    }
}
