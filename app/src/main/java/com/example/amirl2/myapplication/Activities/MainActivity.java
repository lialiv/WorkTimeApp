package com.example.amirl2.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amirl2.myapplication.Accessories.DBHelper;
import com.example.amirl2.myapplication.Accessories.UserObj;
import com.example.amirl2.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnCreateUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBHelper dbHelper = new DBHelper(this);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnCreateUser = (Button) findViewById(R.id.btn_create_user);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (etUsername.length() > 0 && etPassword.length() > 0)
                    btnLogin.setEnabled(true);
                else if (etUsername.length() == 0 || etPassword.length() == 0)
                    btnLogin.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etUsername.length() > 0 && etPassword.length() > 0)
                    btnLogin.setEnabled(true);
                else if (etUsername.length() == 0 || etPassword.length() == 0)
                    btnLogin.setEnabled(false);
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = dbHelper.validateUser(etUsername.getText().toString(), etPassword.getText().toString());
                Toast.makeText(MainActivity.this, "The username and password are good!", Toast.LENGTH_LONG);
            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newUserActivityIntent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(newUserActivityIntent);
            }
        });


    }




}
