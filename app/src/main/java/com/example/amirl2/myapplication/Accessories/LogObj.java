package com.example.amirl2.myapplication.Accessories;

/**
 * Created by AmirL2 on 8/11/2017.
 */

public class LogObj {

    public int id;
    public String date;
    public String entryTime;
    public String exitTime;

    public LogObj() {
    }

    public LogObj(int id, String date, String entryTime, String exitTime) {
        this.id = id;
        this.date = date;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public LogObj(String date, String exitTime) {
        this.date = date;
        this.exitTime = exitTime;
    }

    public LogObj(String date, String entryTime, String exitTime) {
        this.date = date;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }
}
