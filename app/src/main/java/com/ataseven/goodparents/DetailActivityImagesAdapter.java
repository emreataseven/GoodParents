package com.ataseven.goodparents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivityImagesAdapter extends BaseAdapter
{


    ArrayList<Images> resimler;
    Activity etk;
    private final Context mContext;
ImageView imager;
    // 1
    public DetailActivityImagesAdapter(Context context, Activity etk, ArrayList<Images> resimler, ImageView imager) {
        this.mContext = context;
        this.etk = etk;
        this.resimler = resimler;
        this.imager = imager;
    }

    // 2
    @Override
    public int getCount() {
        return resimler.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.layout_detail_activity_gallery_item, null);

        try {

            ImageView imageView = (ImageView) v.findViewById(R.id.imageViewOnce);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(mContext)
                            .asBitmap()
                            .load(resimler.get(position).getImg_url())
                            .into(imager);
                    Toast.makeText(mContext, "Position = "+position+" url = " + resimler.get(position).getImg_url(), Toast.LENGTH_SHORT).show();
                }
            });
            Glide.with(mContext)
                    .asBitmap()
                    .load(resimler.get(position).getImg_url())
                    .into(imageView);
        } catch (Exception e) {

        }
        return v;



    }
}
