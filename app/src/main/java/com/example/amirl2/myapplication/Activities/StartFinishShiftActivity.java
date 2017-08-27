package com.example.amirl2.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amirl2.myapplication.Accessories.DBHelper;
import com.example.amirl2.myapplication.Accessories.LogObj;
import com.example.amirl2.myapplication.Accessories.UserObj;
import com.example.amirl2.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartFinishShiftActivity extends AppCompatActivity {

    TextView tvDate, tvWelcomeUser;
    Switch switchInOut;
    Button btnSetLog;
    Toolbar toolbar;

    DBHelper dbHelper;
    UserObj userObj;
    LogObj logObj;
    String currentDate, currentDay, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_finish_shift);

        tvWelcomeUser = (TextView) findViewById(R.id.tv_welcome_user);
        tvDate = (TextView) findViewById(R.id.tv_date);
        switchInOut = (Switch) findViewById(R.id.switch_in_out);
        btnSetLog = (Button) findViewById(R.id.btn_set_log);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        dbHelper = new DBHelper(this);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        currentDate = date.format(calendar.getTime());
        currentDay = day.format(calendar.getTime());
        currentTime = time.format(calendar.getTime());
        tvDate.setText(currentDay + " " + currentDate);

        Bundle data = getIntent().getExtras();
        userObj = data.getParcelable("user");
        tvWelcomeUser.setText("Welcome " + userObj.getName());
        LogObj entryLogForToday = dbHelper.getLogForUserByDate(userObj.id, currentDate);

        if (entryLogForToday.id != 0) {
            switchInOut.setChecked(false);
            switchInOut.setText("Finish Shift");
            btnSetLog.setText("Finish");
            logObj = entryLogForToday;
        }

        switchInOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switchInOut.setText("Start Shift");
                    btnSetLog.setText("Start");
                } else if (!isChecked) {
                    switchInOut.setText("Finish Shift");
                    btnSetLog.setText("Finish");
                }
            }
        });

        btnSetLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                currentTime = time.format(calendar.getTime());

                if (switchInOut.isChecked()) {
                    logObj = new LogObj(currentDate, currentTime, null);
                    boolean inserted = dbHelper.insertCurrentEntryLog(logObj, userObj);
                    if (inserted)
                        Toast.makeText(StartFinishShiftActivity.this, "The shift has been inserted", Toast.LENGTH_LONG).show();
                    Toast.makeText(StartFinishShiftActivity.this, getResources().getString(R.string.good_luck_in_your_shift), Toast.LENGTH_LONG).show();
                    switchInOut.setChecked(false);
                    btnSetLog.setText("Finish");
                } else {
                    LogObj entryLogForToday = dbHelper.getLogForUserByDate(userObj.id, currentDate);
                    entryLogForToday.setExitTime(currentTime);
                    dbHelper.updateCurrentExitLog(entryLogForToday);
                    Toast.makeText(StartFinishShiftActivity.this, getResources().getString(R.string.see_you_soon), Toast.LENGTH_LONG).show();
                    switchInOut.setChecked(true);
                    btnSetLog.setText("Start");
                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_logout:
                StartFinishShiftActivity.this.finish();
                return true;

            case R.id.action_logs:
                Intent logsActivityIntent = new Intent(StartFinishShiftActivity.this, LogsActivity.class);
                logsActivityIntent.putExtra(getResources().getString(R.string.extra_user_obj), userObj);
                startActivity(logsActivityIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
