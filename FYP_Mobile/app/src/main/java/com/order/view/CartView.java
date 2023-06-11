package com.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;
import com.order.controller.CartController;
import com.order.controller.MenuController;
import com.order.model.Cart;

public class CartView extends AppCompatActivity {
    private CartController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_cart);
        this.controller = new CartController();
        this.controller.setView(this);
        this.initializeCartItemList();
    }

    private void initializeCartItemList(){
        LinearLayout cartItemList = (LinearLayout) findViewById(R.id.cart_cart_item_list);
        this.controller.fetchCartItem(cartItemList);
    }

    public void disable_submit_button(){
        Button button = findViewById(R.id.submit_cart_button);
        button.setClickable(false);
    }

    public void submit_cart(View v){
        this.controller.submit_cart();
    }

    public void increase_cart_item(int cart_item_id){
        this.controller.increase_cart_item(cart_item_id);
        Intent intent = new Intent(this, CartView.class);
        startActivity(intent);
    }

    public void decrease_cart_item(int cart_item_id){
        this.controller.decrease_cart_item(cart_item_id);
        Intent intent = new Intent(this, CartView.class);
        startActivity(intent);
    }

    public void remove_cart_item(int cart_item_id){
        this.controller.remove_cart_item(cart_item_id);
        Intent intent = new Intent(this, CartView.class);
        startActivity(intent);
    }
    public void backSessionView(View v){
        Intent intent = new Intent(this, SessionView.class);
        startActivity(intent);
    }
}
