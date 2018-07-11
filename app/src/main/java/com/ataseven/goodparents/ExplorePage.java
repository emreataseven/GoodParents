package com.ataseven.goodparents;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExplorePage extends AppCompatActivity {

    /*
    * Variables
    * Begins
    * */
    private ProgressDialog pDialog;
    private static String url = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com:9000/v0.1/api/featured";
    private static String url2 = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com:9000/v0.1/api/activityOwners";
    ArrayList<Activity> etkinlikler = new ArrayList<>();
    ArrayList<Owners> kurumlar = new ArrayList<>();
    /*
    * Ends
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_explore_page);

        GetJson getJson = new GetJson();
        getJson.execute();

       /* GetJSONData json = new GetJSONData(this);
        etkinlikler = json.etkinlikleriGetir();
pDialog = json.pDialog;
pDialog.do*/

        //Toast.makeText(this, "Etkinlikler Boyu = " + etkinlikler.size(), Toast.LENGTH_SHORT).show();

        //initRecyclerViewMain();




        TextView tv_yakin = (TextView) findViewById(R.id.textView_yakinda);


        if(ContextCompat.checkSelfPermission(ExplorePage.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //Toast.makeText(ExplorePage.this,"Zaten izin verilmiş durumda", Toast.LENGTH_SHORT).show();
            tv_yakin.setBackgroundResource(R.drawable.butonarkaplanigradient);
        }
        else
        {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Konum Paylaşma İzni");
            alertBuilder.setMessage("657 Etkinlik merkezi arasından Size En Yakın merkezleri sunabilmek için lokasyon izni vermelisiniz");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(ExplorePage.this,
                            new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},
                            1);

                }

            });

            AlertDialog alert = alertBuilder.create();
            alert.show();
        }

        if(ContextCompat.checkSelfPermission(ExplorePage.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //Toast.makeText(ExplorePage.this,"Zaten izin verilmiş durumda", Toast.LENGTH_SHORT).show();

            tv_yakin.setBackgroundResource(R.drawable.butonarkaplanigradient);
        }
        else
        {
            tv_yakin.setBackgroundResource(R.drawable.butonarkaplanigradientgrey);
        }

    }



    private void initRecyclerViewMain(){

        Log.d("init", "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMain);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapterMain adapter = new RecyclerViewAdapterMain(this, etkinlikler);

        recyclerView.setAdapter(adapter);

    }

    private void initRecyclerViewSecond(){

        Log.d("init", "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapterSecond adapter = new RecyclerViewAdapterSecond(this, etkinlikler);

        recyclerView.setAdapter(adapter);

    }

    private void initRecyclerViewCats(){

        Log.d("init", "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCats);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapterCats adapter = new RecyclerViewAdapterCats(this);
        ((TextView) findViewById(R.id.textView_countCats1)).setText(String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);

    }

    private void initRecyclerViewOwners(){

        Log.d("init", "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewKurumlar);

        recyclerView.setLayoutManager(layoutManager);
       // Toast.makeText(this, "kurumlar size = " + kurumlar.size(), Toast.LENGTH_SHORT).show();
        RecyclerViewAdapterOwners adapter = new RecyclerViewAdapterOwners(this, kurumlar);

        recyclerView.setAdapter(adapter);

    }


    public void perform_action(View v) {
        TextView tv= (TextView) findViewById(R.id.textView_yakinda);
        if(ContextCompat.checkSelfPermission(ExplorePage.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Intent mapPage = new Intent(this, ExplorerMapsActivity.class);
            //actPage.putExtra("Sahip", localActs.get(position).getOwners());
            //actPage.putExtra("Kategoriler", localActs.get(position).getCategories());

            this.startActivity(mapPage);
            tv.setBackgroundResource(R.drawable.butonarkaplanigradient);
        }
        else
        {

            //Toast.makeText(ExplorePage.this, "İzin İste", Toast.LENGTH_SHORT).show();
            tv.setBackgroundResource(R.drawable.butonarkaplanigradientgrey);
            ActivityCompat.requestPermissions(ExplorePage.this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            if(ContextCompat.checkSelfPermission(ExplorePage.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                tv.setBackgroundResource(R.drawable.butonarkaplanigradient);
            }
        }
    }

    public void onSearchClick(View v) {



    }
    private class GetJson extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ExplorePage.this);
            pDialog.setMessage("Lütfen Bekleyiniz...\nServer Bağlantısı Yapılıyor");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();



            //region Activity Get
            String jsonStr = sh.makeServiceCall(url);


            Log.e("doInBackground", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("response");

                    for (int i = 0; i < contacts.length(); i++) {
                        Activity act = new Activity();
                        JSONObject c = contacts.getJSONObject(i);



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


                        Log.e("doInBackground", "Tekler Bitti ID = " + act.getId());



                        //region Actvity Categories
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

                        //endregion
                        Log.e("doInBackground", "Kategori Bitti Kategori Sayısı = "+ act.getCategories().size() +" ID = " + act.getId());


                        //region Activity Images
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



//endregion
                        Log.e("doInBackground", "Resimler Bitti Resim Sayısı = "+ act.getImages().size() +" ID = " + act.getId());


                        //region Activity Owners
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
                        //endregion
                        Log.e("doInBackground", "Sahipler Bitti Sahip Sayısı = "+ act.getOwners().size() +" ID = " + act.getId());

                        etkinlikler.add(act);
                        Log.e("doInBackground", "Etkinlik Arraylist e eklendi = "+ etkinlikler.size());
                    }//for - i

                } catch (final JSONException e) {
                    Log.e("doInBackground", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("doInBackground", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            //endregion

            //region Kurum Get
            jsonStr = sh.makeServiceCall(url2);


            Log.e("doInBackground", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("response");

                    for (int i = 0; i < contacts.length(); i++) {
                        Owners own = new Owners();
                        JSONObject c = contacts.getJSONObject(i);



                        String tempStr;
                        int tempInt;

                        tempInt = c.getInt("id");
                        own.setId(tempInt);

                        tempStr = c.getString("name");
                        if(tempStr != null)
                            own.setName(tempStr);
                        else
                            own.setName("");

                        tempStr = c.getString("address");
                        if(tempStr != null)
                            own.setAddress(tempStr);
                        else
                            own.setAddress("");

                        tempStr = c.getString("photo");
                        if(tempStr != null)
                            own.setPhoto("http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com/" + tempStr);
                        else
                            own.setPhoto("");

                        tempStr = c.getString("about");
                        if(tempStr != null)
                            own.setAbout(tempStr);
                        else
                            own.setAbout("");



                        kurumlar.add(own);
                        Log.e("doInBackground", "Etkinlik Arraylist e eklendi = "+ etkinlikler.size());
                    }//for - i

                } catch (final JSONException e) {
                    Log.e("doInBackground", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("doInBackground", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            //endregion
            pDialog.dismiss();
            return null;
        }

        protected void onPostExecute(Void result) {
            ((TextView) findViewById(R.id.textView_countAct1)).setText(String.valueOf(etkinlikler.size()));
            initRecyclerViewMain();
            initRecyclerViewSecond();
            //((TextView) findViewById(R.id.textView_countCats1)).setText(String.valueOf(etkinlikler.size()));
            initRecyclerViewCats();
            ((TextView) findViewById(R.id.textView_countOwners1)).setText(String.valueOf(kurumlar.size()));
            initRecyclerViewOwners();
            super.onPostExecute(result);
        }
    }
}
