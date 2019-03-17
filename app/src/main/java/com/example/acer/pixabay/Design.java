package com.example.acer.pixabay;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Design extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String url1;
    ArrayList<DesignModel> designModels;
    public static final int Loader_Id = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        designModels = new ArrayList<>();

        Intent intent = getIntent();
        String str = intent.getStringExtra("data");

        url1 = "http://pixabay.com/api/?key=10851900-a54b91372f676b2e9a4b166ba&q=" + str;

      if(checkInternetConnection()==true){
          getLoaderManager().initLoader(Loader_Id, null, this);
      }
      else{
          Toast.makeText(this, "check your connection", Toast.LENGTH_SHORT).show();
      }


    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean internet=false;
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {

            internet=true;
        } else {
            internet= false;


        }
        return internet;
    }




    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(url1);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Scanner scanner= new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();

                    }
                    else
                    {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        progressBar.setVisibility(View.GONE);
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("hits");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject value= jsonArray.getJSONObject(i);
                String image=value.getString("webformatURL");
                Log.i("url1",image);
              designModels.add(new DesignModel(image));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new DesignAdapter(this, designModels));

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}
