package com.mhmdawad.qurancloud.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.RecyclerViewAdapters.SurasAdapter;
import com.mhmdawad.qurancloud.Service.QuranService;
import java.util.ArrayList;

public class ReaderDetails extends AppCompatActivity {

    SurasAdapter adapter;
    RecyclerView recyclerView;
    public static String[] suraNumber;
    public static ArrayList<String> reciterSurasNames;
    public static String reciterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_details);

        recyclerView = findViewById(R.id.suraRecyclerView);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        reciterName = bundle.getString(OnlineSuras.RECITER_NAME);
        String surasCount = bundle.getString(OnlineSuras.SURAS);
        String surasLink = bundle.getString(OnlineSuras.SURAS_LINK);
        assert surasCount != null;
        suraNumber = surasCount.split(",");
        String[] allSurasNames = getResources().getStringArray(R.array.suraName);
        reciterSurasNames = new ArrayList<>();

        for (String s : suraNumber) {
            reciterSurasNames.add(allSurasNames[Integer.parseInt(s)]);
        }

        adapter = new SurasAdapter(reciterSurasNames);
        setupRecyclerView();
        setOnClickItem(surasLink);

    }
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ReaderDetails.this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickItem(final String server) {
        adapter.setOnClickListener(new SurasAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent serviceIntent = new Intent(ReaderDetails.this, QuranService.class);
                serviceIntent.setAction(QuranService.RUN_NEW_SURA);
                QuranService.setCurrentPosition(pos);
                QuranService.setCurrentServer(server);
                startService(serviceIntent);

                startActivity(new Intent(ReaderDetails.this,MediaPlayerActivity.class));
            }
        });
    }
    public static String getReciterName() {
        return reciterName;
    }







}
