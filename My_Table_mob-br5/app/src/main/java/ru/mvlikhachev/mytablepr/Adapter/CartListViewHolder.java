package ru.mvlikhachev.mytablepr.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.mvlikhachev.mytablepr.R;

public class CartListViewHolder extends RecyclerView.ViewHolder {
    ImageView productImage;
    TextView productTitles, productPrice;
    TextView grade;

    public CartListViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.pic);
        productTitles = itemView.findViewById(R.id.title);
        productPrice = itemView.findViewById(R.id.fee);
        grade = itemView.findViewById(R.id.grade);
    }
}
