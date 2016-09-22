package com.xxmassdeveloper.mpchartexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by hmoussa on 14/05/2015.
 */
public class GetQueryResults extends AsyncTask<String, String, String> {
    String NAMESPACE = "http://tempuri.org/";
    String METHOD_WITopTen = "GetQueryWI";
    String SOAP_ACTION_PREFIX_WITopTen = "http://tempuri.org/TFSClient_IServ/GetQueryWI";
    String URL = Global_Values.URL + "Service/TFSClient_Serv.svc?wsdl";
    ArrayList<query_item> collectionlist;
    public GetQueryResults(){
        collectionlist = new ArrayList<query_item>();
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
            request.addProperty("assignTo",Global_Values.query_assignto);
            request.addProperty("type",Global_Values.query_type);
            request.addProperty("state",Global_Values.query_state);
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
        try{
            String str = result_soap.getPropertyAsString(0);
            for(int i = 0; i<result_soap.getPropertyCount();i++){
                ArrayList<String> strData = new ArrayList<String>();

                query_item qi = new query_item(((SoapObject)result_soap.getProperty(i)).getProperty(1).toString(),((SoapObject)result_soap.getProperty(i)).getProperty(0).toString(),((SoapObject)result_soap.getProperty(i)).getProperty(2).toString(),((SoapObject)result_soap.getProperty(i)).getProperty(3).toString());

                collectionlist.add(qi);
            }

            Global_Values.QueryResultlist = collectionlist;
            Intent i = new Intent(Global_Values.QueryActiv.getApplicationContext(), ListActivity_Query.class);
            Global_Values.QueryActiv.startActivity(i);
        }catch (Exception ex){
            Toast.makeText(Global_Values.QueryActiv.getApplicationContext(), "No Workitems Found",
                    Toast.LENGTH_LONG).show();
        }

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
