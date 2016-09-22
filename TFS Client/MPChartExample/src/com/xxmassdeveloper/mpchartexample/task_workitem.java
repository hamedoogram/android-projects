package com.xxmassdeveloper.mpchartexample;

/**
 * Created by hmoussa on 13/05/2015.
 */
public class task_workitem {
    public String Title;
    public String Assignto;
    public String CreatedDate;
    public String Description;

    public task_workitem(String Title,String AssignTo, String CreatedDate,String Description){
        this.Title = Title;
        this.Assignto = AssignTo;
        this.CreatedDate = CreatedDate;
        this.Description = Description;

    }
}
