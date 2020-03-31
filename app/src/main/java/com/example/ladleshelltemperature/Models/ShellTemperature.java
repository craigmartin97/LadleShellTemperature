package com.example.ladleshelltemperature.Models;

import java.time.LocalDateTime;
import java.util.UUID;

import androidx.annotation.Nullable;

public class ShellTemperature
{
    private UUID Id;
    private double Temperature;
    private String RecordedDateTime, Latitude, Longitude;
    private DeviceInfo DeviceInfo;

    public ShellTemperature(UUID id, double temperature, String recordedDateTime, String latitude, String longitude, DeviceInfo deviceInfo)
    {
        Id = id;
        Temperature = temperature;
        RecordedDateTime = recordedDateTime;
        Latitude = latitude;
        Longitude = longitude;
        DeviceInfo = deviceInfo;
    }

    public void setId(UUID id)
    {
        Id = id;
    }

    public UUID getId()
    {
        return Id;
    }

    public void setTemperature(double temperature)
    {
        Temperature = temperature;
    }

    public double getTemperature()
    {
        return Temperature;
    }

    public void setRecordedDateTime(String recordedDateTime)
    {
        RecordedDateTime = recordedDateTime;
    }

    public String getRecordedDateTime()
    {
        return RecordedDateTime;
    }

    public void setLatitude(String latitude)
    {
        Latitude = latitude;
    }

    public String getLatitude()
    {
        return Latitude;
    }

    public void setLongitude(String longitude)
    {
        Longitude = longitude;
    }

    public String getLongitude()
    {
        return Longitude;
    }

    public DeviceInfo getDeviceInfo()
    {
        return DeviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo)
    {
        DeviceInfo = deviceInfo;
    }
}
