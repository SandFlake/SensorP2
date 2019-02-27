package com.mau.dalvi.weatherchannel;

import android.annotation.SuppressLint;

import android.os.AsyncTask;

import android.support.v4.app.Fragment;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherAPI {

    private static final Object StringRequest = "";
    private Controller controller;
    JSONObject data = null;
    private WeatherData wData;
    private APIFragment apiFragment;
    private MainActivity activity;



    public WeatherAPI(Controller controller, APIFragment apiFragment) {

        this.controller = controller;
        this.apiFragment = apiFragment;
    }

    public void getWeatherInfo() {
        getJSON("MALMOE");
    }

    @SuppressLint("StaticFieldLeak")
    public void getJSON(final String city) {
        new AsyncTask<Void, Void, Void>() {

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=e1dedb102bf43974a531c443957f42e8 ");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());
                    processJson(data);

                    if (data.getInt("cod") != 200) {
                        System.out.println("Cancelled!");

                        return null;
                    }
                } catch (Exception e) {

                    System.out.println("Exception " + e.getMessage());
                    return null;

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if (data != null) {
                    Log.d("post execute mofo ", data.toString());

                }
            }


        }.execute();
    }

    private void processJson(JSONObject data) throws JSONException {
        wData = new WeatherData();
        JSONObject all = data.getJSONObject("main");
        final String temperature = all.getString("temp");
        final String pressure = all.getString("pressure");
        final String humidity = all.getString("humidity");
        System.out.println("The temp is: " + temperature + "humidity is: " + humidity + " pressure is: " + pressure);

        wData.setHumidity(humidity);
        wData.setPressure(pressure);
        wData.setTemperature(temperature);

        if (apiFragment != null) {
            apiFragment.setValues(wData);
        }

    }





}

