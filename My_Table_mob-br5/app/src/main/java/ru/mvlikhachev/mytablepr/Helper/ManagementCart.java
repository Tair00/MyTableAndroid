package ru.mvlikhachev.mytablepr.Helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mvlikhachev.mytablepr.Activity.CartActivity;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

public class ManagementCart {
    private static ManagementCart instance;
    private ArrayList<RestoranDomain> listCart = new ArrayList<>();
    private CartListener cartListener;

    private ManagementCart(Context context, CartListener listener) {
        // Инициализация объекта ManagementCart
        // Производите необходимые операции, связанные с корзиной
        // Например, получение данных из базы данных или файла

        this.cartListener = listener;
    }

    public static ManagementCart getInstance(Context context, CartActivity listener) {
        if (instance == null) {
            instance = new ManagementCart(context, listener);
        }
        return instance;
    }

    public ArrayList<RestoranDomain> getListCart() {
        return listCart;
    }

    public void addItem(RestoranDomain item) {
        // Добавление элемента в корзину
        // Выполните необходимые операции
        listCart.add(item);
        cartListener.onCartUpdated();
    }

    public void removeItem(ArrayList<RestoranDomain> list, int position) {
        if (list != null && position >= 0 && position < list.size()) {
            RestoranDomain item = list.get(position);
            list.remove(position);
            // Выполните здесь необходимые действия при удалении элемента, например, пересчет общей суммы или обновление интерфейса
            cartListener.onCartUpdated();
        }
    }

    public void deleteCartItem(RestoranDomain item) {
        // Удаление элемента корзины
        // Выполните необходимые операции
        listCart.remove(item);
        cartListener.onCartUpdated();
    }

    // Остальные методы класса

    // Внутренний интерфейс для обратного вызова
    public interface CartListener {
        void onCartUpdated();

        void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction);
    }
}