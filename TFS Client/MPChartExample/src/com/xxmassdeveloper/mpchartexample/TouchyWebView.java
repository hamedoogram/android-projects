package com.xxmassdeveloper.mpchartexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;

/**
 * Created by hmoussa on 26/05/2015.
 */
public class TouchyWebView extends WebView {

    public TouchyWebView(Context context) {
        super(context);
    }

    public TouchyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }



}