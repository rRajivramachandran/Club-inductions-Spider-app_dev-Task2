package com.example.android.sensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    SensorManager manage;
    Sensor sense;
    TextView read;
    Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    MediaPlayer play = new MediaPlayer();
    LinearLayout m;
    TextView status;

    int black= Color.argb(255,0,0,0);
    int red = Color.argb(255,255,0,0);
    CountDownTimer timer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            m.setBackgroundColor(red);
            read.setText("seconds to alarm: " + millisUntilFinished / 1000);

        }

        @Override
        public void onFinish() {
        new Taskx().execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            play.setDataSource(this,alarm);
            play.prepare();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        m = (LinearLayout) findViewById(R.id.system);
        read = (TextView) findViewById(R.id.reading);
        status = (TextView) findViewById(R.id.status);
        manage = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sense = manage.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sense == null)
            Log.i("sTAT", "nO");
        else
            manage.registerListener(proximitySensorEventListener, sense, SensorManager.SENSOR_DELAY_NORMAL);


    }

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.values[0] == 0)
            { status.setText("Hold there");

                timer.start();



                Log.i("startdamn", "0");

            }
            else
            {    timer.cancel();
                 m.setBackgroundColor(black);
                //if(q.isCancelled()==false)
                //q.cancel(true);
                read.setText("seconds to alarm:10");
                status.setText("Come closer to start timer");
                //m.setBackgroundColor(black);

            }


        }

    };
    private class Taskx extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void...params)
        {   Log.i("HERE","HERE");
            play.start();

            // try{ Thread.currentThread();
            //    Thread.sleep(10000);}catch(InterruptedException e){Log.i("kkk","interrupted");};
            return null;
        }

        protected void onPostExecute(Void x)
        {
            Log.i("njjj","JJJJJJJJ");
            read.setText("Move back to test again");
            status.setText("Go back to reset timer");
            //tim.setText("HIIIII");

        }


    }


}
