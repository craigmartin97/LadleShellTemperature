package com.example.ladleshelltemperature.Models;

import java.util.UUID;

public class DeviceInfo
{
    private UUID Id;
    private String DeviceName, DeviceAddress;

    public DeviceInfo(UUID id, String deviceName, String deviceAddress)
    {
        Id = id;
        DeviceName = deviceName;
        DeviceAddress = deviceAddress;
    }

    public UUID getId()
    {
        return Id;
    }

    public void setId(UUID id)
    {
        Id = id;
    }

    public String getDeviceName()
    {
        return DeviceName;
    }

    public void setDeviceName(String deviceName)
    {
        DeviceName = deviceName;
    }

    public String getDeviceAddress()
    {
        return DeviceAddress;
    }

    public void setDeviceAddress(String deviceAddress)
    {
        DeviceAddress = deviceAddress;
    }
}
