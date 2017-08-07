package com.example.amirl2.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        final EditText etName = (EditText) findViewById(R.id.et_name);
        final EditText etUsername = (EditText) findViewById(R.id.et_username);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        final EditText etPasswordRetype = (EditText) findViewById(R.id.et_password_retype);
        final Button btnCreateUser = (Button) findViewById(R.id.btn_create_user);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String passwordRetype = etPasswordRetype.getText().toString();

                if (!password.equals(passwordRetype))
                    Toast.makeText(NewUserActivity.this, "The two passwords you entered are not equal", Toast.LENGTH_LONG).show();

            }

        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (etUsername.length() > 0 && etPassword.length() > 0 && etPasswordRetype.length() > 0)
                    btnCreateUser.setEnabled(true);
                else if (etUsername.length() == 0 || etPassword.length()== 0 || etPasswordRetype.length() == 0)
                    btnCreateUser.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (etUsername.length() > 0 && etPassword.length() > 0 && etPasswordRetype.length() > 0)
                    btnCreateUser.setEnabled(true);
                else if (etUsername.length() == 0 || etPassword.length() == 0 || etPasswordRetype.length() == 0)
                    btnCreateUser.setEnabled(false);
            }
        };

        etUsername.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
        etPasswordRetype.addTextChangedListener(watcher);

    }
}
