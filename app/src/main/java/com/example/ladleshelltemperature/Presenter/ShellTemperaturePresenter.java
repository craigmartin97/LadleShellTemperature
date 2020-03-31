package com.example.ladleshelltemperature.Presenter;

import android.content.Context;

import com.example.ladleshelltemperature.Interfaces.IShellTemperaturePresenter;
import com.example.ladleshelltemperature.Interfaces.IShellTemperatureView;
import com.example.ladleshelltemperature.Models.DeviceInfo;
import com.example.ladleshelltemperature.Models.ShellTemperature;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.Nullable;

public class ShellTemperaturePresenter implements IShellTemperaturePresenter
{

    IShellTemperatureView _shellTemperatureView;

    public ShellTemperaturePresenter(IShellTemperatureView shellTemperatureView)
    {
        _shellTemperatureView = shellTemperatureView;
    }

    @Override
    public void getTemperatures(Context context, LocalDateTime start, LocalDateTime end)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://mpishelltemperatureapi.azurewebsites.net/api/ShellTemperature/GetBetweenDates?start=" + start + "&end=" + end;

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                final ArrayList<ShellTemperature> values = new ArrayList();

                for (int i = 0; i < response.length(); i++)
                {
                    try
                    {
                        final JSONObject object = response.getJSONObject(i);

                        final Double temperature = object.getDouble("temperature");
                        final String dateTime = object.getString("recordedDateTime");
                        final String latitude = object.getString("latitude");
                        final String longitude = object.getString("longitude");
                        final String id = object.getString("id");

                        final JSONObject deviceInfo = object.getJSONObject("device");
                        final String deviceId = deviceInfo.getString("id");
                        final String deviceName = deviceInfo.getString("deviceName");
                        final String deviceAddress = deviceInfo.getString("deviceAddress");
                        DeviceInfo device = new DeviceInfo(UUID.fromString(deviceId), deviceName, deviceAddress);

                        final LocalDateTime recordedDateTime = LocalDateTime.parse(dateTime);
                        final String formattedRecordedDateTime =  format.format(recordedDateTime);

                        final UUID uuid = UUID.fromString(id);

                        final ShellTemperature shellTemperature = new ShellTemperature(uuid, temperature, formattedRecordedDateTime, latitude, longitude, device);
                        values.add(shellTemperature);
                    } catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }

                _shellTemperatureView.setTemperatures(values);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    @Override
    public String formatDatePicked(int dayOfMonth, int month, int year)
    {
        month++;

        String dayText = Integer.toString(dayOfMonth);
        String monthText = Integer.toString(month);

        if (dayOfMonth < 10)
            dayText = "0" + dayText;

        if (month < 10)
            monthText = "0" + monthText;

        return dayText + "/" + monthText + "/" + Integer.toString(year);
    }

    @Override
    public String formatTimePicked(int hour, int minute)
    {
        String hourText = Integer.toString(hour);
        String minuteText = Integer.toString(minute);

        if(hour < 10)
            hourText = "0" + hour;

        if(minute < 10)
            minuteText = "0" + minute;

        return hourText + ":" + minuteText;
    }


}
