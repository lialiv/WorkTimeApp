package com.example.amirl2.myapplication.Accessories;

/**
 * Created by AmirL2 on 8/11/2017.
 */

public class LogObj {

    public int id;
    public String date;
    public String entryTime;
    public String exitTime;
    public String totalTime;
    public String notes;

    public LogObj() {
    }

    public LogObj(int id, String date, String entryTime, String exitTime, String totalTime, String notes) {
        this.id = id;
        this.date = date;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalTime = totalTime;
        this.notes = notes;
    }

    public LogObj(String date, String exitTime, String totalTime, String notes) {
        this.date = date;
        this.exitTime = exitTime;
        this.totalTime = totalTime;
        this.notes = notes;
    }

    public LogObj(String date, String entryTime, String exitTime, String totalTime, String notes) {
        this.date = date;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalTime = totalTime;
        this.notes = notes;

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

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
