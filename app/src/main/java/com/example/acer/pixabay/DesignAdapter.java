package com.example.acer.pixabay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.MyViewHolder> {
    Context context;
    ArrayList<DesignModel> designModels;

    public DesignAdapter(Design design, ArrayList<DesignModel> designModels) {
       this.context=design;
       this.designModels=designModels;
    }

    @NonNull
    @Override
    public DesignAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignAdapter.MyViewHolder holder, int position) {
        DesignModel model=designModels.get(position);

        Picasso.with(context).load(model.imageUrl).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return designModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.rimage);

        }
    }
}
