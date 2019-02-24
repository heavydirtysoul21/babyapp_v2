package com.example.babyapp_v2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.babyapp_v2.Interface.ItemClickListener;
import com.example.babyapp_v2.Model.Exercises;
import com.example.babyapp_v2.R;
import com.example.babyapp_v2.ViewExercise;

import java.util.List;

class RevyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView image;
    public TextView text;
    private ItemClickListener itemClickListener;


    public RevyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.ex_image);
        text = (TextView)itemView.findViewById(R.id.ex_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());

    }
}
public class RecyclerViewAdapter extends RecyclerView.Adapter<RevyclerViewHolder>{

    private List<Exercises> exercisesList;
    private Context context;

    public RecyclerViewAdapter(List<Exercises> exercisesList, Context context) {
        this.exercisesList = exercisesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RevyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise,parent, false);
        return new RevyclerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RevyclerViewHolder holder, int position) {
        holder.image.setImageResource(exercisesList.get(position).getImage_id());
        holder.text.setText(exercisesList.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ViewExercise.class);
                intent.putExtra("image_id", exercisesList.get(position).getImage_id());
                intent.putExtra("name", exercisesList.get(position).getName());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }
}
