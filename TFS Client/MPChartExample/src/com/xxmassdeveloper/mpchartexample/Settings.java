package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hmoussa on 13/05/2015.
 */
public class Settings extends Activity {
    private CharSequence mTitle;
    TextView textView;
    AutoCompleteTextView txt_username;
    EditText txt_password;
    TextView txt_domain;
    Button change_team_btn;
    String NAMESPACE = "http://tempuri.org/";
    String METHOD = "GetCollectionList";
    String URL = Global_Values.URL + "Service/TFSClient_Serv.svc?wsdl";
    String SOAP_ACTION_PREFIX = "http://tempuri.org/TFSClient_IServ/GetCollectionList";
    String obj_username = "";
    String obj_password = "";
    String obj_domain = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        change_team_btn = (Button) findViewById(R.id.login_ChangeteamProject_button);
        change_team_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner5 runner = new AsyncTaskRunner5();
                //textView.setText("Begin Connecting to TFS...");
                runner.execute();
            }
        });
    }
    void ShowColectionAlert(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                Settings.this);
        builderSingle.setIcon(R.drawable.ic_drawer);
        builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                Settings.this,
                android.R.layout.select_dialog_singlechoice);
        String TempCollectionPrefix = "Collection: http://10.102.3.161:8080/tfs/";
        for(int i=0;i<collectionlist.size();i++){
            if(collectionlist.get(i).toString().contains(TempCollectionPrefix)){
                arrayAdapter.add(collectionlist.get(i).toString().substring(TempCollectionPrefix.length()));
            }
        }

        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setTitle("Team Collections");
        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        /*AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                MainActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();*/
                        ShowTeamProjectAlert(strName);
                    }
                });
        builderSingle.show();
    }

    void ShowTeamProjectAlert(final String collectionName){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                Settings.this);
        builderSingle.setIcon(R.drawable.ic_drawer);
        builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                Settings.this,
                android.R.layout.select_dialog_singlechoice);
        String TempCollectionPrefix = "Collection: http://10.102.3.161:8080/tfs/" ;
        String TempProjectPrefix = " Team Project: ";
        int startprojectIndex = 0;
        for(int i=0;i<collectionlist.size();i++){
            if(collectionlist.get(i).toString().contains(TempCollectionPrefix + collectionName)){
                //arrayAdapter.add(collectionlist.get(i).toString().substring(TempCollectionPrefix.length()));
                startprojectIndex = i+1;
            }
        }
        for(int i = startprojectIndex; i<collectionlist.size();i++){
            if(collectionlist.get(i).toString().contains(TempCollectionPrefix)){
                break;
            }else {
                arrayAdapter.add(collectionlist.get(i).toString().substring(TempProjectPrefix.length()-1));
            }
        }
        builderSingle.setTitle(collectionName + " Team Projects");
        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ShowColectionAlert();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                Settings.this);
                        builderInner.setMessage("Team Project: " + strName);
                        builderInner.setTitle("Project Collection: " + collectionName);
                        Global_Values.collection = collectionName;
                        Global_Values.teamProject = strName;
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        Intent mainIntent = new Intent(Settings.this,MainMenu.class);
                                        Settings.this.startActivity(mainIntent);
                                        Settings.this.finish();
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }

    List<String> collectionlist;
    void CreateCollections(String src){
        String strarr[] =  src.split("||");
        collectionlist = Arrays.asList(src.split("\\|\\|"));
    }

    private class AsyncTaskRunner5 extends AsyncTask<String, String, String> {

        private String resp;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Loading contents..."); // Calls onProgressUpdate()
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD);
                request.addProperty("userName",Global_Values.userName);
                request.addProperty("password",Global_Values.password);
                request.addProperty("domain",Global_Values.domain);
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
            } catch (Exception e) {
                resp = e.getMessage();
            }

            return resp;
        }

        /**
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            //textView.setText("Result: \n" + result);
            CreateCollections(result);
            ShowColectionAlert();
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
           // textView.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
