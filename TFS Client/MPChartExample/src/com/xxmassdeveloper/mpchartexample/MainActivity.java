package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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


public class MainActivity extends Activity{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     */
    private CharSequence mTitle;
    TextView textView;
    AutoCompleteTextView txt_username;
    EditText txt_password;
    TextView txt_domain;
    Button login_btn;
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
        setContentView(R.layout.activity_main_tfs);


        mTitle = getTitle();
        // Set up the drawer.

        textView = (TextView) findViewById(R.id.textView);
        txt_domain = (TextView) findViewById(R.id.login_Domain);
        txt_username = (AutoCompleteTextView) findViewById(R.id.login_username);
        txt_password = (EditText) findViewById(R.id.login_password);
        login_btn = (Button) findViewById(R.id.login_sign_in_button);
        try
        {
            SharedPreferences sp = getSharedPreferences("login", 0);
            String tdomain = sp.getString("textdomain", "");
            String tusername = sp.getString("txtusername", "");
            String tpassword = sp.getString("txtpassword", "");
            if (!tdomain.isEmpty() && !tusername.isEmpty() && !tpassword.isEmpty()) {
                txt_domain.setText(tdomain);
                txt_username.setText(tusername);
                txt_password.setText(tpassword);
            }

        }catch(Exception ex){
            Log.println(0,"",ex.getMessage());
        }
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj_username = txt_username.getText().toString();
                obj_password = txt_password.getText().toString();
                obj_domain = txt_domain.getText().toString();

                if(!obj_domain.isEmpty() && !obj_password.isEmpty() && !obj_username.isEmpty()){
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    textView.setText("Begin Connecting to TFS...");
                    runner.execute();
                }else{
                    textView.setText("Dear User Please Fill the empty Fields");
                    //ShowColectionAlert();
                    //Intent mainIntent = new Intent(MainActivity.this,ListActivity.class);
                    //MainActivity.this.startActivity(mainIntent);
                    //MainActivity.this.finish();
                }
            }
        });
    }

    void ShowColectionAlert(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_drawer);
        builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
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
                MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_drawer);
        builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
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
                                MainActivity.this);
                        builderInner.setMessage("Team Project: " + strName);
                        builderInner.setTitle("Project Collection: " + collectionName);
                        Global_Values.collection = collectionName;
                        Global_Values.teamProject = strName;
                        Global_Values.userName = txt_username.getText().toString();
                        Global_Values.password = txt_password.getText().toString();
                        Global_Values.domain = txt_domain.getText().toString();
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        Intent mainIntent = new Intent(MainActivity.this,MainMenu.class);
                                        MainActivity.this.startActivity(mainIntent);
                                        MainActivity.this.finish();
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

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Loading contents..."); // Calls onProgressUpdate()
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD);
                request.addProperty("userName",obj_username);
                request.addProperty("password",obj_password);
                request.addProperty("domain",obj_domain);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION_PREFIX, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                // to get the data
                String resultData = result.toString();
                resp = resultData;
                SharedPreferences sp = getSharedPreferences("login", 0);
                SharedPreferences.Editor sedt = sp.edit();
                sedt.putString("textdomain", obj_domain);
                sedt.putString("txtusername", obj_username);
                sedt.putString("txtpassword", obj_password);
                sedt.commit();
                // 0 is the first object of data
            } catch (Exception e) {
                resp = e.getMessage();
            }
            /*try {
                // SoapEnvelop.VER11 is SOAP Version 1.1 constant
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                SoapObject request = new SoapObject(NAMESPACE, METHOD);
                //bodyOut is the body object to be sent out with this envelope
                request.addProperty("userName","hmostafa");
                request.addProperty("password","1@1@-ffblack@fgtr");
                request.addProperty("domain","domain");
                envelope.bodyOut = request;
                HttpTransportSE transport = new HttpTransportSE(URL);
                try {
                    transport.call(NAMESPACE + SOAP_ACTION_PREFIX + METHOD, envelope);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                //bodyIn is the body object received with this envelope
                if (envelope.bodyIn != null) {
                    //getProperty() Returns a specific property at a certain index.
                    SoapPrimitive resultSOAP = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    resp=resultSOAP.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }*/

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
            textView.setText("Result: \n" + result);
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
            textView.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
