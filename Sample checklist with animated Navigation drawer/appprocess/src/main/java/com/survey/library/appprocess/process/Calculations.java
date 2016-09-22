package com.survey.library.appprocess.process;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class Calculations {
    //Todd Syndrome Final Result
    static int result = 0;
    // Calculate Result
    // Input the main 4 factors
    // Output the final result
    public static int calculateResults(int Age, int Gender, int Migraines, int Drugs){
        if(Age <= 15)
            result +=25;
        if(Gender == 1) // Gender = 1 = Male    0 = Female
            result +=25;
        if(Migraines == 1) // 1 = patient has = Migraines    0 = he hasn't
            result +=25;
        if(Drugs == 1) // 1 = patient take hallucinogenic drugs    0 = he hasn't
            result +=25;
        int finalResult = result;
        result = 0;
        return finalResult;
    }
}
