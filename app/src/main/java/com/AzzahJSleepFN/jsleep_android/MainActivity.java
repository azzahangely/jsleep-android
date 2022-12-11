package com.AzzahJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import com.AzzahJSleepFN.jsleep_android.request.BaseApiService;
import com.AzzahJSleepFN.jsleep_android.request.UtilsApi;
import com.google.gson.Gson;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.AzzahJSleepFN.jsleep_android.Model.*;
import java.io.*;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Account LoginAccount;
    public static Room RoomDetail;
    protected static Account RegisterAccount;
    Context mContext;
    BaseApiService mBaseApiService;
    ListView listView;
    EditText pageNum;
    Button go, prev;
    static ArrayList<Room> roomList = new ArrayList<Room>();
    List<Room> getRoom;
    int page = 1;
    int pageSize = 10;

    public static int roomId;
    String name;
    public static List<Room> getRoomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBaseApiService = UtilsApi.getApiService();
        mContext = this;
        requestRoom();

        //find by id component
        go = findViewById(R.id.go_button);
        prev = findViewById(R.id.prev_button);
        listView = findViewById(R.id.listViewMain);
        pageNum = findViewById(R.id.pageNum);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
                System.out.println(page);
                pageNum.setText(String.valueOf(page));
                requestRoom();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page > 1) {
                    page--;
                    System.out.println(page);
                    pageNum.setText(String.valueOf(page));
                    requestRoom();
                } else if (page == 1) {
                    Toast.makeText(mContext, "You're on the first page!", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        if (LoginActivity.staticAccount.renter == null) {
            menu.findItem(R.id.addbox_button).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Toast.makeText(this, "About me", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent1);
                return true;

            case R.id.addbox_button:
                Toast.makeText(this, "create room", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    protected List<Room> requestRoom(){
        pageNum = findViewById(R.id.pageNum);
        listView = findViewById(R.id.listViewMain);
        String psizestr = pageNum.getText().toString();
        mBaseApiService.getAllRoom(page - 1, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    ArrayList<String> ListId = new ArrayList<>();
                    getRoomList = response.body();
                    for(Room room : getRoomList){
                        ListId.add(room.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, ListId);
                    listView.setAdapter(adapter);
                    Toast.makeText(mContext, "Display Room Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to display Room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}

//        InputStream filepath = null;
//
//
//        try {
//            filepath = getAssets().open("randomRoomList.json");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
//            //Room[] temp = gson.fromJson(reader, Room[].class);
//            //Collections.addAll(roomList, temp);
//        } catch (IOException t) {
//            t.printStackTrace();
//        }
//        for (Room temp : roomList) {
//            idList.add(temp.name);
//        }
//        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, idList);
//        ListView view = findViewById(R.id.listView);
//        view.setAdapter(roomAdapter);
////    }
//


//    }
