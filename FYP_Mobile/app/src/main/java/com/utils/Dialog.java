package com.utils;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Dialog {
    public static void dialog(AppCompatActivity view, String title, String message, boolean cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancel); // set whether the dialog is cancelable or not
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                // do something when the positive button is clicked
            }
        });
        builder.show();
    }
}
