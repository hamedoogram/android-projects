package com.xxmassdeveloper.mpchartexample;

/**
 * Created by hmoussa on 13/05/2015.
 */
public class Item {
    public String mTitle;
    public String mDescription;

    public Item(String mTitle,String mDescription){
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public void setmTitle(String mTitle){
        this.mTitle = mTitle;
    }
    public String getmTitle(){
        return this.mTitle;
    }

    public void setmDescription(String mDescription){
        this.mDescription = mDescription;
    }
    public String getmDescription(){
        return mDescription;
    }
}
