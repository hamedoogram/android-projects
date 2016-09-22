package com.xxmassdeveloper.mpchartexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListAdapter;
import android.widget.Spinner;

/**
 * Created by hmoussa on 25/03/2015.
 */
public class DialogSpinner extends Spinner {
    public DialogSpinner(Context context) {
        super(context);
    }

    @Override
    public boolean performClick() {
        new AlertDialog.Builder(getContext()).setAdapter((ListAdapter) getAdapter(),
                new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        setSelection(which);
                        dialog.dismiss();
                    }
                }).create().show();
        return true;
    }
}