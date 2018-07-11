package com.ataseven.goodparents;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 2/12/2018.
 */



public class RecyclerViewAdapterOwners extends RecyclerView.Adapter<RecyclerViewAdapterOwners.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private Context mContext;
    private ArrayList<Owners> owns;

    public RecyclerViewAdapterOwners(Context context, ArrayList<Owners> sahipler) {
        owns = sahipler;
        mContext = context;
        Toast.makeText(context, "Kurumlar Boyu = " + owns.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kurum_gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Log.d(TAG, "position = " + position);

        Glide.with(mContext)
                .asBitmap()
                .load(owns.get(position).getPhoto())
                .into(holder.image);
        //holder.date.setText("");
        //holder.price.setText("");
        //holder.quota.setText("");
        holder.name.setText(owns.get(position).getName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Tıklandı"+owns.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return owns.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView image;
        TextView name;
        //TextView price;
        //TextView date;
        //TextView quota;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewOnce);
            name = itemView.findViewById(R.id.textView_name);
            //price = itemView.findViewById(R.id.textView_price);
            //date = itemView.findViewById(R.id.textView_date);
            //quota = itemView.findViewById(R.id.textView_quota);
        }
    }

}
