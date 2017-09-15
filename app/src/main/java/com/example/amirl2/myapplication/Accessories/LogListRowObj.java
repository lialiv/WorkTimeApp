package com.example.amirl2.myapplication.Accessories;

/**
 * Created by AmirL2 on 8/26/2017.
 */

public class LogListRowObj {
    String date;
    String entryTime;
    String exitTime;
    String totalTime;
    String notes;

    public LogListRowObj(String date, String entryTime, String exitTime, String totalTime, String notes) {
        this.date=date;
        this.entryTime=entryTime;
        this.exitTime=exitTime;
        this.totalTime=totalTime;
        this.notes=notes;

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

