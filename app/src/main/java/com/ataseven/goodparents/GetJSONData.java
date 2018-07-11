package com.ataseven.goodparents;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class GetJSONData extends AsyncTask<Void, Void, Void> {

    /**
     *
     *
     *
     */
    private String domain = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com:9000/v0.1/api/";
    private String [] urlS = new String[]{
            "getPopularActivites",
            "activities/",
            "activitiesByOwner/",
            "categories",
            "categories/",
            "featured",
            "weeklyFeatured",
            "activityOwners",
            "activityOwners/"
    };
    private int index=0;
    private int ID;
    private Context context;
    public ProgressDialog pDialog;

    public GetJSONData(Context context) {
        this.context = context;





    }

    private ArrayList<Activity> etkinlikler;
    private Activity etkinlik;

    private ArrayList<Categories> kategoriler;
    private Categories kategori;

    private ArrayList<Owners> kurumlar;
    private Owners kurum;

    public ArrayList<Activity> etkinlikleriGetir()
    {
        index = 0;
        etkinlikler = new ArrayList<Activity>();
        execute();
        return etkinlikler;
    }
    public Activity IDGoreEtkinlikGetir(int ID)
    {
        index = 1;
        this.ID = ID;
        return etkinlik;
    }
    public Activity sahibeGoreEtkinlikGetir(int ID)
    {
        index = 2;
        this.ID = ID;
        return etkinlik;
    }
    public ArrayList<Categories> kategorileriGetir()
    {
        index = 3;

        return kategoriler;
    }
    public Categories IDGoreKategorileriGetir(int ID)
    {
        index = 4;
        this.ID = ID;
        return kategori;
    }
    public ArrayList<Activity> featuredEtkinlikleriGetir()
    {
        index = 5;

        return etkinlikler;
    }
    public Activity haftalikFeaturedGetir()
    {
        index = 6;
        return etkinlik;
    }
    public ArrayList<Owners> kurumlariGetir()
    {
        index = 7;

        return kurumlar;
    }
    public Owners kurumGetir(int ID)
    {
        index = 8;
        this.ID = ID;
        return kurum;
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Lütfen Bekleyiniz...\nServer Bağlantısı Yapılıyor");
        pDialog.setCancelable(true);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        String jsonStr;
        if(urlS[index].endsWith("/"))
            jsonStr = sh.makeServiceCall(domain + urlS[index] + ID);
        else
            jsonStr = sh.makeServiceCall(domain + urlS[index]);


        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray response = jsonObj.getJSONArray("response");




                switch (index)
                {
                    case 0:
                        for (int i = 0; i < response.length(); i++) {
                            Activity act = new Activity();
                            JSONObject c = response.getJSONObject(i);


                            /**
                             * Etkinlik Detayları
                             */
                            String tempStr;
                            int tempInt;

                            tempInt = c.getInt("id");
                            act.setId(tempInt);

                            tempInt = c.getInt("activity_owner_id");
                            act.setActivity_owner_id(tempInt);

                            tempStr = c.getString("name");
                            if(tempStr != null)
                                act.setName(tempStr);
                            else
                                act.setName("");

                            tempStr = c.getString("description");
                            if(tempStr != null)
                                act.setDescription(tempStr);
                            else
                                act.setDescription("");

                            tempInt = c.getInt("quota");
                            act.setQuota(tempInt);

                            tempStr = c.getString("price");
                            if(tempStr != null)
                                act.setPrice(tempStr);
                            else
                                act.setPrice("");

                            tempStr = c.getString("notes");
                            if(tempStr != null)
                                act.setNotes(tempStr);
                            else
                                act.setNotes("");

                            tempStr = c.getString("latitude");
                            if(tempStr != null)
                                act.setLatitude(tempStr);
                            else
                                act.setLatitude("");

                            tempStr = c.getString("longitude");
                            if(tempStr != null)
                                act.setLongitude(tempStr);
                            else
                                act.setLongitude("");

                            tempStr = c.getString("address");
                            if(tempStr != null)
                                act.setAddress(tempStr);
                            else
                                act.setAddress("");


                            /**
                             * Etkinlik Kategorileri
                             */
                            JSONArray cats = c.getJSONArray("categories");
                            for (int j = 0; j < cats.length(); j++) {
                                JSONObject cat = cats.getJSONObject(j);
                                Categories localCat = new Categories();
                                tempStr = cat.getString("name");
                                if(tempStr != null)
                                {
                                    localCat.setName(tempStr);
                                }
                                else
                                {
                                    localCat.setName("");
                                }

                                tempInt = cat.getInt("id");
                                localCat.setId(tempInt);

                                act.setCategories(localCat);
                            }


                            /**
                             * Etkinlik Fotoğrafları
                             */
                            JSONArray imgs = c.getJSONArray("images");
                            if(imgs.length() == 0)
                            {
                                Images localImg = new Images();
                                localImg.setImg_url("");
                                localImg.setFeatured(1);
                                act.setImages(localImg);
                            }
                            else
                                for (int j = 0; j < imgs.length(); j++) {
                                    JSONObject img = imgs.getJSONObject(j);
                                    Images localImg = new Images();
                                    tempStr = img.getString("img_url");
                                    if(tempStr != null)
                                    {
                                        String uril = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com/";
                                        uril += tempStr;
                                        localImg.setImg_url(uril);
                                        //Toast.makeText(ExplorePage.this, "tempStr", Toast.LENGTH_SHORT).show();
                                        Log.e("doInBackground", "Resimler URL Resim URL = "+ uril +" ID = " + act.getId());
                                    }
                                    else
                                        localImg.setImg_url("");

                                    tempInt = img.getInt("featured");
                                    localImg.setFeatured(tempInt);

                                    act.setImages(localImg);
                                }


                            /**
                             * Etkilnik Sahibi
                             */
                            JSONArray owns = c.getJSONArray("owners");
                            Owners localOwn = new Owners();
                            for (int j = 0; j < owns.length(); j++) {
                                JSONObject owner = owns.getJSONObject(j);


                                tempInt = owner.getInt("id");
                                localOwn.setId(tempInt);

                                tempStr = owner.getString("name");
                                if(tempStr != null)
                                    localOwn.setName(tempStr);
                                else
                                    localOwn.setName("");

                                tempStr = owner.getString("address");
                                if(tempStr != null)
                                    localOwn.setAddress(tempStr);
                                else
                                    localOwn.setAddress("");


                                tempStr = owner.getString("photo");
                                if(tempStr != null)
                                {
                                    String uril = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com/";
                                    uril += tempStr;
                                    localOwn.setPhoto(uril);
                                }
                                else
                                    localOwn.setPhoto("");


                                tempStr = owner.getString("about");
                                if(tempStr != null)
                                    localOwn.setAbout(tempStr);
                                else
                                    localOwn.setAbout("");

                                act.setOwners(localOwn);
                            }
                            etkinlikler.add(act);
                        }


                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;

                    case 8:
                        break;


                    default:
                        break;



                }








            } catch (final JSONException e) {
                Log.e("doInBackground", "Json parsing error: " + e.getMessage());
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });*/

            }
        } else {
            Log.e("doInBackground", "Couldn't get json from server.");
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });*/

        }
        pDialog.dismiss();
        return null;
    }

    protected void onPostExecute(Void result) {
        //TextView textView_countAct1 = (TextView) findViewById(R.id.textView_countAct1);
        //textView_countAct1.setText(String.valueOf(etkinlikler.size()));
        //initRecyclerViewMain();
        //initRecyclerViewSecond();
        //initRecyclerViewCats();
        super.onPostExecute(result);
    }



}
