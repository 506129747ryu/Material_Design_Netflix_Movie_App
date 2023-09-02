package com.tutorial.movieapp.Activity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.tutorial.movieapp.Adapter.ImageListAdapter;
import com.tutorial.movieapp.Domain.FilmItem;
import com.tutorial.movieapp.R;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieDateTxt, movieSummaryInfo, movieActorsInfo;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg;
    private RecyclerView.Adapter adapterImgList;
    private  RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idFilm = getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET,
                "https://moviesapi.ir/api/v1/movies/" + idFilm,
                response -> {
                    Gson gson = new Gson();
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    FilmItem item = gson.fromJson(response, FilmItem.class);

                    Glide.with(DetailActivity.this)
                            .load(item.getPoster())
                            .into(pic1);

                    Glide.with(DetailActivity.this)
                            .load(item.getPoster())
                            .into(pic2);

                    titleTxt.setText(item.getTitle());
                    movieRateTxt.setText(item.getRated());
                    movieTimeTxt.setText(item.getRuntime());
                    movieDateTxt.setText(item.getReleased());
                    movieSummaryInfo.setText(item.getPlot());
                    movieActorsInfo.setText(item.getActors());

                    if(item.getImages() != null){
                        adapterImgList = new ImageListAdapter(item.getImages());
                        recyclerView.setAdapter(adapterImgList);
                    }
                }, error -> {
                    progressBar.setVisibility(View.GONE);
                    Log.i("errortag","onErrorResponse: "+error.toString());
                });
            mRequestQueue.add(mStringRequest);


    }

    private void initView() {

        titleTxt = findViewById(R.id.movieNameTxt);
        progressBar = findViewById(R.id.detailLoading);
        scrollView = findViewById(R.id.scrollView2);
        pic1 = findViewById(R.id.posterNormalImg);
        pic2 = findViewById(R.id.posterBigImg);
        movieRateTxt = findViewById(R.id.movieRateTxt);
        movieTimeTxt = findViewById(R.id.movieTimeTxt);
        movieDateTxt = findViewById(R.id.movieDateTxt);
        movieSummaryInfo = findViewById(R.id.movieSummaryTxt);
        movieActorsInfo = findViewById(R.id.movieActorInfo);
        backImg = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.imagesRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        backImg.setOnClickListener(v -> finish());


    }
}
