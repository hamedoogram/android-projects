package com.xxmassdeveloper.mpchartexample;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by hmoussa on 14/05/2015.
 */
public class GetAllUsers extends AsyncTask<String, String, String> {
    String NAMESPACE = "http://tempuri.org/";
    String METHOD_WITopTen = "GetTeamName";
    String SOAP_ACTION_PREFIX_WITopTen = "http://tempuri.org/TFSClient_IServ/GetTeamName";
    String URL = Global_Values.URL + "Service/TFSClient_Serv.svc?wsdl";
    ArrayList<ArrayList<String>> collectionlist;
    public GetAllUsers(){
        collectionlist = new ArrayList<ArrayList<String>>();
    }
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
            for(int y = 0;y<2;y++){

                strData.add(((SoapObject)result_soap.getProperty(i)).getProperty(y).toString());
            }
            collectionlist.add(strData);
        }

        Global_Values.USerslist = collectionlist;
        Global_Values.QueryActiv.call_processes();
    }

    /**
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {

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
