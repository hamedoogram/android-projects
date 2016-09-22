
package com.xxmassdeveloper.mpchartexample.listviewitems;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.xxmassdeveloper.mpchartexample.Global_Values;
import com.xxmassdeveloper.mpchartexample.Item;
import com.xxmassdeveloper.mpchartexample.MainMenu;
import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.TFSUsers;

import java.util.ArrayList;

public class PieChartItem2 extends ChartItem {

    private Typeface mTf;

    public PieChartItem2(ChartData<?> cd, Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null);
            holder.chart = (PieChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.setDescription("All Bugs per Status");
        holder.chart.setHoleRadius(52f);
        holder.chart.setTransparentCircleRadius(57f);
        holder.chart.setCenterText("MPChart\nAndroid");
        holder.chart.setCenterTextTypeface(mTf);
        holder.chart.setCenterTextSize(18f);
        holder.chart.setUsePercentValues(true);
        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                if(Global_Values.statusvalue.get(0) == "0" && Global_Values.statusvalue.get(1) == "0" && Global_Values.statusvalue.get(2) == "0" && Global_Values.statusvalue.get(3) == "0" ){}
                else {

                    ArrayList<ArrayList<String>> Selected_Status_collectionlist = new ArrayList<ArrayList<String>>();
                    String selectedstatus = Global_Values.statusname.get(e.getXIndex());
                    for(int i=0;i<Global_Values.Status_collectionlist.size();i++){
                        if(Global_Values.Status_collectionlist.get(i).get(2).equals(selectedstatus)){
                            Selected_Status_collectionlist.add(Global_Values.Status_collectionlist.get(i));
                        }
                    }

                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                            Global_Values.activity);
                    builderSingle.setIcon(R.drawable.ic_launcher);
                    builderSingle.setTitle("Select One Name:-");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            Global_Values.activity,
                            android.R.layout.select_dialog_singlechoice);

                    Global_Values.bug_state = Global_Values.statusname.get(e.getXIndex());
                    ArrayList<Item> users = new ArrayList<Item>();
                    ArrayList<String> users_check = new ArrayList<String>();
                    int wi_count = 0;
                    for (int i = 0; i < Selected_Status_collectionlist.size(); i++) {
                        String name = Selected_Status_collectionlist.get(i).get(0).toString();
                        if (users_check.contains(Selected_Status_collectionlist.get(i).get(0).toString()) == false) {

                            for (int y = 0; y < Selected_Status_collectionlist.size(); y++) {
                                if (name.equals(Selected_Status_collectionlist.get(y).get(0).toString()) && users_check.contains(Selected_Status_collectionlist.get(i).get(0).toString()) == false) {
                                    wi_count++;
                                }
                            }
                            users_check.add(Selected_Status_collectionlist.get(i).get(0).toString());
                            users.add(new Item(Selected_Status_collectionlist.get(i).get(0).toString(), String.valueOf(wi_count)));
                            wi_count = 0;
                        }
                    }
                    Global_Values.users_Statis = users;
                    Global_Values.stateORdepartement = 2;
                    Global_Values.Selected_Status_collectionlist = Selected_Status_collectionlist;
                    Intent in = new Intent(Global_Values.activity, TFSUsers.class);
                    Global_Values.activity.startActivity(in);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChartData.setValueFormatter(new PercentFormatter());
        mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.BLACK);
        // set data
        holder.chart.setData((PieData) mChartData);

        Legend l = holder.chart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateXY(900, 900);

        return convertView;
    }

    private static class ViewHolder {
        PieChart chart;
    }
}
