package com.example.amirl2.myapplication.Accessories;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by AmirL2 on 8/4/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WorkTimeDB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_USERNAME = "username";
    public static final String USERS_COLUMN_PASSWORD = "password";

    public static final String LOGS_TABLE_NAME = "logs";
    public static final String LOGS_COLUMN_ID = "id";
    public static final String LOGS_COLUMN_DATE = "date";
    public static final String LOGS_COLUMN_ENTRY_TIME = "entry_time";
    public static final String LOGS_COLUMN_EXIT_TIME = "exit_time";
    public static final String LOGS_COLUMN_FK_USERS_USER_ID = "user_id";
    public Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + USERS_TABLE_NAME
                        + " ( " + USERS_COLUMN_ID + " integer primary key autoincrement, "
                        + USERS_COLUMN_NAME + " text, " + USERS_COLUMN_USERNAME + " text, " + USERS_COLUMN_PASSWORD + " text )"
        );

        db.execSQL(
                "create table " + LOGS_TABLE_NAME
                        + " ( " + LOGS_COLUMN_ID + " integer primary key autoincrement, " + LOGS_COLUMN_DATE
                        + " text, " + LOGS_COLUMN_ENTRY_TIME + " text, " + LOGS_COLUMN_EXIT_TIME + " text, "
                        + LOGS_COLUMN_FK_USERS_USER_ID + " integer, "
                        + " foreign key " + "(" + LOGS_COLUMN_FK_USERS_USER_ID + ")" + " references " + USERS_TABLE_NAME + " ( " + USERS_COLUMN_ID + " ))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);
    }

    public void createNewLog() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());
    }

    public boolean createNewUser(UserObj user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, user.getName());
        contentValues.put(USERS_COLUMN_USERNAME, user.getUsername());
        contentValues.put(USERS_COLUMN_PASSWORD, user.getPassword());
        db.insert(USERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public LogObj getLogForUserByDate(int id, String date) {
        LogObj logObj = new LogObj();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LOGS_TABLE_NAME + " where " + LOGS_COLUMN_DATE + "='" + date + "' and " + LOGS_COLUMN_FK_USERS_USER_ID + "=" + id + ";", null);
        if (res != null) {
            res.moveToFirst();
            logObj.setId((res.getInt(res.getColumnIndex(LOGS_COLUMN_ID))));
            logObj.setDate((res.getString(res.getColumnIndex(LOGS_COLUMN_DATE))));
            logObj.setEntryTime((res.getString(res.getColumnIndex(LOGS_COLUMN_ENTRY_TIME))));
            logObj.setExitTime((res.getString(res.getColumnIndex(LOGS_COLUMN_EXIT_TIME))));
        }
        return logObj;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateCurrentExitLog(LogObj logObj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGS_COLUMN_EXIT_TIME, logObj.exitTime);
        db.update(LOGS_TABLE_NAME, contentValues, LOGS_COLUMN_ID + " = " + logObj.id, null);
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<UserObj> getAllUsers() {
        ArrayList<UserObj> usersList = new ArrayList<UserObj>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + USERS_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            UserObj user = new UserObj();
            user.setId((res.getInt(res.getColumnIndex(USERS_COLUMN_ID))));
            user.setName((res.getString(res.getColumnIndex(USERS_COLUMN_NAME))));
            user.setUsername((res.getString(res.getColumnIndex(USERS_COLUMN_USERNAME))));
            user.setPassword((res.getString(res.getColumnIndex(USERS_COLUMN_PASSWORD))));
            usersList.add(user);
            res.moveToNext();
        }
        return usersList;
    }


    public int validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_USERNAME + "='" + username + "' AND " + USERS_COLUMN_PASSWORD + "='" + password + "'", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            int userId = res.getInt(res.getColumnIndex(USERS_COLUMN_ID));
            return userId;
        } else
            return 0;
    }

    public UserObj getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_ID + "='" + userId + "';", null);
        res.moveToFirst();
        String name = res.getString(res.getColumnIndex(USERS_COLUMN_NAME));
        String username = res.getString(res.getColumnIndex(USERS_COLUMN_USERNAME));
        String password = res.getString(res.getColumnIndex(USERS_COLUMN_PASSWORD));
        UserObj user = new UserObj(userId, name, username, password);
        return user;
    }

    public boolean insertCurrentEntryLog(LogObj logObj, UserObj userObj) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGS_COLUMN_DATE, logObj.date);
        contentValues.put(LOGS_COLUMN_ENTRY_TIME, logObj.entryTime);
        contentValues.put(LOGS_COLUMN_EXIT_TIME, logObj.exitTime);
        contentValues.put(LOGS_COLUMN_FK_USERS_USER_ID, userObj.id);
        long inserted = db.insert(LOGS_TABLE_NAME, null, contentValues);

        if (inserted == -1)
            return false;
        return true;
    }
//``
//    public ArrayList<String> getAllUsers() {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from contacts", null );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_NAME)));
//            res.moveToNext();
//        }
//        return array_list;
//    }


}