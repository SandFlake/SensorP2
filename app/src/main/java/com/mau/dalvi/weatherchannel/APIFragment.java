package com.mau.dalvi.weatherchannel;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class APIFragment extends Fragment {

    private TextView tvTitleAPI;
    private TextView tvHumAPI;
    private TextView tvPreAPI;
    private TextView tvTempAPI;
    private ImageView imgHumAPI;
   private ImageView imgPreAPI;
    private ImageView imgTempAPI;
    private Button btnAsync;
    private Button btnVolley;
    private Button btnSensor;
    private Controller controller;
    private SensorFragment fragSensor;
    private WeatherData wData;
    private String temp;


    public APIFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_api, container, false);
        initializeComponents(view);
        registerListeners();


        return view;

    }

    private void registerListeners() {
        ButtonListener listener = new ButtonListener();
        btnAsync.setOnClickListener(listener);
        btnSensor.setOnClickListener(listener);
        btnVolley.setOnClickListener(listener);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == btnAsync){
                controller.btnAsynClicked();

            } else if (view == btnVolley) {
                controller.btnVolleyClicked();

            } else if (view == btnSensor){
                controller.btnGoSensorClicked();
            }

        }
    }

    private void initializeComponents(View view) {
        tvTitleAPI = view.findViewById(R.id.tvTitleAPI);
        tvHumAPI = view.findViewById(R.id.tvHumAPI);
        tvPreAPI = view.findViewById(R.id.tvPreAPI);
        tvTempAPI = view.findViewById(R.id.tvTempAPI);
        imgHumAPI = view.findViewById(R.id.imgHumAPI);
        imgPreAPI = view.findViewById(R.id.imgPreAPI);
        imgTempAPI = view.findViewById(R.id.imgTempAPI);
        btnAsync = view.findViewById(R.id.btnAsync);
        btnVolley = view.findViewById(R.id.btnVolley);
        btnSensor = view.findViewById(R.id.btnBack);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void runOnUIThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler.post(runnable);
    }


    public void setValues(final WeatherData wData) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {

                tvTempAPI.setText("Temperature: " + "\n" + wData.getTemperature() + "°C." + "\n" + "Difference: - " + wData.getTemperature());
                tvPreAPI.setText("Pressure: " + "\n" + wData.getPressure() + " hPa."  + "\n" + "Difference: - " + (Integer.parseInt(wData.getPressure()) - fragSensor.getSensorPressure()) );
                tvHumAPI.setText("Humidity: " + "\n" + wData.getHumidity() + " %." + "\n" + "Difference: - " + wData.getHumidity());
            }
        });

        }



}
