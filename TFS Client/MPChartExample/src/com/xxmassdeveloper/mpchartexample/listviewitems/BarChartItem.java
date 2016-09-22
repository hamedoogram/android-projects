package com.xxmassdeveloper.mpchartexample.listviewitems;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.xxmassdeveloper.mpchartexample.Global_Values;
import com.xxmassdeveloper.mpchartexample.ListActivity_Delayed;
import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.TFSUsers;

import java.util.ArrayList;

public class BarChartItem extends ChartItem {
    
    private Typeface mTf;
    
    public BarChartItem(ChartData<?> cd, Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }



    @Override
    public View getView(int position, View convertView, final Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = (BarChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.setDescription("Top Persons Delayed Tasks");
        holder.chart.setDrawGridBackground(false);
        holder.chart.setDrawBarShadow(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        
        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5);
        leftAxis.setSpaceTop(20f);
       
        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5);
        rightAxis.setSpaceTop(20f);

        mChartData.setValueTypeface(mTf);

        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                final ArrayList<ArrayList<String>> collectionlist_filter = new ArrayList<ArrayList<String>>();

                int count = (int)e.getVal();
                for(int i =0; i<Global_Values.collectionlist.size();i++){
                    if(Global_Values.collectionlist_aftercounting.get(e.getXIndex()).get(0).equals(Global_Values.collectionlist.get(i).get(0))){
                        //arrayAdapter.add(Global_Values.collectionlist.get(i).get(1).toString());
                        collectionlist_filter.add(Global_Values.collectionlist.get(i));
                    }
                }

                Global_Values.collectionlist_filter = collectionlist_filter;
                Intent in = new Intent(Global_Values.activity, ListActivity_Delayed.class);
                Global_Values.activity.startActivity(in);

                //Toast.makeText(c, String.valueOf(e.getXIndex()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // set data
        holder.chart.setData((BarData) mChartData);
        
        // do not forget to refresh the chart
//        holder.chart.invalidate();
        holder.chart.animateY(700);

        return convertView;
    }
    
    private static class ViewHolder {
        BarChart chart;
    }
}
