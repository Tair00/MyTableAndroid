package ru.mvlikhachev.mytablepr.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.Nullable;

import ru.mvlikhachev.mytablepr.Activity.BookingActivity2;
import ru.mvlikhachev.mytablepr.R;

public class UserNameFragment extends DialogFragment {
    private String name;
    private OnUserNameSetListener onUserNameSetListener;

    public static UserNameFragment newInstance() {
        return new UserNameFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_user_name, null);
        final EditText etUserName = dialogView.findViewById(R.id.etUserName);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etUserName.getText().toString();

                // Проверяем, что имя пользователя не пустое
                if (name.isEmpty()) {
                    etUserName.setError("Впишите свое имя");
                } else {
                    // Вызываем метод onUserNameSet из активности или фрагмента,
                    // передавая имя пользователя
                    if (onUserNameSetListener != null) {
                        onUserNameSetListener.onUserNameSet(name);
                    }

                    dismiss(); // Закрываем фрагмент только при валидном имени
                }
            }
        });

        builder.setView(dialogView);
        setCancelable(false); // Запрещаем закрытие фрагмента при нажатии вне диалога

        return builder.create();
    }

    public void setOnUserNameSetListener(OnUserNameSetListener listener) {
        this.onUserNameSetListener = listener;
    }

    public interface OnUserNameSetListener {
        void onUserNameSet(String userName);
    }
}
