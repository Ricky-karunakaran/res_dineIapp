package com.reporting.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_mobile.R;
import com.reporting.controller.HistoryVisitController;

public class ConsumptionView  extends AppCompatActivity {
    private HistoryVisitController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        this.controller = new HistoryVisitController();

    }
}
