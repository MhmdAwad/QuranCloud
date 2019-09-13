package com.mhmdawad.qurancloud.Fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mhmdawad.qurancloud.Activities.FavoriteSuras;
import com.mhmdawad.qurancloud.Activities.ReaderDetails;
import com.mhmdawad.qurancloud.MediaPlayer.ListOfMp3FromStorage;
import com.mhmdawad.qurancloud.MediaPlayer.Mp3File;
import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.RecyclerViewAdapters.FavoriteSurasAdapter;

import java.io.IOException;
import java.util.List;

public class OfflineSuraFragment extends Fragment {
    RecyclerView recyclerView;
    public static FavoriteSurasAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorite_suras,container,false);
         recyclerView = view.findViewById(R.id.favRecyclerView);
        setupRecyclerView();

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        if (Build.VERSION.SDK_INT >= 26) {
//            ft.setReorderingAllowed(false);
//        }
//        ft.detach(this).attach(this).commit();
        return view;

    }
    private void setupRecyclerView() {

//        FavoriteDatabase db = FavoriteDatabase.getInstance(this);
        ListOfMp3FromStorage.scanDeviceForMp3Files();
        List<Mp3File> favoriteList = ListOfMp3FromStorage.getMp3Files() ;

        adapter = new FavoriteSurasAdapter(favoriteList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new FavoriteSurasAdapter.ClickListener() {
            @Override
            public void itemOnClickListener(int pos, View v) {
                final String n = ReaderDetails.getReciterName();
                Toast.makeText(getActivity(),n , Toast.LENGTH_SHORT).show();
                ListOfMp3FromStorage.scanDeviceForMp3Files();
                List<Mp3File> mp3Files = ListOfMp3FromStorage.getMp3Files();
                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(getActivity(), Uri.parse(mp3Files.get(pos).getSuraPath()));
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
