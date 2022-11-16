package com.example.sep_socialful;

public class Event {
    String event_Address, event_Date, event_Name, event_Time;
    public Event(){

    }
    public Event(String eventDate, String eventName, String eventTime){
        this.event_Date = eventDate;
        this.event_Name = eventName;
        this.event_Time = eventTime;
    }

    public void setEvent_Address(String event_Address) {
        this.event_Address = event_Address;
    }

    public String getEvent_Address() {
        return event_Address;
    }

    public String getEvent_Date() {
        return event_Date;
    }

    public void setEvent_Date(String event_Date) {
        this.event_Date = event_Date;
    }

    public String getEvent_Name() {
        return event_Name;
    }

    public void setEvent_Name(String event_Name) {
        this.event_Name = event_Name;
    }

    public String getEvent_Time(){
        return event_Time;
    }

    public void setEvent_Time(String event_Time) {
        this.event_Time = event_Time;
    }
}