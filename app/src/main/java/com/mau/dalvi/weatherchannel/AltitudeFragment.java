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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AltitudeFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor pressure, altitude;
    private TextView tvSensorPre, tvSeaPre, tvAltitude, tvYourGuess;
    private Controller controller;
    private GuessFragment guessFrag;

    public AltitudeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_altitude, container, false);
        initializeComponents(view);
        getPreSensor();
        setGuess();
        return view;
    }

    private void initializeComponents(View view) {
        tvSensorPre = view.findViewById(R.id.tvSensorPre);
        tvSeaPre = view.findViewById(R.id.tvSeaPre);
        tvAltitude = view.findViewById(R.id.tvAltitude);
        tvYourGuess = view.findViewById(R.id.tvYourGuess);

    }



    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void getPreSensor() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, pressure, sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        sensorManager = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            tvSensorPre.setText("Pressure: " + "\n" + event.values[0] + " hPa");
        }
        float pressure = event.values[0];

        float seaPressure = SensorManager.PRESSURE_STANDARD_ATMOSPHERE;
        tvSeaPre.setText("Local pressure from the service is: " + Float.toString(seaPressure) + " hPa");
        float altitude = SensorManager.getAltitude(seaPressure, pressure);
        tvAltitude.setText("Current altitude is " + altitude + "meters above sea level");

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setGuess(){
        tvYourGuess.setText("" + guessFrag.getGuess());
    }
}

