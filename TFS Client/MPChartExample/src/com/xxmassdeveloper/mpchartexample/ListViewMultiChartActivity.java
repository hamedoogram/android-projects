
package com.xxmassdeveloper.mpchartexample;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xxmassdeveloper.mpchartexample.listviewitems.BarChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.ChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.LineChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.PieChartItem;
import com.xxmassdeveloper.mpchartexample.listviewitems.PieChartItem2;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 * 
 * @author Philipp Jahoda
 */
public class ListViewMultiChartActivity extends DemoBase {

    String NAMESPACE = "http://tempuri.org/";
    String METHOD = "NumberOFOpenedTasksPerTeam";
    String METHOD_WIStatus = "GetAllCreatedBugWIPerStatus";
    String METHOD_WITopTen = "DelayedTasks";
    String METHOD_Department_GetData = "TaskListPerTeam_GetAllValue";
    String URL = Global_Values.URL + "Service/TFSClient_Serv.svc?wsdl";
    String SOAP_ACTION_PREFIX = "http://tempuri.org/TFSClient_IServ/NumberOFOpenedTasksPerTeam";
    String SOAP_ACTION_PREFIX_WIStatus = "http://tempuri.org/TFSClient_IServ/GetAllCreatedBugWIPerStatus";
    String SOAP_ACTION_PREFIX_WIStatus_GetData = "http://tempuri.org/TFSClient_IServ/GetAllCreatedBugWIPerStatus_GetAllValue";
    String SOAP_ACTION_PREFIX_WITopTen = "http://tempuri.org/TFSClient_IServ/DelayedTasks";
    String SOAP_ACTION_PREFIX_Department_GetData = "http://tempuri.org/TFSClient_IServ/TaskListPerTeam_GetAllValue";
    ListView lv;
    ArrayList<String> teamname;
    ArrayList<String> teamvalue;
    ArrayList<String> statusname;
    ArrayList<String> statusvalue;
    ArrayList<ArrayList<String>> collectionlist;
    ArrayList<ArrayList<String>> collectionlist_aftercounting;

    ArrayList<ArrayList<String>> Department_collectionlist;
    ArrayList<ArrayList<String>> Status_collectionlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);
        
        lv = (ListView) findViewById(R.id.listView1);

        teamname = new ArrayList<String>();
        teamvalue = new ArrayList<String>();
        statusname = new ArrayList<String>();
        statusvalue = new ArrayList<String>();
        collectionlist = new ArrayList<ArrayList<String>>();
        collectionlist_aftercounting = new ArrayList<ArrayList<String>>();
        Department_collectionlist = new ArrayList<ArrayList<String>>();
        Status_collectionlist = new ArrayList<ArrayList<String>>();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        //textView.setText("Begin Connecting to TFS...");
        runner.execute();

        AsyncTaskRunner_WIStatus runner_WIStatus = new AsyncTaskRunner_WIStatus();
        //textView.setText("Begin Connecting to TFS...");
        runner_WIStatus.execute();

        AsyncTaskRunner_WITopTen runner_WITopTen = new AsyncTaskRunner_WITopTen();
        //textView.setText("Begin Connecting to TFS...");
        runner_WITopTen.execute();

    }

    public void draw(){
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        //list.add(new PieChartItem(generateDataPie(1, teamname, teamvalue), getApplicationContext()));

        // 30 items
        for (int i = 0; i < 30; i++) {

            if(i % 3 == 0) {
                //list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
            } else if(i % 3 == 1) {
                //list.add(new BarChartItem(generateDataBar(i + 1), getApplicationContext()));
            } else if(i % 3 == 2) {
                list.add(new PieChartItem(generateDataPie(1,teamname,teamvalue), getApplicationContext()));
                list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
                list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
                ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                lv.setAdapter(cda);
            }
        }


    }

    public void draw_WIStatus(int status){
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        //list.add(new PieChartItem(generateDataPie(2,statusname,statusvalue), getApplicationContext()));
        Global_Values.collectionlist = this.collectionlist;
        Global_Values.Department_collectionlist = this.Department_collectionlist;
        Global_Values.Status_collectionlist = this.Status_collectionlist;
        Global_Values.teamname = this.teamname;
        Global_Values.teamvalue = this.teamvalue;
        Global_Values.statusname = this.statusname;
        Global_Values.statusvalue = this.statusvalue;
        Global_Values.activity = this;
        if(status == 1){
            list.add(new BarChartItem(generateDataBar_TopTen(1), this));
            list.add(new PieChartItem(generateDataPie(1, teamname, teamvalue), getApplicationContext()));
            list.add(new PieChartItem2(generateDataPie(2, statusname, statusvalue), getApplicationContext()));
            ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
            lv.setAdapter(cda);
        }else if(status == 2){

        }else if(status == 3) {

        }
        // 30 items
        /*for (int i = 0; i < 30; i++) {

            if(i % 3 == 0) {
                list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
            } else if(i % 3 == 1) {
                list.add(new BarChartItem(generateDataBar(i + 1), getApplicationContext()));
            } else if(i % 3 == 2) {
                list.add(new PieChartItem(generateDataPie(1,teamname,teamvalue), getApplicationContext()));
            }
        }*/

    }

    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {
        
        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }
        
        @Override
        public int getItemViewType(int position) {           
            // return the views type
            return getItem(position).getItemType();
        }
        
        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        
        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleSize(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);
        
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        sets.add(d2);
        
        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < collectionlist.size(); i++) {
            //entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
            entries.add(new BarEntry(i*10, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setBarSpacePercent(10f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }

    private BarData generateDataBar_TopTen(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        occurrences_count();
        for (int i = 0; i < collectionlist_aftercounting.size(); i++) {
            //entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
            entries.add(new BarEntry(Integer.parseInt(collectionlist_aftercounting.get(i).get(1).toString()), i));
        }

        BarDataSet d = new BarDataSet (entries, "New DataSet " + cnt);
        d.setBarSpacePercent(10f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }
    
    /**
     * generates a random ChartData object with just one DataSet
     * 
     * @return
     */
    private PieData generateDataPie(int cnt, ArrayList<String> name, ArrayList<String> value) {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for(int i=0; i<value.size();i++){
            entries.add(new Entry(Integer.parseInt(value.get(i)),i));
        }

        PieDataSet d = new PieDataSet(entries, "");
        
        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        
        PieData cd = new PieData(getQuarters(name), d);
        return cd;
    }
    
    private ArrayList<String> getQuarters(ArrayList<String> name) {
        
        ArrayList<String> q = new ArrayList<String>();
        for(int i=0; i<name.size();i++){
            q.add((name.get(i)));
        }
        
        return q;
    }

    private void occurrences_count(){
        ArrayList<String> names = new ArrayList<String>();
        int count = 0;
        for(int i=0;i<collectionlist.size();i++){
            ArrayList<String> countingArray = new ArrayList<String>();

            for(int x=0;x<collectionlist.size();x++){
                if (collectionlist.get(i).get(0).toString().equals(collectionlist.get(x).get(0).toString())){
                    count++;
                }
                //countingArray.clear();
            }
            if(names.contains(collectionlist.get(i).get(0).toString()) == false){
            countingArray.add(collectionlist.get(i).get(0).toString());
            countingArray.add(String.valueOf(count));
            collectionlist_aftercounting.add(countingArray);
            names.add(collectionlist.get(i).get(0).toString());
            }
            count=0;
        }

        Global_Values.collectionlist_aftercounting = this.collectionlist_aftercounting;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        for(int i =0; i<collectionlist_aftercounting.size();i++){
            m.add(collectionlist_aftercounting.get(i).get(0).toString());
        }
        /*m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");
*/
        return m;
    }
    List<String> bugs_list_Name;
    List<String> bugs_list_Value;

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        private SoapObject result_soap;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Loading contents..."); // Calls onProgressUpdate()
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD);
                request.addProperty("userName",Global_Values.userName);
                request.addProperty("password",Global_Values.password);
                request.addProperty("domain",Global_Values.domain);
                request.addProperty("collectionName",Global_Values.collection);
                request.addProperty("projectName",Global_Values.teamProject);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION_PREFIX, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                // to get the data
                String resultData = result.toString();
                resp = resultData;
                // 0 is the first object of data

                ///Get All Data

                SoapObject request2 = new SoapObject(NAMESPACE, METHOD_Department_GetData);
                request2.addProperty("userName",Global_Values.userName);
                request2.addProperty("password",Global_Values.password);
                request2.addProperty("domain",Global_Values.domain);
                request2.addProperty("collectionName",Global_Values.collection);
                request2.addProperty("projectName",Global_Values.teamProject);
                SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope2.dotNet = true;
                envelope2.setOutputSoapObject(request2);
                HttpTransportSE androidHttpTransport2 = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION_PREFIX_Department_GetData, envelope2);
                SoapObject result2 = (SoapObject) envelope2.getResponse();
                result_soap = result2;
            } catch (Exception e) {
                resp = e.getMessage();
            }

            return resp;
        }

        /**
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */


        List<String> collectionlist;
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            //String strarr[] =  result.split("--");
           //
            collectionlist = Arrays.asList(result.split("\\|\\|"));
            for(int i=0;i<collectionlist.size();i++){
                String name = collectionlist.get(i).split(":")[0];
                String value = collectionlist.get(i).split(":")[1];
                teamname.add(name);
                teamvalue.add(value);
            }
            if(teamvalue.get(0) == "0" && teamvalue.get(1) == "0" && teamvalue.get(2) == "0" && teamvalue.get(3) == "0"){}
            else{
                for(int i = 0; i<result_soap.getPropertyCount();i++){
                    ArrayList<String> strData = new ArrayList<String>();
                    for(int y = 0;y<4;y++){

                        strData.add(((SoapObject)result_soap.getProperty(i)).getProperty(y).toString());
                    }
                    Department_collectionlist.add(strData);
                }
            }

            draw_WIStatus(2);
        }

        /**
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }
        /**
         *
         * @see android.os.AsyncTask onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            //textView.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }

    private class AsyncTaskRunner_WIStatus extends AsyncTask<String, String, String> {

        private SoapObject result_soap;
        private String resp;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Loading contents..."); // Calls onProgressUpdate()
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_WIStatus);
                request.addProperty("userName",Global_Values.userName);
                request.addProperty("password",Global_Values.password);
                request.addProperty("domain",Global_Values.domain);
                request.addProperty("collectionName",Global_Values.collection);
                request.addProperty("projectName",Global_Values.teamProject);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION_PREFIX_WIStatus, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                // to get the data
                String resultData = result.toString();
                resp = resultData;


                SoapObject request2 = new SoapObject(NAMESPACE, "GetAllCreatedBugWIPerStatus_GetAllValue");
                request2.addProperty("userName",Global_Values.userName);
                request2.addProperty("password",Global_Values.password);
                request2.addProperty("domain",Global_Values.domain);
                request2.addProperty("collectionName",Global_Values.collection);
                request2.addProperty("projectName",Global_Values.teamProject);
                SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope2.dotNet = true;
                envelope2.setOutputSoapObject(request2);
                HttpTransportSE androidHttpTransport2 = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION_PREFIX_WIStatus_GetData, envelope2);
                SoapObject result2 = (SoapObject) envelope2.getResponse();
                result_soap = result2;
                // 0 is the first object of data
            } catch (Exception e) {
                resp = e.getMessage();
            }

            return resp;
        }

        /**
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */


        List<String> collectionlist;
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            //String strarr[] =  result.split("--");
            //
            collectionlist = Arrays.asList(result.split("\\|\\|"));
            for(int i=0;i<collectionlist.size();i++){
                String name = collectionlist.get(i).split(":")[0];
                String value = collectionlist.get(i).split(":")[1];
                statusname.add(name);
                statusvalue.add(value);
            }
            if(statusvalue.get(0) == "0" && statusvalue.get(1) == "0" && statusvalue.get(2) == "0" && statusvalue.get(3) == "0" ){}
            else{
            for(int i = 0; i<result_soap.getPropertyCount();i++){
                ArrayList<String> strData = new ArrayList<String>();
                for(int y = 0;y<4;y++){

                    strData.add(((SoapObject)result_soap.getProperty(i)).getProperty(y).toString());
                }
                Status_collectionlist.add(strData);
            }
            }

            draw_WIStatus(3);
        }

        /**
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }
        /**
         *
         * @see android.os.AsyncTask onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            //textView.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }

    private class AsyncTaskRunner_WITopTen extends AsyncTask<String, String, String> {

        private String resp;
        private SoapObject result_soap;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Loading contents..."); // Calls onProgressUpdate()

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_WITopTen);
                request.addProperty("userName",Global_Values.userName);
                request.addProperty("password",Global_Values.password);
                request.addProperty("domain",Global_Values.domain);
                request.addProperty("collectionName",Global_Values.collection);
                request.addProperty("projectName",Global_Values.teamProject);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION_PREFIX_WITopTen, envelope);
                SoapObject result = (SoapObject) envelope.getResponse();
                // to get the data
                //List<String[]> reslist = (List<String[]>)result;
                String resultData = result.toString();
                resp = resultData;
                result_soap = result;
                // 0 is the first object of data
            } catch (Exception e) {
                resp = e.getMessage();
            }

            return "";
        }

        /**
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */



        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            String str = result_soap.getPropertyAsString(0);
            for(int i = 0; i<result_soap.getPropertyCount();i++){
                ArrayList<String> strData = new ArrayList<String>();
                for(int y = 0;y<4;y++){

                    strData.add(((SoapObject)result_soap.getProperty(i)).getProperty(y).toString());
                }
                collectionlist.add(strData);
            }
            //String strarr[] =  result.split("--");
            //
            //collectionlist = Arrays.asList(result.split("\\|\\|"));
            //for(int i=0;i<collectionlist.size();i++){
               // String name = collectionlist.get(i).split(":")[0];
               // String value = collectionlist.get(i).split(":")[1];
               // statusname.add(name);
              //  statusvalue.add(value);
            //}
            draw_WIStatus(1);
        }

        /**
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }
        /**
         *
         * @see android.os.AsyncTask onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            //textView.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
