package com.order.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.order.model.Menu;
import com.order.model.MenuItem;
import com.order.model.WaiterCall;
import com.order.view.SessionView;
import com.systemAccount.model.Restaurant;
import com.systemAccount.model.User;
import com.utils.Dialog;
import com.utils.JDateTime;
import com.utils.Session;
import com.utils.SessionManager;

import java.util.ArrayList;

public class SessionController {

    private AppCompatActivity currentView;
    public void setView(AppCompatActivity v) {
        this.currentView  = v;
    }

    public void fetchMenu(TextView restaurant_name, LinearLayout linear_layour) {


        System.out.println("FetchMenuCalled");
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String session_restaurant_id = (String) session.getAttributes("session_restaurant_id");

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(session_restaurant_id);
        try {
            restaurant.read_restaurant_by_id();
            restaurant_name.setText("Restaurant: " + restaurant.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(session_restaurant_id);
        Menu menu = new Menu();
        menu.setMenuRestaurantId(session_restaurant_id);
        ArrayList<Menu> menus = menu.read_all_menu();
        for (int i = 0; i < menus.size(); i++) {
            MenuItem menuItem = new MenuItem();
            menuItem.setMenuId(menus.get(i).getMenuId());
            menus.get(i).setMenuItem(menuItem.read_menu_item_list());

            TextView menuTitle = new TextView(currentView);
            menuTitle.setText(menus.get(i).getDescription());
            menuTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            menuTitle.setTypeface(null, Typeface.BOLD);
            menuTitle.setTextColor(Color.BLACK);


            linear_layour.addView(menuTitle);
            linear_layour.setDividerPadding(15);
            for (int j = 0; j < menus.get(i).getMenuItems().size(); j++) {

                MenuItem thisMenuItem = menus.get(i).getMenuItems().get(j);

                LinearLayout horizontal_layout = new LinearLayout(currentView);

                horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                horizontal_layout_params.setMargins(0, 10, 0, 10);
                horizontal_layout.setLayoutParams(horizontal_layout_params);

                ImageView menuItemImage = new ImageView(currentView);
                Bitmap bitmap = BitmapFactory.decodeByteArray(thisMenuItem.getMenuItemImage(), 0, thisMenuItem.getMenuItemImage().length);
                menuItemImage.setImageBitmap(bitmap);
                double original_whRatio = bitmap.getWidth() / bitmap.getHeight();
                double desired_width = 300;
                double desired_height = 350;

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) desired_width, (int) desired_height);
                menuItemImage.setLayoutParams(layoutParams);

                // Setup menu info

                LinearLayout menu_info = new LinearLayout(currentView);
                LinearLayout.LayoutParams menu_info_params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                menu_info_params.setMargins(0, 10, 0, 10);
                menu_info.setLayoutParams(menu_info_params);

                menu_info.setOrientation(LinearLayout.VERTICAL);
                menu_info.setDividerPadding(5);
                // menu_info.setGravity(Gravity.CENTER);


                TextView menuItemName = new TextView(currentView);
                menuItemName.setText(thisMenuItem.getMenuItemName());
                TextView menuItemDescription = new TextView(currentView);
                menuItemDescription.setText(thisMenuItem.getMenuItemDescription());
                TextView menuItemPrice = new TextView(currentView);
                menuItemPrice.setText("RM" + String.valueOf(thisMenuItem.getMenuItemPrice()));

                menuItemName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                menuItemPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                menuItemDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                menuItemName.setTextColor(Color.BLACK);
                menuItemPrice.setTextColor(Color.BLACK);

                ViewGroup.MarginLayoutParams textViewParams = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(20, 5, 20, 5);


                menuItemName.setLayoutParams(textViewParams);
                menuItemPrice.setLayoutParams(textViewParams);
                menuItemDescription.setLayoutParams(textViewParams);

                Button action_button = new Button(currentView);
                action_button.setId(Integer.parseInt(thisMenuItem.getMenuItemId()));
                action_button.setText("Add To Cart");
                action_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((SessionView) currentView).add_to_cart(v.getId());
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                action_button.setLayoutParams(params);
                menu_info.addView(menuItemName);
                menu_info.addView(menuItemDescription);
                menu_info.addView(menuItemPrice);
                menu_info.addView(action_button);


                horizontal_layout.addView(menuItemImage);
                horizontal_layout.addView(menu_info);
                linear_layour.addView(horizontal_layout);

            }
        }
    }

    public void checkSessionStatus(){

    }


}
