package com.example.mymusicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.File;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity
{
    Button next,previous,pause;
    TextView textView2;
    SeekBar seekBar2;

    static MediaPlayer mp;
    int pos;
    String songname;

    ArrayList<File> mySongs;
    Thread updatesk;
    ActionBar actionBar;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        pause = (Button) findViewById(R.id.pause);
        textView2 = (TextView) findViewById(R.id.textView2);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00BFFF")));

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updatesk = new Thread() {

            @Override
            public void run() {
                int totalTime = mp.getDuration();
                int currentPos = 0;

                while (currentPos < totalTime) {
                    try {
                        sleep(500);
                        currentPos = mp.getCurrentPosition();
                        seekBar2.setProgress(currentPos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        if (mp != null) {
            mp.stop();
            mp.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");

        songname = mySongs.get(pos).getName();

        String sname = i.getStringExtra("songname");

        textView2.setText(sname);
        textView2.setSelected(true);

        pos = bundle.getInt("pos", 0);

        Uri u = Uri.parse(mySongs.get(pos).toString());

        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        seekBar2.setMax(mp.getDuration());
        updatesk.start();

        seekBar2.getProgressDrawable().setColorFilter(getResources().getColor(R.color.dark_blue), PorterDuff.Mode.MULTIPLY);
        seekBar2.getThumb().setColorFilter(getResources().getColor(R.color.dark_blue), PorterDuff.Mode.SRC_IN);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar2.getProgress());
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar2.setMax(mp.getDuration());
                if (mp.isPlaying()) {
                    pause.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                    mp.pause();
                } else {
                    pause.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    mp.start();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.release();

                if((pos+1) > (mySongs.size()-1))
                {
                    pos = 0;
                }
                else
                {
                    pos=pos+1;
                }

                Uri u = Uri.parse(mySongs.get(pos).toString());

                mp = MediaPlayer.create(getApplicationContext(), u);

                songname = mySongs.get(pos).toString();
                textView2.setText(songname);

                mp.start();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.release();

                if((pos-1)<0)
                {
                     pos = mySongs.size()-1;
                }
                else
                {
                    pos=pos-1;
                }

                Uri u = Uri.parse(mySongs.get(pos).toString());

                mp = MediaPlayer.create(getApplicationContext(), u);

                songname = mySongs.get(pos).toString();
                textView2.setText(songname);

                mp.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
