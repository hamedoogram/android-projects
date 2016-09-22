package com.ksu.team.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmoussa on 21/02/2015.
 */
    class CustomAdapter extends ArrayAdapter<Call> {

        private final Context context;
        private final List<Call> itemsArrayList;

        public CustomAdapter(Context context, List<Call> itemsArrayList) {

            super(context, R.layout.listcell, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.listcell, parent, false);

            // 3. Get the two text view from the rowView
            TextView labelView = (TextView) rowView.findViewById(R.id.log_description);
            TextView valueView = (TextView) rowView.findViewById(R.id.log_title);

            // 4. Set the text for textView
            labelView.setText(itemsArrayList.get(position).getPath());
            valueView.setText(itemsArrayList.get(position).getPhoneNumber());

            // 5. retrn rowView
            return rowView;
        }
    }