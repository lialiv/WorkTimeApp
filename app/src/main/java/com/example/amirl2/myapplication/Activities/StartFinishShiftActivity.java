package com.example.amirl2.myapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.amirl2.myapplication.Accessories.DBHelper;
import com.example.amirl2.myapplication.Accessories.LayoutAccess;
import com.example.amirl2.myapplication.Accessories.LogObj;
import com.example.amirl2.myapplication.Accessories.UserObj;
import com.example.amirl2.myapplication.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class StartFinishShiftActivity extends AppCompatActivity {

    TextView tvDate, tvWelcomeUser;
    Switch switchInOut;
    Button btnSetLog;
    EditText etNotes;
    Toolbar toolbar;

    DBHelper dbHelper;
    UserObj userObj;
    LogObj logObj;
    String currentDate, currentDay, currentTime;
    SimpleDateFormat time;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_finish_shift);

        tvWelcomeUser = (TextView) findViewById(R.id.tv_welcome_user);
        tvDate = (TextView) findViewById(R.id.tv_date);
        switchInOut = (Switch) findViewById(R.id.switch_in_out);
        btnSetLog = (Button) findViewById(R.id.btn_set_log);
        etNotes = (EditText) findViewById(R.id.et_notes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        dbHelper = new DBHelper(this);
//        toolbar.setLogo(R.drawable.clock_date);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("EEEE, dd-MMM-yy");
        time = new SimpleDateFormat(getResources().getString(R.string.time_pattern));
        currentDate = date.format(calendar.getTime());
        currentTime = time.format(calendar.getTime());
        tvDate.setText(currentDate);

        Bundle data = getIntent().getExtras();
        userObj = data.getParcelable(getResources().getString(R.string.extra_user));
        tvWelcomeUser.setText("Welcome " + userObj.getFirstName() + " " + userObj.getLastName());
        LogObj entryLogForToday = dbHelper.getLogForUserByDate(userObj.id, currentDate);

        if (entryLogForToday.id != 0) {
            switchInOut.setChecked(false);
            switchInOut.setText(getResources().getString(R.string.finish_shift));
            btnSetLog.setText(getResources().getString(R.string.finish));
            logObj = entryLogForToday;
            etNotes.setText(logObj.getNotes());
        }

        switchInOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switchInOut.setText(getResources().getString(R.string.start_shift));
                    btnSetLog.setText(getResources().getString(R.string.start));
                } else if (!isChecked) {
                    switchInOut.setText(getResources().getString(R.string.finish_shift));
                    btnSetLog.setText(getResources().getString(R.string.finish));
                }
            }
        });

        btnSetLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                currentTime = time.format(calendar.getTime());
                String note = "";
                note = etNotes.getText().toString();

                if (switchInOut.isChecked()) {
                    logObj = new LogObj(currentDate, currentTime, null, null, note);
                    dbHelper.insertCurrentEntryLog(logObj, userObj);
                    LayoutAccess.ToastMsgInflater(StartFinishShiftActivity.this, "Shift Started", getResources().getString(R.string.good_luck_in_your_shift));
                    switchInOut.setChecked(false);
                    btnSetLog.setText(getResources().getString(R.string.finish));
                } else {
                    LogObj entryLogForToday = dbHelper.getLogForUserByDate(userObj.id, currentDate);
                    entryLogForToday.setExitTime(currentTime);
                    String totalTime = getTimeDifference(entryLogForToday.getEntryTime(), entryLogForToday.getExitTime());
                    entryLogForToday.setTotalTime(totalTime);
                    note += entryLogForToday.getNotes();
                    entryLogForToday.setNotes(note);
                    dbHelper.updateCurrentExitLog(entryLogForToday);
                    LayoutAccess.ToastMsgInflater(StartFinishShiftActivity.this, "End Shift", getResources().getString(R.string.see_you_soon));

                    switchInOut.setChecked(true);
                    btnSetLog.setText(getResources().getString(R.string.start));
                    etNotes.setText("");
                }

            }

        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu_start_finish_activity, menu);
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

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public String getTimeDifference(String entryTime, String exitTime) {

        DateTimeFormatter f = DateTimeFormatter.ofPattern(getResources().getString(R.string.time_pattern));
        LocalTime start = LocalTime.parse(entryTime, f);
        LocalTime stop = LocalTime.parse(exitTime, f);

        Duration d = Duration.between(start, stop);
        long minutes = d.toMinutes();

        String totalTime = LocalTime.MIN.plus(
                Duration.ofMinutes(minutes)
        ).toString();

        return totalTime;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("StartFinishShift Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
