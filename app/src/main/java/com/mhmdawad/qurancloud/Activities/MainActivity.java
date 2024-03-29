package com.mhmdawad.qurancloud.Activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mhmdawad.qurancloud.Fragment.OfflineSuraFragment;
import com.mhmdawad.qurancloud.Fragment.OnlineSuraFragment;
import com.mhmdawad.qurancloud.MediaPlayer.QuranMediaPlayer;
import com.mhmdawad.qurancloud.R;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private boolean isOnlineFragmentOpen = true;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            selectedFragment = new OnlineSuraFragment();
                            isOnlineFragmentOpen = true;
                            break;
                        case R.id.favorite:
                            selectedFragment = new OfflineSuraFragment();
                            isOnlineFragmentOpen = false;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setElevation(0);
//        ViewPager viewPager = findViewById(R.id.pager);
//        TabLayout tableLayout = findViewById(R.id.tab);
//        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(fragmentAdapter);
//        tableLayout.setupWithViewPager(viewPager);

        NavHostFragment navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController=navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNav,navController);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,
//                new OnlineSuraFragment()).commit();
//        checkPermissions();
        //createFolder();
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (!(grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            checkPermissions();
//        }
//    }

    private void checkPermissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                    MainActivity.this
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }
                    , 1);
        }
    }

    public static void downloadFile(Context context) {
        DownloadManager.Request request = new DownloadManager.Request(QuranMediaPlayer.getSuraUri());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        String nameOfFile = MediaPlayerActivity.getReaderName() + "," + MediaPlayerActivity.getSuraName();
        request.setDestinationInExternalFilesDir(context, "", nameOfFile + ".mp3");

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (isOnlineFragmentOpen) {
//                    OnlineSuraFragment.adapter.getFilter().filter(newText);
//                } else {
//                    OfflineSuraFragment.adapter.getFilter().filter(newText);
//                }
                return false;
            }
        });
        return true;
    }


//    private void createFolder(Context context) {
//        String rootPath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath() + "/data/0/" + context.getPackageName() + "/";
//        try {
//            File root = new File(rootPath);
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            File f = new File(rootPath + ".");
//            Log.v("mm", rootPath);
//            if (f.exists()) {
//                f.delete();
//            }
//            f.createNewFile();
//
//            FileOutputStream out = new FileOutputStream(f);
//
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
