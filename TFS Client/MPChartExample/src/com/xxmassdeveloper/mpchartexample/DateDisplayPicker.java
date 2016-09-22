package com.xxmassdeveloper.mpchartexample;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by hmoussa on 25/03/2015.
 */
public class DateDisplayPicker extends TextView implements DatePickerDialog.OnDateSetListener {
    private Context _context;

    public DateDisplayPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        _context = context;
    }

    public DateDisplayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        _context = context;
        setAttributes();
    }

    public DateDisplayPicker(Context context) {
        super(context);
        _context = context;
        setAttributes();
    }

    private void setAttributes() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(_context, this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(Color.WHITE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        setTextColor(Color.WHITE);
        setText(String.format("%s/%s/%s", monthOfYear, dayOfMonth, year));

    }
}
