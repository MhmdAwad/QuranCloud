package com.mhmdawad.qurancloud.Activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mhmdawad.qurancloud.MediaPlayer.ListOfMp3FromStorage;
import com.mhmdawad.qurancloud.MediaPlayer.Mp3File;
import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.RecyclerViewAdapters.FavoriteSurasAdapter;
import com.mhmdawad.qurancloud.database.FavoriteDatabase;
import com.mhmdawad.qurancloud.database.FavoriteEntity;

import java.io.IOException;
import java.util.List;

public class FavoriteSuras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_suras);

        setupRecyclerView();

    }
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.favRecyclerView);
//        FavoriteDatabase db = FavoriteDatabase.getInstance(this);
        ListOfMp3FromStorage.scanDeviceForMp3Files();
        List<Mp3File> favoriteList = ListOfMp3FromStorage.getMp3Files() ;

        FavoriteSurasAdapter adapter = new FavoriteSurasAdapter(favoriteList);

        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteSuras.this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new FavoriteSurasAdapter.ClickListener() {
            @Override
            public void itemOnClickListener(int pos, View v) {
                final String n = ReaderDetails.getReciterName();
                Toast.makeText(FavoriteSuras.this,n , Toast.LENGTH_SHORT).show();
                ListOfMp3FromStorage.scanDeviceForMp3Files();
                List<Mp3File> mp3Files = ListOfMp3FromStorage.getMp3Files();
                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(FavoriteSuras.this, Uri.parse(mp3Files.get(pos).getSuraPath()));
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
