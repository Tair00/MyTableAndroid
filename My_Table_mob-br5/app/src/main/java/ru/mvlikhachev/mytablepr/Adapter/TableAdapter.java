package ru.mvlikhachev.mytablepr.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.mvlikhachev.mytablepr.Activity.PuyActivity;
import ru.mvlikhachev.mytablepr.Activity.ShowDetailActivity;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Domain.TableDomain;
import ru.mvlikhachev.mytablepr.R;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
    private Context context;
    private ArrayList<TableDomain> products;
    private OnItemClickListener onItemClickListener;

    public TableAdapter(Context context, ArrayList<TableDomain> products) {
        this.context = context;
        this.products = products;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);
        return new TableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        TableDomain table = products.get(position);

        holder.tableTitles.setText(table.getTitle());
        holder.tableDesc.setText(String.valueOf(table.getSeat()));

//        int imageId = holder.itemView.getContext().getResources().getIdentifier(table.getPic(), "drawable", holder.itemView.getContext().getPackageName());
//        Glide.with(holder.itemView.getContext()).load(imageId).into(holder.tableImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(table);
                }
            }
        });

        // Добавление эффекта отскока
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener {
        void onItemClick(TableDomain table);
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
        ImageView tableImage;
        TextView tableTitles, tableDesc;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tableImage = itemView.findViewById(R.id.table_cart);
            tableTitles = itemView.findViewById(R.id.title);
            tableDesc = itemView.findViewById(R.id.size);
        }
    }
}
