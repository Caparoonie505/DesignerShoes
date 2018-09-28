package com.example.capar.designershoes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> productNames;
    private ArrayList<String> productImages;
    private ArrayList<String> productPrices;

    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> names, ArrayList<String> images, ArrayList<String> prices, Context context){
        this.productNames = names;
        this.productImages = images;
        this.productPrices = prices;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext).asBitmap().load(productImages.get(position)).into(holder.prodImg);

        holder.prodName.setText(productNames.get(position));
        holder.prodPrice.setText(productPrices.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on " + productNames.get(position));

                Toast.makeText(mContext,productNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView prodName;
        ImageView prodImg;
        TextView prodPrice;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.textViewProductName);
            prodImg = itemView.findViewById(R.id.imageViewProduct);
            prodPrice = itemView.findViewById(R.id.textViewProductPrice);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }




}
