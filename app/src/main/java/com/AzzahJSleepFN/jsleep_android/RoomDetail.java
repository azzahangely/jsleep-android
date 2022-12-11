package com.AzzahJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import com.AzzahJSleepFN.jsleep_android.Model.*;
import com.AzzahJSleepFN.jsleep_android.request.BaseApiService;

import java.util.List;

/**
 * This activity represent every details such as Name of the renter, bed type, price, room size,
 * and address, that has been created on create room activity.
 * The details should be shown as a listview
 *
 * @author Azzah Angeli
 * @version 1.0
 *
 */

public class RoomDetail extends AppCompatActivity {
    Context mContext;
    protected static Room RoomDetail = MainActivity.roomList.get(MainActivity.roomId);
    TextView detailName, detailBedType, detailSize, detailPrice, detailAddress;
    CheckBox AC,WiFi,SwimmingPool,Bathtub,FitnessCenter,Balcony,Restaurant,Refrigerator;
    List<Facility> facilities = MainActivity.roomList.get(MainActivity.roomId).facility;
    Button checkOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}
        setContentView(R.layout.activity_room_detail);

        detailName = findViewById(R.id.roomsName);
        detailBedType = findViewById(R.id.roomsBedType);
        detailSize = findViewById(R.id.roomsSize);
        detailPrice = findViewById(R.id.roomsPrice);
        detailAddress= findViewById(R.id.roomsAddress);

        checkOut = findViewById(R.id.CheckOutRoom);
        AC = findViewById(R.id.AC);
        Refrigerator = findViewById(R.id.Refrigerator);
        WiFi = findViewById(R.id.WiFi);
        Bathtub = findViewById(R.id.Bathtub);
        Balcony = findViewById(R.id.Balcony);
        Restaurant = findViewById(R.id.Restaurant);
        SwimmingPool = findViewById(R.id.SwimmingPool);
        FitnessCenter = findViewById(R.id.Fitnesscenter);

        detailName.setText(RoomDetail.name);
        String price = "IDR " + String.valueOf(RoomDetail.price.price);
        detailPrice.setText(price);
        String size = String.valueOf(RoomDetail.size) + " m2";
        detailSize.setText(size);
        detailAddress.setText(RoomDetail.address);
        String bed = RoomDetail.bedType.toString() + " bed";
        detailBedType.setText(bed);

        for(Facility facility : facilities){
            if(facility == Facility.AC){
                AC.setChecked(true);
            } else if(facility == Facility.Refrigerator){
                Refrigerator.setChecked(true);
            } else if(facility == Facility.WiFi){
                WiFi.setChecked(true);
            } else if(facility == Facility.Bathub){
                Bathtub.setChecked(true);
            } else if(facility == Facility.Balcony){
                Balcony.setChecked(true);
            } else if(facility == Facility.Restaurant){
                Restaurant.setChecked(true);
            } else if(facility == Facility.SwimmingPool){
                SwimmingPool.setChecked(true);
            } else if(facility == Facility.FitnessCenter){
                FitnessCenter.setChecked(true);
            }
        }
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}