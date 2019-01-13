package com.blogspot.letstartprogramming.horseman;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class First extends AppCompatActivity{
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private Sensor mMagnetic;
    private Button mButton;
    private SensorEventListener acc_EventListner;
    private SensorEventListener gyro_EventListner;
    private SensorEventListener mag_EventListner;

    String mString="";
    String accString;
    String gyroString;
    String magString;
    String filename="qwerty.CSV";

    private TextView ax,ay,az,gx,gy,gz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetic =mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        ax=(TextView)findViewById(R.id.acx);
        ay=(TextView)findViewById(R.id.acy);
        az=(TextView)findViewById(R.id.acz);
        gx=(TextView)findViewById(R.id.gyx);
        gy=(TextView)findViewById(R.id.gyy);
        gz=(TextView)findViewById(R.id.gyz);
        acc_EventListner=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] value=event.values;

                ax.setText(""+value[0]);
                ay.setText(""+value[1]);
                az.setText(""+value[2]);
                accString="A"+value[0]+","+"A"+value[1]+","+"A"+value[2];
                mString+=accString+",";

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        gyro_EventListner=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                float[] value=event.values;
                gx.setText(""+value[0]);
                gy.setText(""+value[1]);
                gz.setText(""+value[2]);
                gyroString="G"+value[0]+","+"G"+value[1]+","+"G"+value[2];
                mString+=gyroString+",";

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mag_EventListner=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] value=event.values;
                magString="M"+value[0]+","+"M"+value[1]+","+"M"+value[2];
                mString+=magString+"\n";

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //mString+=accString+","+gyroString+"\n";

        mButton=(Button)findViewById(R.id.press);
       mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FileOutputStream out= openFileOutput(filename,MODE_APPEND);
                    out.write(mString.getBytes());
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(acc_EventListner, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(gyro_EventListner,mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mag_EventListner,mMagnetic,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(acc_EventListner);
        mSensorManager.unregisterListener(gyro_EventListner);
        mSensorManager.unregisterListener(mag_EventListner);
    }


   /* @Override
    public void onSensorChanged(SensorEvent event) {

        float[] value=event.values;

        mString+=value[0]+","+value[1]+","+value[2]+"\n";

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }*/
}
