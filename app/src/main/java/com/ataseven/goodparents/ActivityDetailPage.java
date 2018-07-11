package com.ataseven.goodparents;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;




public class ActivityDetailPage extends AppCompatActivity implements OnMapReadyCallback{

    Activity etk;
    ArrayList<Images> resimler;
    ArrayList<Categories> kategoriler;
    ArrayList<Owners> sahip;
    private ProgressDialog pDialog;
    int etkID;
    private static String url = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com:9000/v0.1/api/Activities/";

    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_detail_page);





        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setSubtitle("MapView");

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        Bundle b = getIntent().getExtras();
        etkID = b.getInt("EtkinlikID");
        GetJson js = new GetJson();
        js.execute();



        //etk = b.getParcelable("Etkinlik");
        //resimler = b.getParcelableArrayList("Resimler");
        //Toast.makeText(this, resimler.get(0).getImg_url() + " - " + Integer.toString(resimler.size()), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "id = " + resimler.get(0).getImg_url(), Toast.LENGTH_SHORT).show();
        ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        /*gmap.setMinZoomPreference(15);
        LatLng ny = new LatLng(Double.parseDouble(etk.getLatitude()),Double.parseDouble(etk.getLongitude()));
        gmap.addMarker(new MarkerOptions().position(ny).title("Kız Kulesi"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));*/
    }
    public void addCursor(double lat, double lon)
    {
        gmap.setMinZoomPreference(15);
        LatLng ny = new LatLng(lat, lon);
        gmap.addMarker(new MarkerOptions().position(ny).title("Kız Kulesi"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }







    public void devam()
    {
        int featuredImage = 0;
        for (int i = 0; i< resimler.size() ; i++)
        {
            if(resimler.get(i).getFeatured() == 1)
            {
                featuredImage = i;
                break;
            }

        }

        ImageView actImage = (ImageView) findViewById(R.id.imageViewActImage);
        Glide.with(this)
                .asBitmap()
                .load(resimler.get(featuredImage).getImg_url())
                .into(actImage);


        TextView textView_actName = (TextView) findViewById(R.id.textView_actName);
        textView_actName.setText(etk.getName());

        GridView gridView = (GridView) findViewById(R.id.gridViewGallery);
        DetailActivityImagesAdapter adapter = new DetailActivityImagesAdapter(this, etk, resimler, actImage);
        gridView.setAdapter(adapter);
    }


    private class GetJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ActivityDetailPage.this);
            pDialog.setMessage("Lütfen Bekleyiniz...\nServer Bağlantısı Yapılıyor");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + etkID);

            Log.e("doInBackground", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("response");
                    etk = new Activity();
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);



                        String tempStr;
                        int tempInt;

                        tempInt = c.getInt("id");
                        etk.setId(tempInt);

                        tempInt = c.getInt("activity_owner_id");
                        etk.setActivity_owner_id(tempInt);

                        tempStr = c.getString("name");
                        if(tempStr != null)
                            etk.setName(tempStr);
                        else
                            etk.setName("");

                        tempStr = c.getString("description");
                        if(tempStr != null)
                            etk.setDescription(tempStr);
                        else
                            etk.setDescription("");

                        tempInt = c.getInt("quota");
                        etk.setQuota(tempInt);

                        tempStr = c.getString("price");
                        if(tempStr != null)
                            etk.setPrice(tempStr);
                        else
                            etk.setPrice("");

                        tempStr = c.getString("notes");
                        if(tempStr != null)
                            etk.setNotes(tempStr);
                        else
                            etk.setNotes("");

                        tempStr = c.getString("latitude");
                        if(tempStr != null)
                            etk.setLatitude(tempStr);
                        else
                            etk.setLatitude("");

                        tempStr = c.getString("longitude");
                        if(tempStr != null)
                            etk.setLongitude(tempStr);
                        else
                            etk.setLongitude("");

                        tempStr = c.getString("address");
                        if(tempStr != null)
                            etk.setAddress(tempStr);
                        else
                            etk.setAddress("");


                        kategoriler = new ArrayList<Categories>();
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

                            kategoriler.add(localCat);
                        }






                        JSONArray imgs = c.getJSONArray("images");
                        resimler = new ArrayList<>();
                        if(imgs.length() == 0)
                        {
                            Images localImg = new Images();
                            localImg.setImg_url("");
                            localImg.setFeatured(1);
                            resimler.add(localImg);
                        }
                        else
                            for (int j = 0; j < imgs.length(); j++) {
                                Images localImg = new Images();
                                JSONObject img = imgs.getJSONObject(j);

                                tempStr = img.getString("img_url");
                                if(tempStr != null)
                                {
                                    String uril = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com/";
                                    uril += tempStr;
                                    localImg.setImg_url(uril);
                                }
                                else
                                    localImg.setImg_url("");

                                tempInt = img.getInt("featured");
                                localImg.setFeatured(tempInt);

                                resimler.add(localImg);
                            }





                        JSONArray owns = c.getJSONArray("owners");
                        sahip = new ArrayList<Owners>();
                        for (int j = 0; j < owns.length(); j++) {
                            JSONObject owner = owns.getJSONObject(j);

                            Owners localOwn = new Owners();
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

                            sahip.add(localOwn);
                        }
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
            pDialog.dismiss();
            return null;
        }

        protected void onPostExecute(Void result) {
            devam();
            //Toast.makeText(ActivityDetailPage.this, "Lat = "+etk.getLatitude()+" - Lon = "+etk.getLongitude(), Toast.LENGTH_SHORT).show();
            addCursor(Double.parseDouble(etk.getLatitude()), Double.parseDouble(etk.getLongitude()));
            super.onPostExecute(result);
        }
    }
}
