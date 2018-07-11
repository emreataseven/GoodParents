package com.ataseven.goodparents;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapterSecond extends RecyclerView.Adapter<RecyclerViewAdapterSecond.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private Context mContext;
    private ArrayList<Activity> localActs;

    public RecyclerViewAdapterSecond(Context context, ArrayList<Activity> acts) {
        localActs = acts;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Log.d(TAG, "position = " + position);
        Log.e("Adapter", "ataa: " + localActs.get(position).getImages().get(0).getImg_url());
        int featuredImage = 0;
        for (int i = 0; i< localActs.get(position).getImages().size() ; i++)
        {

            if(localActs.get(position).getImages().get(i).getFeatured() == 1)
            {
                //Toast.makeText(mContext, "id = " + localActs.get(position).getId() + "---featured resim = " + localActs.get(position).getImages().get(i).getFeatured(), Toast.LENGTH_SHORT).show();
                featuredImage = i;
                break;
            }

        }
        Glide.with(mContext)
                .asBitmap()
                .load(localActs.get(position).getImages().get(featuredImage).getImg_url())
                .into(holder.image);
        holder.date.setText("00:00:0000");
        holder.price.setText(localActs.get(position).getPrice() + " TL");
        holder.quota.setText(String.valueOf(localActs.get(position).getQuota()));
        //Toast.makeText(mContext, "resimlerin sayısı = " + localActs.get(position).getImages().size(), Toast.LENGTH_SHORT).show();
        holder.name.setText(localActs.get(position).getName());
        Log.e("Adapter", "name: " + localActs.get(position).getName());
        Log.d("test", "---test---");
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));*/
                //Toast.makeText(mContext, position + " - " + Integer.toString(localActs.get(position).getId()), Toast.LENGTH_SHORT).show();
                Intent actPage = new Intent(mContext, ActivityDetailPage.class);
                actPage.putExtra("EtkinlikID", localActs.get(position).getId());
                //actPage.putExtra("Sahip", localActs.get(position).getOwners());
                //actPage.putExtra("Kategoriler", localActs.get(position).getCategories());

                mContext.startActivity(actPage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localActs.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView image;
        TextView name;
        TextView price;
        TextView date;
        TextView quota;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_actImg);
            name = itemView.findViewById(R.id.textView_name);
            price = itemView.findViewById(R.id.textView_price);
            date = itemView.findViewById(R.id.textView_date);
            quota = itemView.findViewById(R.id.textView_quota);
        }
    }
}
