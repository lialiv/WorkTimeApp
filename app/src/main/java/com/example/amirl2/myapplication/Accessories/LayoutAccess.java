package com.example.amirl2.myapplication.Accessories;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amirl2.myapplication.R;


/**
 * Created by AmirL2 on 9/15/2017.
 */

public class LayoutAccess {

    static public void ToastMsgInflater(Activity activity, String headerText, String msgText) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_msg,
                (ViewGroup) activity.findViewById(R.id.toast_msg_layout));

        TextView tvHeader = layout.findViewById(R.id.tv_toast_header);
        TextView tvText = layout.findViewById(R.id.tv_toast_text);
        tvHeader.setText(headerText);
        tvText.setText(msgText);

        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    static public void DialogMsgInflater(Activity activity, LogObj logObj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(R.layout.dialog_msg);
        builder.setMessage("Entry time: " + logObj.entryTime)
                .setTitle(logObj.getDate()+" ")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
}