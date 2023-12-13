package ru.mvlikhachev.mytablepr.Fragment;

import android.app.Activity;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import ru.mvlikhachev.mytablepr.Fragment.DatePickerFragment;

public class Date extends FragmentActivity {
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
