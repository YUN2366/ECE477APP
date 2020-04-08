package com.example.temp2;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private SensorEventListener gyroscopeListener;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscope == null){
            Toast.makeText(this, "This device has no Gyroscope.", Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                String message = "x: "+ event.values[0] + "\ny: " + event.values[1] + "\nz: " + event.values[2];
                TextView textView = (TextView) findViewById(R.id.gyro);
                textView.setText(message);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        webview = (WebView) findViewById(R.id.webcam);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.loadUrl("http://192.168.4.1:80");

    }
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(gyroscopeListener,gyroscope,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeListener);
    }


}
