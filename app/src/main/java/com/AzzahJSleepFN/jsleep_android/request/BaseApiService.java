package com.AzzahJSleepFN.jsleep_android.request;


import retrofit2.Call;
import com.AzzahJSleepFN.jsleep_android.Model.*;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);

}
