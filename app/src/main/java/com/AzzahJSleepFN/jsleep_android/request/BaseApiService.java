package com.AzzahJSleepFN.jsleep_android.request;

import com.AzzahJSleepFN.jsleep_android.Model.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register (@Query("name") String name, @Query("email") String email, @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("account/{id}/topup")
    Call<Boolean>topUp(@Path("id") int id, @Query("amount") double amount);

    @POST("room/create")
    Call<Room> room(
            @Query("accountId") int id,
            @Query("name") String name,
            @Query("price") int price,
            @Query("address") String address,
            @Query("size") int size,
            @Query("facility") ArrayList<Facility> facility,
            @Query("city") City city,
            @Query("bedType") BedType bedType);


    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize") int pageSize);


//    @POST("payment/create")
//    Call<Payment> payment(@Query("buyerId") int buyerId, @Query("renterId") int renterId, @Query("roomId") int roomId, @Query("from") String from, @Query("to") String to);

}