package com.mhmdawad.qurancloud.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mhmdawad.qurancloud.MediaPlayer.ListOfMp3FromStorage;
import com.mhmdawad.qurancloud.MediaPlayer.Mp3File;
import com.mhmdawad.qurancloud.MediaPlayer.QuranMediaPlayer;
import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.Service.QuranService;
import com.mhmdawad.qurancloud.database.FavoriteDatabase;
import com.mhmdawad.qurancloud.database.FavoriteEntity;

import java.util.List;

public class MediaPlayerActivity extends AppCompatActivity {

    private SeekBar mSeekBar;
    TextView currentDurationTextView;
    TextView maxDurationTextView;
    private MediaPlayer player = QuranMediaPlayer.getMediaPlayer();
    Runnable mRunnable;
    Handler mHandler;
    TextView readerNameTextView;
    TextView suraNameTextView;
    ImageButton playButton;
    ImageButton forwardButton;
    ImageButton backButton;
    ImageButton listButton;
    ImageButton loveButton;
    Intent intent;
    int currentPosition;
    //private FavoriteDatabase favDB;
    private static String readerName;
    private static String suraName;
    private static String checkReaderName;
    private static String checkSuraName;

    public static String getCheckReaderName() {
        return checkReaderName;
    }

    public static String getCheckSuraName() {
        return checkSuraName;
    }

    public static String getReaderName() {
        return readerName;
    }

    public static String getSuraName() {
        return suraName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        mSeekBar = findViewById(R.id.seekBar);
        currentDurationTextView = findViewById(R.id.currentDuration);
        maxDurationTextView = findViewById(R.id.maxDuration);
        suraNameTextView = findViewById(R.id.suraMediaPlayer);
        readerNameTextView = findViewById(R.id.readerMediaPlayer);
        playButton = findViewById(R.id.playMediaPlayer);
        forwardButton = findViewById(R.id.forwardMediaPlayer);
        backButton = findViewById(R.id.backMediaPlayer);
        listButton = findViewById(R.id.listMediaPlayer);
        loveButton = findViewById(R.id.loveMediaPlayer);
        changeSuraName();
        initializeViews();
        //favDB = FavoriteDatabase.getInstance(MediaPlayerActivity.this);
        checkFavoriteSura();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayImage(player.isPlaying());
                intent = new Intent(MediaPlayerActivity.this, QuranService.class);
                intent.setAction(QuranService.PLAY_OR_PAUSE);
                startService(intent);
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MediaPlayerActivity.this, QuranService.class);
                intent.setAction(QuranService.FORWARD_SURA);
                changePlayImage(false);
                startService(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MediaPlayerActivity.this, QuranService.class);
                intent.setAction(QuranService.BACK_SURA);
                changePlayImage(false);
                startService(intent);
            }
        });
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loveButton.setImageResource(R.drawable.ic_heart_red);
                suraName = ReaderDetails.reciterSurasNames.get(QuranMediaPlayer.realPosition);
                readerName = ReaderDetails.getReciterName();
//                favDB.favoriteDao().insert(new FavoriteEntity(suraName, readerName));
                MainActivity.downloadFile(MediaPlayerActivity.this);

            }
        });

    }


    private void checkFavoriteSura() {
        ListOfMp3FromStorage.scanDeviceForMp3Files();
        List<Mp3File> savedList = ListOfMp3FromStorage.getMp3Files();

        for (Mp3File save : savedList) {
            if (save.getReaderName().equals(checkReaderName)
                    && save.getSuraName().equals(checkSuraName)) {
                loveButton.setImageResource(R.drawable.ic_heart_red);
                break;
            } else {
                loveButton.setImageResource(R.drawable.ic_heart_white);
            }
        }
    }

    public void changeSuraName() {
        checkSuraName = ReaderDetails.reciterSurasNames.get(QuranMediaPlayer.realPosition);
        suraNameTextView.setText("سـورة " + checkSuraName);

    }

    private void changePlayImage(boolean isPlaying) {
        if (isPlaying) {
            playButton.setImageResource(R.drawable.ic_play_button_white);
        } else {
            playButton.setImageResource(R.drawable.ic_pause_button_white);
        }
    }

    private void initializeViews() {

        checkReaderName = ReaderDetails.getReciterName();
        currentPosition = QuranService.getCurrentPosition();
        readerNameTextView.setText(checkReaderName);
        mHandler = new Handler();
        mSeekBar.setMax(100);
        changeSuraName();
        updateSeekBar();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mRunnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mRunnable);
                int totalDuration = player.getDuration();
                int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

                player.seekTo(currentPosition);
                mHandler.postDelayed(mRunnable, 1000);
            }
        });
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration;
        totalDuration = (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        return currentDuration * 1000;
    }

    public int getProgressPercentage(long currentDuration, long totalDuration) {
        double percentage;
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        return (int) percentage;
    }

    private String getDurationFormat(int milliseconds) {
        String finalTimerString = "";
        String secondsString;
        String minutesString;
        int hours = (milliseconds / (1000 * 60 * 60));
        int minutes = (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }

        finalTimerString = finalTimerString + minutesString + ":" + secondsString;

        return finalTimerString;
    }

    private void updateSeekBar() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (player.isPlaying()) {
                    mSeekBar.setProgress(getProgressPercentage(player.getCurrentPosition()
                            , player.getDuration()));
                    currentDurationTextView.setText(getDurationFormat(player.getCurrentPosition()));
                    maxDurationTextView.setText(getDurationFormat(player.getDuration()));
                    changeSuraName();
                    changePlayImage(false);
                    intent = new Intent(MediaPlayerActivity.this, QuranService.class);
                    intent.setAction(QuranService.CHECK_NOTIFY);
                    startService(intent);
                } else {
                    changePlayImage(true);

                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }


}


