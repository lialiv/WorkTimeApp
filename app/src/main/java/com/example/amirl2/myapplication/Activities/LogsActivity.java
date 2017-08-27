package com.example.amirl2.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.amirl2.myapplication.Accessories.DBHelper;
import com.example.amirl2.myapplication.Accessories.ListAdapter;
import com.example.amirl2.myapplication.Accessories.LogListRowObj;
import com.example.amirl2.myapplication.Accessories.LogObj;
import com.example.amirl2.myapplication.Accessories.UserObj;
import com.example.amirl2.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class LogsActivity extends AppCompatActivity {

    ListView lvLogs;
    UserObj userObj;

    ListAdapter listAdapter;
    DBHelper dbHelper;
    ArrayList<LogListRowObj> logListRowObjs;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvLogs = (ListView) findViewById(R.id.lv_logs);

        userObj = new UserObj();
        dbHelper = new DBHelper(this);
        logListRowObjs = new ArrayList<>();

        Bundle data = getIntent().getExtras();
        userObj = data.getParcelable(getResources().getString(R.string.extra_user_obj));

        ArrayList<LogObj> listLogs = dbHelper.getLogsForUser(userObj.id);

        for (int i = 0; i < listLogs.size(); ++i) {
            String date = listLogs.get(i).getDate();
            String entryTime = "Enter: " + listLogs.get(i).getEntryTime();
            String exitTime = "Exit: " + listLogs.get(i).getExitTime();
            logListRowObjs.add(new LogListRowObj(date, entryTime, exitTime, "Total Time: "));
        }


        listAdapter = new ListAdapter(logListRowObjs, getApplicationContext());
        lvLogs.setAdapter(listAdapter);
        lvLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogListRowObj logListRowObj= logListRowObjs.get(position);
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
                Intent mainActivityIntent = new Intent(LogsActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                LogsActivity.this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}



