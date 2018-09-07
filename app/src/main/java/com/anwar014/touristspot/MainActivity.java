package com.anwar014.touristspot;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anwar014.touristspot.Retrofit.MyApi;
import com.anwar014.touristspot.Retrofit.MyService;
import com.anwar014.touristspot.Retrofit.tourist_model;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.anwar014.touristspot.Utils.NetworkUtil.isInternetConnected;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    NameAdapter mAdapter;
    List<tourist_model> mtourist_modelList;
    String mTag = "mainActivityData", mURL = "";
    ProgressDialog mProgressDialog;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Log.d(mTag, "before service call....");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please Hold On while Loading data from Server...");
        mProgressDialog.setTitle("Loading Data");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        callServer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mProgressDialog.show();
        callServer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mProgressDialog.dismiss();
    }

    private void callServer() {
        if (isInternetConnected(this)) {

            Call<List<tourist_model>> res = MyService.getService().getData();
            res.enqueue(new Callback<List<tourist_model>>() {

                @Override
                public void onResponse(Call<List<tourist_model>> call, Response<List<tourist_model>> response) {
                    Log.d(mTag, "onResponse: " + response.body().size());
                    Log.d(mTag, "Response_Date : " + response.body().get(0).getUrl());
                    try {
                        mtourist_modelList = new ArrayList<tourist_model>();
                        mtourist_modelList.addAll(response.body());
                        mAdapter = new NameAdapter(getBaseContext(), mtourist_modelList);
                        mRecyclerView.setAdapter(mAdapter);
                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }

                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<tourist_model>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(), "An error occur while fetching data from SERVER. Please try again.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        } else {
            mProgressDialog.dismiss();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                Toast toast = Toast.makeText(this, "Please provide Internet permission, to fetch data from SERVER.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
