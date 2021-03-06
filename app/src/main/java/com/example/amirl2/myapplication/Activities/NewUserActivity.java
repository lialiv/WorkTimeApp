package com.example.amirl2.myapplication.Activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amirl2.myapplication.Accessories.DBHelper;
import com.example.amirl2.myapplication.Accessories.UserObj;
import com.example.amirl2.myapplication.R;

public class NewUserActivity extends AppCompatActivity {
    EditText etFirstName, etLastName;
    EditText etUsername;
    EditText etPassword;
    EditText etPasswordRetype;
    Button btnCreateUser;
    TextView tvPassStrength, tvUserLong;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etLastName = (EditText) findViewById(R.id.et_last_name);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordRetype = (EditText) findViewById(R.id.et_password_retype);
        btnCreateUser = (Button) findViewById(R.id.btn_create_user);
        tvPassStrength = (TextView) findViewById(R.id.tv_password_strength);
        tvUserLong = (TextView) findViewById(R.id.tv_username_long);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        final DBHelper dbHelper = new DBHelper(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String username = etUsername.getText().toString().toLowerCase();
                String password = etPassword.getText().toString();
                String passwordRetype = etPasswordRetype.getText().toString();

                if (!password.equals(passwordRetype))
                    Toast.makeText(NewUserActivity.this, getResources().getString(R.string.passwords_do_not_match), Toast.LENGTH_LONG).show();
                else {
                    if (dbHelper.checkUsernameAvailability(username)) {
                        UserObj newUser = new UserObj(firstName, lastName, username, password);
                        long userId = dbHelper.createNewUser(newUser);
                        if (userId != -1) {
                            newUser.setId(Long.valueOf(userId).intValue());
                            Intent logsActivityIntent = new Intent(NewUserActivity.this, StartFinishShiftActivity.class);
                            logsActivityIntent.putExtra("user", newUser);
                            startActivity(logsActivityIntent);
                            finish();
                        }
                    } else
                        Toast.makeText(NewUserActivity.this, getResources().getString(R.string.username_not_available), Toast.LENGTH_LONG).show();
                }

            }
        });


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                checkUsernameAndPasswordValidity();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                checkUsernameAndPasswordValidity();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkUsernameAndPasswordValidity();
            }
        };

        etUsername.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
        etPasswordRetype.addTextChangedListener(watcher);

    }

    private void checkUsernameAndPasswordValidity() {
//        if (etUsername.length() > 0 && etPassword.length() > 0 && etPasswordRetype.length() > 0) {
            if ((etUsername.length() >= 8 && etUsername.length() <= 24)) {
                tvUserLong.setVisibility(View.INVISIBLE);
                if (etPassword.length() > 0 || etPasswordRetype.length() > 0) {
                    if (isStrong(etPassword.getText().toString())) {
                        btnCreateUser.setEnabled(true);
                        tvPassStrength.setVisibility(View.INVISIBLE);
                    } else {
                        tvPassStrength.setText(getResources().getString(R.string.password_not_strong));
                        tvPassStrength.setVisibility(View.VISIBLE);

                    }
                }
            } else if (etUsername.length() > 0 && etUsername.length() < 8) {
                tvUserLong.setText(getResources().getString(R.string.username_not_long));
                tvUserLong.setVisibility(View.VISIBLE);
            } else if ((etUsername.length()) > 24) {
                tvUserLong.setText(getResources().getString(R.string.username_too_long));
                tvUserLong.setVisibility(View.VISIBLE);
            } else if (etUsername.length() == 0 || etPassword.length() == 0 || etPasswordRetype.length() == 0) {
                btnCreateUser.setEnabled(false);
            }
    }

    private boolean isStrong(String password) {
        if
                (password.matches("^(?=.*[0-9])(?=.*[a-z]).{8,24}$") ||
                password.matches("^(?=.*[A-Z])(?=.*[0-9]).{8,24}$") ||
                password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,24}$"))

        {
            return true;
        } else
            return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu_new_user_activity, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}
