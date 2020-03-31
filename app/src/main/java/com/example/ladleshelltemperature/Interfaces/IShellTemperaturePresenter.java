package com.example.ladleshelltemperature.Interfaces;

import android.content.Context;

import java.time.LocalDateTime;

public interface IShellTemperaturePresenter
{
    void getTemperatures(Context context, LocalDateTime start, LocalDateTime end);

    String formatDatePicked(int dayOfMonth, int month, int year);

    String formatTimePicked(int hour, int minute);
}
