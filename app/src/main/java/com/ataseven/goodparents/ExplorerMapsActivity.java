package com.ataseven.goodparents;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class ExplorerMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gmap;

    private ProgressDialog pDialog;
    private static String url = "http://ec2-18-220-182-146.us-east-2.compute.amazonaws.com:9000/v0.1/api/featured";
    ArrayList<Activity> etkinlikler = new ArrayList<>();
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_explorer_maps);


        //region Map
        Toolbar tb = findViewById(R.id.toolbar2);

        setSupportActionBar(tb);
        tb.setSubtitle("MapView");

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = findViewById(R.id.map);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        //endregion




        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        //spinner.setOnItemSelectedListener();

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_cat_list_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_cat_list_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



    }



    //region Map Functions
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
    }
    //endregion


    private class GetJson extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ExplorerMapsActivity.this);
            pDialog.setMessage("Lütfen Bekleyiniz...\nServer Bağlantısı Yapılıyor");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
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



                        Log.e("doInBackground", "Kategori Bitti Kategori Sayısı = "+ act.getCategories().size() +" ID = " + act.getId());




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




                        Log.e("doInBackground", "Resimler Bitti Resim Sayısı = "+ act.getImages().size() +" ID = " + act.getId());


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
            pDialog.dismiss();
            return null;
        }

        protected void onPostExecute(Void result) {


            for (Activity item:etkinlikler) {
                LatLng ltln = new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
                gmap.addMarker(new MarkerOptions().position(ltln).title(item.getName()));
                gmap.moveCamera(CameraUpdateFactory.newLatLng(ltln));
            }


            super.onPostExecute(result);
        }
    }
}
