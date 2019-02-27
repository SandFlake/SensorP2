package com.mau.dalvi.weatherchannel;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment implements SensorEventListener {

    private TextView tvTitleSensor;
    private TextView tvHum;
    private TextView tvPre;
    private TextView tvTemp;
    private TextView tvTime;
    private ImageView imgHum;
    private ImageView imgPre;
    private ImageView imgTemp;
    private Button btnGoAPI;
    private Button btnGuess;

    public static float sensorPressure;
    private Controller controller;

    private SensorManager sensorManager;
    private Sensor humidity;
    private Sensor pressure;
    private Sensor temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        initializeComponents(view);
        getHumSensor();
        getPreSensor();
        getTempSensor();
        registerListeners();

        return view;
    }

    public void setController (Controller controller){
        this.controller = controller;
    }


    private void registerListeners() {
        ButtonListener listener = new ButtonListener();
        btnGoAPI.setOnClickListener(listener);
        btnGuess.setOnClickListener(listener);

    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == btnGoAPI){
                controller.btnGoAPIClicked();
            } else if (view == btnGuess) {
                controller.btnGuessClicked();

            }

        }
    }
    private void initializeComponents(View view ){
        tvTitleSensor = view.findViewById(R.id.tvTitleSensor);
        tvHum = view.findViewById(R.id.tvHum);
        tvPre = view.findViewById(R.id.tvPre);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvTime = view.findViewById(R.id.tvTimeStamp);
        imgHum = view.findViewById(R.id.imgHum);
        imgPre = view.findViewById(R.id.imgPre);
        imgTemp = view.findViewById(R.id.imgTemp);
        btnGoAPI = view.findViewById(R.id.btnGoAPI);
        btnGuess = view.findViewById(R.id.btnGuessAltitude);
    }

    private void getTempSensor() {
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (temp != null) {
            Toast.makeText(getActivity(), "Thermometer activated", Toast.LENGTH_SHORT).show();
        } else {
            tvTemp.setText("Temp sensor not available");
        }
    }

    private void getHumSensor() {
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (humidity != null) {
            Toast.makeText(getActivity(), "Humidity sensor exists", Toast.LENGTH_SHORT).show();
        } else {
            tvHum.setText( "Humidity sensor not available");
        }
    }

    private void getPreSensor(){
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (pressure != null){
            Toast.makeText(getActivity(), "Pressure sensor activated", Toast.LENGTH_SHORT).show();
        } else {
            tvPre.setText("Pressure sensor not available");
        }
    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, pressure, sensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(getActivity(), "Pre Listener registered", Toast.LENGTH_SHORT).show();
        sensorManager.registerListener(this, humidity, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, temp, sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        Toast.makeText(getActivity(), "Pre listener off", Toast.LENGTH_SHORT).show();
    }

    public void onDestroy(){
        super.onDestroy();
        sensorManager = null;
        pressure = null;
        humidity = null;
        temp = null;

    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            tvHum.setText("Humidity: " + "\n" + event.values[0] + " %");
        }
        else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            tvPre.setText("Pressure: " + "\n" + event.values[0] + " hPa");
            sensorPressure = event.values[0];
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = "" + sdf.format(c);
            tvTime.setText(time);

        }
        else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            tvTemp.setText("Temperature: " + "\n" + event.values[0] + "°C");
        }
    }

    public static float getSensorPressure(){
        return sensorPressure;
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
