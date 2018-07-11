package com.ataseven.goodparents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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



public class RecyclerViewAdapterCats extends RecyclerView.Adapter<RecyclerViewAdapterCats.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private Context mContext;
    private ArrayList<Kategoriler> kats;

    public RecyclerViewAdapterCats(Context context) {
        kats = new ArrayList<Kategoriler>();
        mContext = context;
        GetJson gt = new GetJson();
        gt.execute();
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

        Glide.with(mContext)
                .asBitmap()
                .load(kats.get(position).getImg_url())
                .into(holder.image);
        holder.date.setText("");
        holder.price.setText("");
        holder.quota.setText("");
        holder.name.setText(kats.get(position).getName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Tıklandı", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kats.size();

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

    private class GetJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com:9000/v0.1/api/categories");

            Log.e("doInBackground", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("response");

                    for (int i = 0; i < contacts.length(); i++) {
                        Kategoriler cat = new Kategoriler();
                        JSONObject c = contacts.getJSONObject(i);



                        String tempStr;
                        int tempInt;

                        tempInt = c.getInt("id");
                        cat.setId(tempInt);

                        tempStr = c.getString("name");
                        if(tempStr != null)
                            cat.setName(tempStr);
                        else
                            cat.setName("");

                        tempStr = c.getString("img_url");
                        String uril = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com/";
                        uril += tempStr;

                        if(tempStr != null)
                            cat.setImg_url(uril);
                        else
                            cat.setImg_url("");



                        kats.add(cat);

                    }//for - i

                } catch (final JSONException e) {
                    Log.e("doInBackground", "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.e("doInBackground", "Couldn't get json from server.");

            }
            return null;
        }

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
        }
    }
}
