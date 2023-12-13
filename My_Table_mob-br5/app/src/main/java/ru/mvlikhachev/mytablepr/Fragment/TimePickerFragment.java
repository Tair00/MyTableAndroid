package ru.mvlikhachev.mytablepr.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

import ru.mvlikhachev.mytablepr.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnTimeSetListener onTimeSetListener;
    private int selectedHour;
    private int selectedMinute;

    // Метод для установки слушателя выбора времени
    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.onTimeSetListener = listener;
    }

    // Интерфейс слушателя выбора времени
    public interface OnTimeSetListener {
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Получить текущее время
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Запомнить выбранное время
        selectedHour = hour;
        selectedMinute = minute;

        // Создать новый экземпляр TimePickerDialog и вернуть его
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.TimePickerDialogStyle, this, hour, minute, DateFormat.is24HourFormat(getActivity())) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // Установка цвета кнопок
                Button positiveButton = getButton(TimePickerDialog.BUTTON_POSITIVE);
                Button negativeButton = getButton(TimePickerDialog.BUTTON_NEGATIVE);
                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.mainColor));
                    negativeButton.setTextColor(getResources().getColor(R.color.mainColor));
                }
            }
        };

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Запомнить выбранное время
        selectedHour = hourOfDay;
        selectedMinute = minute;

        // Передача выбранного времени слушателю
        if (onTimeSetListener != null) {
            onTimeSetListener.onTimeSet(view, hourOfDay, minute);
        }
    }

    public String getSelectedTime() {
        // Вернуть выбранное время в формате "часы:минуты"
        return String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
    }

    public void updateTime(int hourOfDay, int minute) {
        // Обновить выбранное время
        selectedHour = hourOfDay;
        selectedMinute = minute;
    }
}
