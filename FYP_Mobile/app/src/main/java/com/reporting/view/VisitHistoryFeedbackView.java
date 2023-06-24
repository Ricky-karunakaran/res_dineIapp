package com.reporting.view;
//FeedbackForm
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;
import com.reporting.controller.HistoryVisitController;
import com.utils.CustomException;
import com.utils.Session;
import com.utils.SessionManager;

public class VisitHistoryFeedbackView extends AppCompatActivity {
    private HistoryVisitController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        this.controller = new HistoryVisitController();
        this.initialize();
    }

    private void initialize(){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String restaurant_name = (String) session.getAttributes("add_feedback_restaurant_name");
        String visit_date_time= (String) session.getAttributes("add_feedback_session_date");
        TextView feedback_restaurant_name = (TextView) findViewById(R.id.feedback_restaurant_name);
        TextView feedback_session_date = (TextView) findViewById(R.id.feedback_session_date);

        feedback_restaurant_name.setText(restaurant_name);
        feedback_session_date.setText(visit_date_time);

        String mode = (String) session.getAttributes("feedback_mode");
        if(mode==null){

        }
        else if(mode!=null && mode.equals("edit")){
            try {
                String content = this.controller.fetch_feedback_content();
                EditText edit_feedback = (EditText) findViewById(R.id.feedback_input);
                edit_feedback.setText(content);
                Button submit = (Button) findViewById(R.id.submit_feedback);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        update_feedback(v);
                    }
                });
            } catch (CustomException e) {
                e.printStackTrace();
            }

        }
        else if (mode.equals("replace")){
            Button submit = (Button) findViewById(R.id.submit_feedback);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_feedback(v);
                }
            });
        }
    }

    public void add_feedback(View v){

        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String add_feedback_session_id = (String) session.getAttributes("add_feedback_session_id");
        EditText feedback = (EditText) findViewById(R.id.feedback_input);
        this.controller.add_feedback(feedback.getText().toString(),add_feedback_session_id);
        Intent intent = new Intent(this, VisitHistoryView.class);
        startActivity(intent);
    }

    public void update_feedback(View v){
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.getSession();
        String add_feedback_session_id = (String) session.getAttributes("add_feedback_session_id");
        EditText feedback = (EditText) findViewById(R.id.feedback_input);
        this.controller.update_feedback(feedback.getText().toString(),add_feedback_session_id);

        Intent intent = new Intent(this, VisitHistoryView.class);
        startActivity(intent);
    }


}
