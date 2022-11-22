package com.AzzahJSleepFN.jsleep_android.request;

import com.AzzahJSleepFN.jsleep_android.Model.Account;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("username") String username, @Query("password") String password);

    @GET("account/register")
    Call<Account> register (@Query("name") String name, @Query("email") String email, @Query("password") String password);
}