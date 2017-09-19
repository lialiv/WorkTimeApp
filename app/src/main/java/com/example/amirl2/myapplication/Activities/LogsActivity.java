package com.example.amirl2.myapplication.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.amirl2.myapplication.Accessories.DBHelper;
import com.example.amirl2.myapplication.Accessories.LayoutAccess;
import com.example.amirl2.myapplication.Accessories.ListAdapter;
import com.example.amirl2.myapplication.Accessories.LogListRowObj;
import com.example.amirl2.myapplication.Accessories.LogObj;
import com.example.amirl2.myapplication.Accessories.UserObj;
import com.example.amirl2.myapplication.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class LogsActivity extends AppCompatActivity {

    ListView lvLogs;
    UserObj userObj;

    ListAdapter listAdapter;
    DBHelper dbHelper;
    ArrayList<LogListRowObj> logListRowObjs;
    Toolbar toolbar;

    final int PERMISSION_GRANTED_WRITE_EXTERNAL_STORAGE = 1;
    final int PERMISSION_GRANTED_READ_EXTERNAL_STORAGE = 2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvLogs = (ListView) findViewById(R.id.lv_logs);

        userObj = new UserObj();
        dbHelper = new DBHelper(this);
        logListRowObjs = new ArrayList<>();

        Bundle data = getIntent().getExtras();
        userObj = data.getParcelable(getResources().getString(R.string.extra_user_obj));
        ArrayList<LogObj> listLogs = dbHelper.getLogsForUser(userObj.id);
        extractLogsList();


        for (int i = 0; i < listLogs.size(); ++i) {
            String date = listLogs.get(i).getDate();
            String entryTime = "";
            String exitTime = "";
            String totalTime = "";
            String notes = "";

            if (listLogs.get(i).getEntryTime() != null) {
                entryTime = entryTime + listLogs.get(i).getEntryTime();
            }
            if (listLogs.get(i).getExitTime() != null) {
                exitTime = exitTime + listLogs.get(i).getExitTime();
            }
            if (listLogs.get(i).getTotalTime() != null) {
                totalTime = totalTime + listLogs.get(i).getTotalTime();
            }
            if (listLogs.get(i).getNotes() != null) {
                notes = listLogs.get(i).getNotes();
            }

            logListRowObjs.add(new LogListRowObj(date, entryTime, exitTime, totalTime, notes));
        }

        listAdapter = new ListAdapter(logListRowObjs, getApplicationContext());
        lvLogs.setAdapter(listAdapter);
        lvLogs.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvLogs.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            private int numberLinesChosen = 0;

            @Override
            public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
                numberLinesChosen = 0;
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu_logs_activity, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_delete:
                        numberLinesChosen = 0;
                        listAdapter.clearSelection();
                        actionMode.finish();
                }
                    return false;
            }
            @Override
            public void onDestroyActionMode(android.view.ActionMode actionMode) {

            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode actionMode, int position, long l, boolean checked) {
                if (checked) {
                    numberLinesChosen++;
                    listAdapter.setNewSelection(position, checked);
                } else {
                    numberLinesChosen--;
                    listAdapter.removeSelection(position);
                }
                actionMode.setTitle(numberLinesChosen + " selected");

            }
        });


        lvLogs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {


                LogListRowObj logListRowObj = logListRowObjs.get(position);
                lvLogs.setItemChecked(position, !listAdapter.isPositionChecked(position));


                return false;
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    private ArrayList<LogObj> extractLogsList() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu_log_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_print_log:
                checkWriteDocumentPermission();
                return true;

            case R.id.action_logout:
                Intent mainActivityIntent = new Intent(LogsActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                LogsActivity.this.finish();
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu_logs_activity, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
//                    shareCurrentItem();
                    return true;
                case R.id.action_delete:
//                    shareCurrentItem();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
        }
    };

    private void checkWriteDocumentPermission() {
        if (ContextCompat.checkSelfPermission(LogsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(LogsActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_GRANTED_WRITE_EXTERNAL_STORAGE);

        } else
            try {
                createPdf();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    void createPdf() throws DocumentException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_hhmmss");
        String pdfName = "LogsHistoryList"
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        File outputFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), pdfName);
        outputFile.getParentFile().mkdirs();
        OutputStream output = new FileOutputStream(outputFile);

        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, output);
        LayoutAccess.ToastMsgInflater(LogsActivity.this, "Create and Save PDF", "Your history data has been saved in Documents folder");

        document.open();

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph title = new Paragraph("History Logs List - for " + userObj.getFirstName() + " " + userObj.getLastName(), boldFont);
        sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        Paragraph dateTime = new Paragraph("Created on: " + sdf.format(Calendar.getInstance().getTime()), boldFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(title));
        document.add(new Paragraph(dateTime));

        for (int i = 0; i < logListRowObjs.size(); i++) {
            document.add(new Paragraph(" "));
            LogListRowObj logListRowObj = logListRowObjs.get(i);
            document.add(new Paragraph(logListRowObj.getDate()));
            document.add(new Paragraph("Entry Time: " + logListRowObj.getEntryTime() + "   Exit Time: " + logListRowObj.getExitTime() + "   Total Time: " + logListRowObj.getTotalTime() + "   Notes: " + logListRowObj.getNotes()));
        }
        document.close();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Logs Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_GRANTED_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        createPdf();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

        }
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
