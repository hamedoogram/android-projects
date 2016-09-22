package com.chatt.demo;

import android.app.Application;

import com.chatt.demo.utils.Const;
import com.parse.Parse;

/**
 * The Class ChattApp is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class ChattApp extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		//Parse.initialize(this, "mobiletrackapp","jCdHxhBK7buXKmMq3AUCwyqKUmEtAoHBuC8Sulp9");
		Const.conx = getApplicationContext();
		Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
				.applicationId("mobiletrackapp")
				.clientKey("")
				.server("http://192.168.1.2:1337/parse/")

				.build()
		);

	}
}
