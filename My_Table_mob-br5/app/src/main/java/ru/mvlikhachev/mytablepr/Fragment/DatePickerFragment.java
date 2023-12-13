package ru.mvlikhachev.mytablepr.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

import ru.mvlikhachev.mytablepr.R;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnDateSetListener onDateSetListener;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    // Метод для установки слушателя выбора даты
    public void setOnDateSetListener(OnDateSetListener listener) {
        this.onDateSetListener = listener;
    }

    // Интерфейс слушателя выбора даты
    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int month, int day);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Получение текущей даты
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Запомнить выбранную дату
        selectedYear = year;
        selectedMonth = month;
        selectedDay = day;

        // Создание нового объекта DatePickerDialog и его возврат
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogStyle, this, year, month, day) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // Установка цвета кнопок
                Button positiveButton = getButton(DatePickerDialog.BUTTON_POSITIVE);
                Button negativeButton = getButton(DatePickerDialog.BUTTON_NEGATIVE);
                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.mainColor));
                    negativeButton.setTextColor(getResources().getColor(R.color.mainColor));
                }
            }
        };

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Запомнить выбранную дату
        selectedYear = year;
        selectedMonth = month;
        selectedDay = day;

        // Передача выбранной даты слушателю
        if (onDateSetListener != null) {
            onDateSetListener.onDateSet(view, year, month, day);
        }
    }

    public String getSelectedDate() {
        // Вернуть выбранную дату в формате "день.месяц.год"
        return String.format(Locale.getDefault(), "%02d.%02d.%04d", selectedDay, selectedMonth + 1, selectedYear);
    }

    public void updateDate(int year, int month, int day) {
        // Обновить выбранную дату
        selectedYear = year;
        selectedMonth = month;
        selectedDay = day;
    }
}
