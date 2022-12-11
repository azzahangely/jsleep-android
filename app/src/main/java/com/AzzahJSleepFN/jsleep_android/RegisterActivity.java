package com.AzzahJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AzzahJSleepFN.jsleep_android.Model.Account;
import com.AzzahJSleepFN.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.AzzahJSleepFN.jsleep_android.request.BaseApiService;

/**
 * The Register Activity allows a user to create a new account on the app.
 * @author Azzah Angeli
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText name, email, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        name = findViewById(R.id.name_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_register = name.getText().toString();
                String email_register = email.getText().toString();
                String pass_register = password.getText().toString();
                Account account_register = requestRegister(name_register, email_register, pass_register);
            }
        });
    }

    protected Account requestRegister(String name, String email, String password){
        System.out.println("===request register===");
        mApiService.register(name, email, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    System.out.println("===request register successfull===");
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("===request register failed===");
                Toast.makeText(mContext, "Account has already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}