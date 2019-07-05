package com.example.mymusicplayer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MusicList extends AppCompatActivity {

    ActionBar actionBar;
    ListView songsList;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00BFFF")));
        songsList = findViewById(R.id.listView);
        permission();
    }

    public void permission()
    {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findSong(File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for(File singleFile: files)
        {
            if(singleFile.isDirectory() && !singleFile.isHidden())
            {
                arrayList.addAll(findSong(singleFile));
            }
            else
            {
                if(singleFile.getName().endsWith(".mp3"))
                {
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    void display()
    {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for(int i=0;i<mySongs.size();i++)
        {
            items[i] = mySongs.get(i).getName().replace(".mp3","");
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        songsList.setAdapter(myAdapter);

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String songName = songsList.getItemAtPosition(i).toString();
                startActivity(new Intent(getApplicationContext(),SecondActivity.class)
                        .putExtra("songs",mySongs).putExtra("songname",songName)
                        .putExtra("pos",i));
            }
        });
    }


}
