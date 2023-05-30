package com.reporting.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.fyp_mobile.R;
import com.order.view.SessionView;
import com.reporting.controller.HistoryVisitController;
import com.systemAccount.controller.SystemAccountController;
import com.systemAccount.model.User;
import com.systemAccount.view.EditProfileView;
import com.systemAccount.view.ProfileView;
import com.utils.CustomException;
import com.utils.Dialog;
import com.utils.JDateTime;
import com.utils.Session;
import com.utils.SessionManager;

import java.util.Calendar;
import java.util.Date;

public class VisitHistoryView extends AppCompatActivity {
    private HistoryVisitController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new HistoryVisitController();
        this.controller.setView(this);
        setContentView(R.layout.activity_visit_history);
        this.add_history_row("Visit Date Time","Restaurant Name","0",true);
        this.controller.fetch_user_visit_history();


    }

    public LinearLayout get_visit_list_layout(){
        return (LinearLayout) findViewById(R.id.visit_history_list);
    }

    public void add_history_row(String visit_date_time, String visit_restaurant_name,String session_id, boolean isTitle){
        // layout
        LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        horizontal_layout_params.setMargins(0,10,0,10);
        ViewGroup.MarginLayoutParams textViewParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(5, 5, 5, 10);

        LinearLayoutCompat.LayoutParams layoutParams2 = new LinearLayoutCompat.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams2.setMargins(5, 5, 5, 10);
        layoutParams2.weight = 1;
        LinearLayout horizontal_layout = new LinearLayout(this);
        horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);
        horizontal_layout.setLayoutParams(horizontal_layout_params);

        TextView l_visit_date_time = new TextView(this);
        l_visit_date_time.setText(visit_date_time==null?"Undefined":visit_date_time);
        l_visit_date_time.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_visit_date_time.setTextColor(Color.BLACK);
        l_visit_date_time.setLayoutParams(layoutParams2);
        if(isTitle) {l_visit_date_time.setTypeface(null, Typeface.BOLD);}
        TextView l_visit_restaurant_name = new TextView(this);

        l_visit_restaurant_name.setText(visit_restaurant_name);
        l_visit_restaurant_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        l_visit_restaurant_name.setTextColor(Color.BLACK);
        l_visit_restaurant_name.setLayoutParams(layoutParams2);
        if(isTitle) {l_visit_restaurant_name.setTypeface(null, Typeface.BOLD);}

        Button action_button = new Button(this);
        action_button.setId(Integer.parseInt(session_id));
        action_button.setText("Add Feedback");
        action_button.setLayoutParams(layoutParams2);
        action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Date date  = JDateTime.getDate(visit_date_time);

                Calendar calendarToday = Calendar.getInstance();
                calendarToday.setTime(new Date());

                Calendar visitDay = Calendar.getInstance();
                visitDay.setTime(date);

                long diffInMillis = calendarToday.getTimeInMillis() - visitDay.getTimeInMillis();
                long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
                if(diffInDays >=90 ) {
                    throw new CustomException("This visit exceeds 3 months, you are not allowed to add feedback.");
                } else{
                    SessionManager sessionManager = SessionManager.getInstance();
                    sessionManager.getSession().setAttributes("add_feedback_session_id", String.valueOf(v.getId()));
                    sessionManager.getSession().setAttributes("add_feedback_restaurant_name", visit_restaurant_name);
                    sessionManager.getSession().setAttributes("add_feedback_session_date", visit_date_time);
                    to_add_feedback();
                }
                } catch (CustomException e ){
                    Dialog.dialog(controller.getView(),"Fail to add feedback",e.getMessage(),false);
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    Dialog.dialog(controller.getView(),"Fail to add feedback","There is an error with system.",false);
                }
            }
        });
        horizontal_layout.addView(l_visit_date_time);
        horizontal_layout.addView(l_visit_restaurant_name);
        if(!isTitle) {
            horizontal_layout.addView(action_button);
        }
        else {
            TextView emptyView = new TextView(this);
            emptyView.setLayoutParams(layoutParams2);
            horizontal_layout.addView(emptyView);
        }

        this.get_visit_list_layout().addView(horizontal_layout);

    }

    public void to_add_feedback(){
        try{
            this.controller.check_feedback_exist();
            Intent intent = new Intent(controller.getView(), VisitHistoryFeedbackView.class);
            startActivity(intent);
        } catch (CustomException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(controller.getView());
            builder.setTitle("Feedback Exist");
            builder.setMessage("You have added feedback. Do you want to Replace or Edit it?");
            builder.setCancelable(true); // set whether the dialog is cancelable or not
            builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SessionManager sessionManager = SessionManager.getInstance();
                    sessionManager.getSession().setAttributes("feedback_mode","edit");
                    dialog.dismiss();
                    Intent intent = new Intent(controller.getView(), VisitHistoryFeedbackView.class);
                    startActivity(intent);
                    // do something when the positive button is clicked
                }
            });
            builder.setNegativeButton("Replace", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SessionManager sessionManager = SessionManager.getInstance();
                    sessionManager.getSession().setAttributes("feedback_mode","replace");
                    dialog.dismiss();
                    Intent intent = new Intent(controller.getView(), VisitHistoryFeedbackView.class);
                    startActivity(intent);
                    // do something when the positive button is clicked
                }
            });
            builder.show();
        }


    }
}
