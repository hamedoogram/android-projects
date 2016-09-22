package com.ksu.team.myapplication;

/**
 * Created by hmoussa on 22/02/2015.
 */
public class Call {
    int id;
    String PhoneNumber;
    String Path;
    String Duration;
    public Call()
    {}
    public Call(int id, String PhoneNumber, String Path, String Duration)
    {
        this.id = id; this.PhoneNumber = PhoneNumber; this.Path = Path; this.Duration = Duration;
    }

    public Call(String PhoneNumber, String Path, String Duration)
    {
        this.id = id;
        this.PhoneNumber = PhoneNumber;
        this.Path = Path;
        this.Duration = Duration;
    }
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    public String getPhoneNumber(){
        return this.PhoneNumber;
    }

    // setting id
    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }

    public String getPath(){
        return this.Path;
    }

    // setting id
    public void setPath(String Path){
        this.Path = Path;
    }

    public String getDuration(){
        return this.Duration;
    }

    // setting id
    public void setDuration(String Duration){
        this.Duration = Duration;
    }
}
