package ru.mvlikhachev.mytablepr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


import ru.mvlikhachev.mytablepr.Domain.IventDomain;
import ru.mvlikhachev.mytablepr.R;

public class IventAdapter extends RecyclerView.Adapter<IventAdapter.ViewHolder> {
    ArrayList<IventDomain> iventDomains;
    Context context;
    public IventAdapter(ArrayList<IventDomain> iventDomains) {
        this.iventDomains = iventDomains;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_ivent,parent,false);
        return new ViewHolder(inflate);


    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        holder.title.setText(iventDomains.get(position).getTitle());
        holder.desc.setText(String.valueOf(iventDomains.get(position).getDescription()));


        int drawableReourceId =holder.itemView.getContext().getResources()
                .getIdentifier(iventDomains.get(position).getPic(),"drawable",holder.itemView.getContext()
                        .getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableReourceId).into(holder.pic);
//        holder.pic.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
////            intent.putExtra("object", iventDomains.get(position));
//            holder.itemView.getContext().startActivity(intent);
////!!!!
//        });

    }
    @Override
    public int getItemCount(){return iventDomains.size() ;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc;
        ImageView pic;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.iventTitle);
            pic= itemView.findViewById(R.id.iventPic);
            desc = itemView.findViewById(R.id.iventDesc);
        }
    }
}