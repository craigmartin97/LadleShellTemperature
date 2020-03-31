package com.example.ladleshelltemperature.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ladleshelltemperature.Adapters.ShellTemperatureAdapter;
import com.example.ladleshelltemperature.Interfaces.IShellTemperaturePresenter;
import com.example.ladleshelltemperature.Interfaces.IShellTemperatureView;
import com.example.ladleshelltemperature.Models.ShellTemperature;
import com.example.ladleshelltemperature.Presenter.ShellTemperaturePresenter;
import com.example.ladleshelltemperature.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements IShellTemperatureView
{

    private IShellTemperaturePresenter _shellTemperaturePresenter;
    private ListView list;

    private EditText startDate, endDate, startTimeText, endTimeText;

    private LocalDateTime start, end;
    private LocalTime startTime, endTime;
    private Button applyFilterButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        startTimeText = findViewById(R.id.startTime);
        endTimeText = findViewById(R.id.endTime);
        applyFilterButton = findViewById(R.id.applyFilterButton);

        _shellTemperaturePresenter = new ShellTemperaturePresenter(this);

        startTime = LocalTime.MIDNIGHT;
        setStartTimeText(startTime.toString());

        endTime = LocalTime.of(23, 59);
        setEndTimeText(endTime.toString());

        start = LocalDateTime.of(LocalDate.now().minusDays(7), startTime);
        setStartDateText(_shellTemperaturePresenter.formatDatePicked(start.getDayOfMonth(), start.getMonthValue() - 1, start.getYear()));

        end = LocalDateTime.of(LocalDate.now(), endTime);
        setEndDateText(_shellTemperaturePresenter.formatDatePicked(end.getDayOfMonth(), end.getMonthValue() - 1, end.getYear()));

        _shellTemperaturePresenter.getTemperatures(this, start, end);

        applyFilterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.UK);

                // Start
                LocalDate sDate = LocalDate.parse(startDate.getText().toString(), formatter);
                start = LocalDateTime.of(sDate, startTime);

                // End
                LocalDate eDate = LocalDate.parse(endDate.getText().toString(), formatter);
                end = LocalDateTime.of(eDate, endTime);

                _shellTemperaturePresenter.getTemperatures(MainActivity.this, start, end);
            }
        });

        setDatePickerOnClickListeners();
        setTimePickerOnClickListeners();
    }

    /**
     * Set the temperature list on screen with new data
     *
     * @param temperatures
     */
    @Override
    public void setTemperatures(ArrayList<ShellTemperature> temperatures)
    {
        ShellTemperatureAdapter shellTemperatureAdapter = new ShellTemperatureAdapter(this, R.layout.shelltemperature_listitem, temperatures);
        list.setAdapter(shellTemperatureAdapter);
    }

    /**
     * Set the on click listener events for date pickers
     */
    private void setDatePickerOnClickListeners()
    {
        // Start date picker
        startDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        R.style.datepicker,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                            {
                                String formattedDate = _shellTemperaturePresenter.formatDatePicked(dayOfMonth, month, year);
                                setStartDateText(formattedDate);
                            }
                        },
                        year, month, day);
                dialog.show();
            }
        });

        // End date picker
        endDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        R.style.datepicker,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                            {
                                String formattedDate = _shellTemperaturePresenter.formatDatePicked(dayOfMonth, month, year);
                                setEndDateText(formattedDate);
                            }
                        },
                        year, month, day);
                dialog.show();
            }
        });
    }

    private void setTimePickerOnClickListeners()
    {
        // Start time text
        startTimeText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        startTime = LocalTime.of(selectedHour, selectedMinute);
                        String formattedTime = _shellTemperaturePresenter.formatTimePicked(selectedHour, selectedMinute);
                        setStartTimeText(formattedTime);
                    }
                }, hour, minute, true);
                mTimePicker.show();
            }
        });

        // End time text
        endTimeText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        endTime = LocalTime.of(selectedHour, selectedMinute);
                        String formattedTime = _shellTemperaturePresenter.formatTimePicked(selectedHour, selectedMinute);
                        setEndTimeText(formattedTime);
                    }
                }, hour, minute, true);
                mTimePicker.show();
            }
        });
    }

    private void setStartDateText(String text)
    {
        startDate.setText(text);
    }

    private void setEndDateText(String text)
    {
        endDate.setText(text);
    }

    private void setStartTimeText(String text)
    {
        startTimeText.setText(text);
    }

    private void setEndTimeText(String text)
    {
        endTimeText.setText(text);
    }
}
