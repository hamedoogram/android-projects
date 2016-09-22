package com.ksu.team.myapplication;

/**
 * Created by hmoussa on 20/02/2015.
 */
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class CallRecordingService extends Service
{


    final MediaRecorder recorder=new MediaRecorder();
    boolean recording=false;
    int i=0;
    String fname;
    String file_name;
    String file_path;
    String datetime_from;
    String datetime_to;
    Intent intent2; // = new Intent(this, MainActivity.class);
    BroadcastReceiver CallRecorder=new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context arg0, Intent intent)
        {

            // TODO Auto-generated method stub
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            i++;
            if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state))
            {
                Toast.makeText(getApplicationContext(), state, Toast.LENGTH_LONG).show();

                Toast.makeText(arg0, "Start CaLLED "+recording+fname, Toast.LENGTH_LONG).show();

                startRecording();


            }
            if(TelephonyManager.EXTRA_STATE_IDLE.equals(state)&&recording==true)
            {
                Toast.makeText(getApplicationContext(), state, Toast.LENGTH_LONG).show();

                Toast.makeText(arg0, "STOP CaLLED :"+recording, Toast.LENGTH_LONG).show();
                stopRecording();
            }

            if(TelephonyManager.EXTRA_STATE_RINGING.equals(state))
            {

                fname=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(getApplicationContext(), state+" : "+fname, Toast.LENGTH_LONG).show();
            }
        }
    };
    BroadcastReceiver OutGoingNumDetector=new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            fname=intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }
    };
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_LONG).show();

        IntentFilter RecFilter = new IntentFilter();
        RecFilter.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(CallRecorder, RecFilter);
        IntentFilter OutGoingNumFilter=new IntentFilter();
        OutGoingNumFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(OutGoingNumDetector, OutGoingNumFilter);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(CallRecorder);
        unregisterReceiver(OutGoingNumDetector);
        Toast.makeText(getApplicationContext(), "Destroyed", Toast.LENGTH_SHORT).show();
    }
    public void startRecording()
    {
        if(recording==false)
        {
            datetime_from = DateFormat.getDateTimeInstance().format(new Date());
            Toast.makeText(getApplicationContext(), "Recorder_Sarted"+fname, Toast.LENGTH_LONG).show();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            String file= "/storage/sdcard0/My Application Records";
            String filepath= file+"/11111111111111";
            File dir= new File(filepath);
            dir.mkdirs();
            file_path = filepath;
            filepath+="/"+datetime_from+"_"+fname+".mp3";
            file_name = datetime_from+"_"+fname+".mp3";
            file_path+="/"+datetime_from+"_"+fname+".mp3";
            recorder.setOutputFile(filepath);

            try {
                recorder.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            recorder.start();
            recording=true;
        }
    }
    public void stopRecording()
    {
        if(recording==true)
        {
            Toast.makeText(getApplicationContext(), "Recorder_Relesed from "+recording, Toast.LENGTH_LONG).show();

            recorder.stop();
            recorder.reset();
            recorder.release();
            recording=false;
            broadcastIntent();
            datetime_to = DateFormat.getDateTimeInstance().format(new Date());
            Intent intent2 = new Intent(this, MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("title", file_path);
            startActivity(intent2);

            /**
             * Insert Into Database
             * in Calls Table
             */
            DatabaseHandler db = new DatabaseHandler(this);
            // Inserting Calls
            //Log.d("Insert: ", "Inserting ..");
            db.addCall(new Call(fname,file_path + "..." + file_name,datetime_from + "..." + datetime_to ));
            makeGetRequest(file_name,datetime_from,datetime_to);
        }
    }

    private void makeGetRequest(final String _path,final String start,final String end) {
        new Thread(new Runnable() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try{
                    String encodedurl = "http://192.168.8.103/callrecording/insert_log.php?name="+ URLEncoder.encode(Global.uname, "UTF-8")+"&path="+URLEncoder.encode(_path.replace(":","_"), "UTF-8")+"&start="+URLEncoder.encode(start, "UTF-8")+"&end="+URLEncoder.encode(end, "UTF-8");
                    HttpGet request = new HttpGet(encodedurl);

                    HttpResponse response;
                    try {
                        response = client.execute(request);

                        Log.d("Response of GET request", response.toString());
                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

        // replace with your url


    }

    final String uploadFilePath = "/mnt/sdcard0/";
    final String uploadFileName = "dashboard.apk";
    public void broadcastIntent()
    {
        Intent intent = new Intent();
        intent.setAction("com.exampled.beta.CUSTOM_INTENT");
        sendBroadcast(intent);
        Toast.makeText(getApplicationContext(), "BroadCaste", Toast.LENGTH_LONG).show();

    }
}