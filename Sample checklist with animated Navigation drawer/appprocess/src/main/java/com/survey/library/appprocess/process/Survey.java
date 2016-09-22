package com.survey.library.appprocess.process;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class Survey {
    //private variables
    String Name;
    int Age, Gender, Migraines, Drugs, Result;

    public Survey(String Name, int Age, int Gender, int Migraines, int Drugs, int Result){
        // Set Params
        this.Name = Name; this.Age = Age; this.Gender = Gender; this.Migraines = Migraines; this.Drugs = Drugs; this.Result = Result;
    }
    // Getting Name
    public String getName(){
        return this.Name;
    }

    // Getting Age
    public int getAge(){
        return this.Age;
    }

    // Getting Gender
    public int getGender(){
        return this.Gender;
    }

    // Getting Migraines
    public int getMigraines(){
        return this.Migraines;
    }

    // Getting Drugs
    public int getDrugs(){
        return this.Drugs;
    }

    // Getting Result
    public int getResult(){
        return this.Result;
    }

}
