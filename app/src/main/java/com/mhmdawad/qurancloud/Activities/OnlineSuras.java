package com.mhmdawad.qurancloud.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.RecyclerViewAdapters.MainActivityAdapter;
import com.mhmdawad.qurancloud.Retrofit.ApiEndPoint;
import com.mhmdawad.qurancloud.Retrofit.Objects.QuranData;
import com.mhmdawad.qurancloud.Retrofit.Objects.Reciter;
import com.mhmdawad.qurancloud.Retrofit.QuranResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineSuras extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private ArrayList<Reciter> reciters;
    MainActivityAdapter adapter;

    static final String RECITER_NAME = "name";
    static final String SURAS = "suras";
    static final String SURAS_LINK = "suraLink";
    static final String SURAS_COUNT = "surasCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_sura);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.mainRecyclerView);
        fetchAPIDate();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(OnlineSuras.this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void fetchAPIDate() {
        ApiEndPoint apiEndPoint = QuranResponse.createResponse().create(ApiEndPoint.class);
        apiEndPoint.getQuranData().enqueue(new Callback<QuranData>() {
            @Override
            public void onResponse(Call<QuranData> call, Response<QuranData> response) {
                assert response.body() != null;
                progressBar.setVisibility(View.GONE);
                reciters = (ArrayList<Reciter>) response.body().getReciters();
                adapter = new MainActivityAdapter(reciters);

                setupRecyclerView();

                adapter.setOnClickListener(new MainActivityAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int pos, View view) {

                        Intent intent = new Intent(OnlineSuras.this, ReaderDetails.class);
                        intent.putExtra(RECITER_NAME, reciters.get(pos).getName());
                        intent.putExtra(SURAS_LINK, reciters.get(pos).getServer());
                        intent.putExtra(SURAS, reciters.get(pos).getSuras());
                        intent.putExtra(SURAS_COUNT, reciters.get(pos).getCount());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<QuranData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(OnlineSuras.this, "Can't Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


