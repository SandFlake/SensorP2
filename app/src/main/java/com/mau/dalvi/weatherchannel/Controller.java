package com.mau.dalvi.weatherchannel;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    private MainActivity activity;
    private SensorFragment sensorFragment;
    private APIFragment apiFragment;
    private WeatherAPI weatherAPI;
    private AltitudeFragment altitudeFragment;
    private GuessFragment guessFragment;

    public Controller(MainActivity activity) {
        this.activity = activity;
        sensorFragment = new SensorFragment();
        apiFragment = new APIFragment();
        altitudeFragment = new AltitudeFragment();
        guessFragment = new GuessFragment();
        activity.setFragment(sensorFragment, false);
        sensorFragment.setController(this);
        weatherAPI = new WeatherAPI(this, apiFragment);
    }

    public void btnGoAPIClicked() {

        apiFragment.setController(this);
        activity.setFragment(apiFragment, true);
        weatherAPI = new WeatherAPI(this, apiFragment);
        weatherAPI.getWeatherInfo();

    }

    public void btnGoSensorClicked() {
        sensorFragment.setController(this);
        activity.setFragment(sensorFragment, true);
    }

    public void btnGuessClicked() {
        guessFragment.setController(this);
        activity.setFragment(guessFragment, true);
    }

    public void btnAltitudeClicked(final String userGuess) {
        altitudeFragment.setController(this);
        activity.setFragment(altitudeFragment, true);
        Log.d("Altitude frag clicked", "" + userGuess);


    }

    public void btnVolleyClicked() {
        volleyRequest();
    }

    public void btnAsynClicked() {
        weatherAPI.getWeatherInfo();
    }

    private void volleyRequest() {
        RequestQueue queue = Volley.newRequestQueue(activity);

        String key = "&APPID=e1dedb102bf43974a531c443957f42e8";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Malmoe,se&units=metric&APPID=e1dedb102bf43974a531c443957f42e8";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject jsonObject = response.getJSONObject("main");

                            String volleyTemp = jsonObject.getString("temp");
                            String volleyPre = jsonObject.getString("pressure");
                            String volleyHum = jsonObject.getString("humidity");
                            Log.d("volley values", "Humidity " + volleyHum + "Pressure " + volleyPre + "Temp " + volleyTemp);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        queue.add(request);
    }


}
