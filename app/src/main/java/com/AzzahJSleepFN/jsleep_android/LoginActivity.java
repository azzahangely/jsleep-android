package com.AzzahJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AzzahJSleepFN.jsleep_android.Model.Account;
import com.AzzahJSleepFN.jsleep_android.request.BaseApiService;
import com.AzzahJSleepFN.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;
    public static Account staticAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        username = findViewById(R.id.Username);

        password = findViewById(R.id.Password);

        Button login = findViewById(R.id.LoginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameLogin = username.getText().toString();
                String passwordLogin = password.getText().toString();
                Account account = requestLogin(usernameLogin, passwordLogin);
                System.out.println("====login button====");
            }
        });

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

//    protected Account requestAccount() {
//        System.out.println("===request account===");
//        mApiService.getAccount(0).enqueue(new Callback<Account>() {
//            @Override
//            public void onResponse(Call<Account> call, Response<Account> response) {
//                if (response.isSuccessful()) {
//                    Account account;
//                    account = response.body();
//                    System.out.println("SUCCESSFUL");
//                    System.out.println(account.toString());
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Account> call, Throwable t) {
//                System.out.println("failed");
//                //System.out.println(t.toString());
//                Toast.makeText(mContext, "no Account id=0", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return null;
//    }

    protected Account requestLogin(String username, String password){
        System.out.println("===REQUEST LOGIN===");
        mApiService.login(username, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    staticAccount = account;
                    System.out.println("SUCCESSFUL");
                    System.out.println(account.toString());
                    Toast.makeText(mContext, "Logged in, welcome!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Login Failed!", Toast.LENGTH_SHORT).show();
                System.out.println("Failed!");
            }
        });
        return null;
    }
}