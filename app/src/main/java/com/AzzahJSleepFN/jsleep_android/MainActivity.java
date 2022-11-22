package com.AzzahJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.AzzahJSleepFN.jsleep_android.Model.*;
import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    Gson gson = new Gson();
    protected static Account account_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream filepath = null;

        ArrayList<Room> roomList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();

        try {
            filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] temp = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, temp);
        } catch (IOException t) {
            t.printStackTrace();
        }
        for (Room temp : roomList ) {
            idList.add(temp.name);
        }
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,idList);
        ListView view = findViewById(R.id.listView);
        view.setAdapter(roomAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
        switch (item.getItemId()){
            case R.id.person_button:
                Toast.makeText(this, "About me", Toast.LENGTH_SHORT).show();
                startActivity(aboutMeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}