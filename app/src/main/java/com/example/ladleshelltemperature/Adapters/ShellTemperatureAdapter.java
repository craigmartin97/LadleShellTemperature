package com.example.ladleshelltemperature.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.ladleshelltemperature.Models.DeviceInfo;
import com.example.ladleshelltemperature.Models.ShellTemperature;
import com.example.ladleshelltemperature.R;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShellTemperatureAdapter extends ArrayAdapter<ShellTemperature>
{

    private Context _context;
    private int _resource;

    public ShellTemperatureAdapter(@NonNull Context context, int resource, @NonNull List<ShellTemperature> objects)
    {
        super(context, resource, objects);

        _context = context;
        _resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ShellTemperature currentShellTemperature = getItem(position);

        Double temperature = currentShellTemperature.getTemperature();
        String recordedDateTime = currentShellTemperature.getRecordedDateTime();
        String latitude = currentShellTemperature.getLatitude();
        String longitude = currentShellTemperature.getLongitude();
        DeviceInfo device = currentShellTemperature.getDeviceInfo();

        // Check if lat and long are null
        latitude = latitude.equalsIgnoreCase("null") ? "N/A" : latitude;
        longitude = longitude.equalsIgnoreCase("null") ? "N/A" : longitude;

        LayoutInflater inflater = LayoutInflater.from(_context);
        convertView = inflater.inflate(_resource, parent, false);

        TextView temperatureText = convertView.findViewById(R.id.Temperature);
        TextView recordedDateTimeText = convertView.findViewById(R.id.RecordedDateTime);
        TextView latitudeText = convertView.findViewById(R.id.Latitude);
        TextView longitudeText = convertView.findViewById(R.id.Longitude);
        TextView deviceNameText = convertView.findViewById(R.id.DeviceName);

        temperatureText.setText(temperature.toString());
        recordedDateTimeText.setText(recordedDateTime);
        latitudeText.setText(latitude);
        longitudeText.setText(longitude);
        deviceNameText.setText(device.getDeviceName());

        return convertView;
    }
}
