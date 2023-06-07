package com.example.carstore1;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CarRVAdapter extends RecyclerView.Adapter<CarRVAdapter.carViewHolder> {
public ArrayList<Car>cars= new ArrayList<>();
public OnRecyclerViewOnClickListner listener;
    public CarRVAdapter(ArrayList<Car>cars , OnRecyclerViewOnClickListner listner) {
        this.cars =cars;
        this.listener = listner;
    }

    @NonNull
    @Override
    public carViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_layout,null,false);
        carViewHolder viewHolder =new carViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull carViewHolder holder, int position) {
       Car c =cars.get(position);
       if (!c.getImage().isEmpty()&&c.getImage() != null )
        holder.iv.setImageURI(Uri.parse(c.getImage()));
        holder.tv_model.setText(c.getModel());
        holder.tv_dpl.setText(String.valueOf(c.getDpl()));
        holder.tv_color.setText(c.getColor());
        holder.iv.setTag(c.getId());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public ArrayList<Car> getCars() {
        return cars ;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    class carViewHolder extends RecyclerView.ViewHolder{
    ImageView iv ;
    TextView tv_model,tv_color,tv_dpl , tv_desc;
        public carViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.imageView);
            tv_model=itemView.findViewById(R.id.custom_card_model);
            tv_color=itemView.findViewById(R.id.custom_card_color);
            tv_dpl=itemView.findViewById(R.id.custom_card_dpel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int)iv.getTag();
                    listener.onItemClick(id);
                } });
        }

    }
}
