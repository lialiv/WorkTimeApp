package com.example.amirl2.myapplication.Accessories;

import android.app.Activity;
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

      static public void ToastMsgInflater (Activity activity, String headerText, String msgText) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_msg,
                (ViewGroup) activity.findViewById(R.id.toast_msg_layout));

        TextView header = (TextView) layout.findViewById(R.id.tv_toast_header);
        TextView text = (TextView) layout.findViewById(R.id.tv_toast_text);
        header.setText(headerText);
        text.setText(msgText);

        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
