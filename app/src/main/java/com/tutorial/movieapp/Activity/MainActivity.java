package com.tutorial.movieapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tutorial.movieapp.Adapter.FilmListAdapter;
import com.tutorial.movieapp.Domain.FilmItem;
import com.tutorial.movieapp.Domain.ListFilm;
import com.tutorial.movieapp.R;

import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpComming;
    private RecyclerView recylerViewNewMovies, recyclerViewUpComming;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loading1, loading2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequest1();
        sendRequest2();
    }

    private void sendRequest1() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(
                Request.Method.GET,
                "https://moviesapi.ir/api/v1/movies?page=1",
                response -> {
                    Gson gson = new Gson();
                    loading1.setVisibility(View.GONE);

                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    adapterNewMovies = new FilmListAdapter(items);
                    recylerViewNewMovies.setAdapter(adapterNewMovies);
                }, error -> {
                    loading1.setVisibility(View.GONE);
                });

        mRequestQueue.add(mStringRequest);
    }

    private void sendRequest2() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(
                Request.Method.GET,
                "https://moviesapi.ir/api/v1/movies?page=3",
                response -> {
                    Gson gson = new Gson();
                    loading2.setVisibility(View.GONE);

                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    adapterUpComming = new FilmListAdapter(items);
                    recyclerViewUpComming.setAdapter(adapterUpComming);
                }, error -> {
            loading2.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest2);
    }

    private void initView() {
        recylerViewNewMovies = findViewById(R.id.view1);
        recylerViewNewMovies.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        recyclerViewUpComming = findViewById(R.id.view2);
        recyclerViewUpComming.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);

    }
}
