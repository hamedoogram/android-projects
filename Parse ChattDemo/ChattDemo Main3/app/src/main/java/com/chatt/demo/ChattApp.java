package com.chatt.demo;

import android.app.Application;

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

		Parse.initialize(this, "VWpQ4wegz7C94II1V1gxIDbVUyO53m4l1PBXTIJH",
				"jCdHxhBK7buXKmMq3AUCwyqKUmEtAoHBuC8Sulp9");

	}
}
