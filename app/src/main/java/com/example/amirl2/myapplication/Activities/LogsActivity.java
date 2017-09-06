package com.example.amirl2.myapplication.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amirl2.myapplication.Accessories.DBHelper;
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

        for (int i = 0; i < listLogs.size(); ++i) {
            String date = listLogs.get(i).getDate();
            String entryTime = "Start time: ";
            String exitTime = "End time: ";
            String totalTime = "Total shift time: ";

            if (listLogs.get(i).getEntryTime() != null)
                entryTime = entryTime + listLogs.get(i).getEntryTime();
            if (listLogs.get(i).getExitTime() != null)
                exitTime = exitTime + listLogs.get(i).getExitTime();
            if (listLogs.get(i).getTotalTime() != null)
                totalTime = totalTime + listLogs.get(i).getTotalTime();

            logListRowObjs.add(new LogListRowObj(date, entryTime, exitTime, totalTime));
        }

        listAdapter = new ListAdapter(logListRowObjs, getApplicationContext());
        lvLogs.setAdapter(listAdapter);
        lvLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogListRowObj logListRowObj = logListRowObjs.get(position);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
//!!!!!!!!!!!!!!!!!!!!!!!!Works well!!!!!!!!!!!!!!!!!!!!!!
//        PdfDocument document = new PdfDocument();
//
//        // repaint the user's text into the page
//        View content = findViewById(R.id.tv_logs_header);
//
//        // crate a page description
//        int pageNumber = 1;
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
//                content.getHeight() - 20, pageNumber).create();
//
//        // create a new page from the PageInfo
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        content.draw(page.getCanvas());
//
//        // do final processing of the page
//        document.finishPage(page);
//
//        try {
//            outputFile.createNewFile();
//            OutputStream out = new FileOutputStream(outputFile);
//            document.writeTo(out);
//            document.close();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
        String pdfName = "TryTryLogsHistoryList"
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        File outputFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), pdfName);
        outputFile.getParentFile().mkdirs();
        OutputStream output = new FileOutputStream(outputFile);

        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, output);
        Toast.makeText(LogsActivity.this, outputFile.getAbsoluteFile() + "", Toast.LENGTH_LONG).show();

        document.open();
        document.add(new Paragraph("History Logs List - for user: " + userObj.getName()));
        document.add(new Paragraph("First row of the list!"));
        document.close();
//
//        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
//        File fileWithinMyDir = new File(outputFile.getPath());
//
//        if(fileWithinMyDir.exists()) {
//            intentShareFile.setType("application/pdf");
//            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/"+outputFile.getPath()));
//
//            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
//                    "Sharing File...");
//            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
//            intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            startActivity(Intent.createChooser(intentShareFile, "Share File"));
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
