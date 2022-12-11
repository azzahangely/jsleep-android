package com.AzzahJSleepFN.jsleep_android;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.AzzahJSleepFN.jsleep_android.Model.Account;
import com.AzzahJSleepFN.jsleep_android.Model.Renter;
import com.AzzahJSleepFN.jsleep_android.request.BaseApiService;
import com.AzzahJSleepFN.jsleep_android.request.UtilsApi;

import java.sql.SQLOutput;

/**
 * About Me Activity represent the summary of profile activity of this application.
 * This section will display such as name, email, and balance of user.
 * This activity should be the same section to Top Up user's balance.
 *
 * @author Azzah Angeli
 * @version 1.0
 *
 */
public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mBaseApiService;
    Context mContext;

    TextView name, email, balance, accountName, accountEmail, accountBalance;
    TextView display_phone_number, display_name, display_address;
    Button RegisterRenter_button,  cancel_register_button, register_fixed_button, topup_button;
    CardView card_layout_RegisterRenter, card_layout_input_register, card_layout_renter;
    EditText amount,editName, editAddress, editPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //default layout
        setContentView(R.layout.activity_about_me);
        mBaseApiService = UtilsApi.getApiService();

        accountName = findViewById(R.id.accountName);
        accountEmail = findViewById(R.id.accountEmail);
        accountBalance = findViewById(R.id.balance_num);
        accountName.setText(LoginActivity.staticAccount.name);
        accountEmail.setText(LoginActivity.staticAccount.email);
        accountBalance.setText(String.valueOf(LoginActivity.staticAccount.balance));


        //top up amount
        topup_button = findViewById(R.id.btnTopUp);
        amount = findViewById(R.id.amount);
        topup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(LoginActivity.staticAccount.id);
                System.out.println(Double.parseDouble(amount.getText().toString()));
                requestTopUp(LoginActivity.staticAccount.id, Double.parseDouble(amount.getText().toString()));
            }
        });

        //cardview 1 -> renter == null
        RegisterRenter_button = findViewById(R.id.btnRegisterRenter);
        card_layout_RegisterRenter = findViewById(R.id.cardview1);

        //cardview 2 -> register renter button has clicked
        card_layout_input_register = findViewById(R.id.cardview2);
        editName = findViewById(R.id.editName);
        editAddress =findViewById(R.id.editAddress);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        register_fixed_button = findViewById(R.id.btnRegister);
        cancel_register_button = findViewById(R.id.btnCancel);

        //cardview 3 -> renter has just registered now
        card_layout_renter = findViewById(R.id.cardview3);

        display_name = findViewById(R.id.registeredName);
        display_address = findViewById(R.id.registeredAddress);
        display_phone_number = findViewById(R.id.registeredPhoneNumber);


        RegisterRenter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
                card_layout_input_register.setVisibility(View.VISIBLE);
                card_layout_renter.setVisibility(View.INVISIBLE);
            }
        });
        if(LoginActivity.staticAccount.renter == null) {
            card_layout_RegisterRenter.setVisibility(View.VISIBLE);
            card_layout_renter.setVisibility(View.INVISIBLE);
            card_layout_input_register.setVisibility(View.INVISIBLE);
        }
        else {
            card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
            card_layout_renter.setVisibility(View.VISIBLE);
            card_layout_input_register.setVisibility(View.INVISIBLE);
            display_name.setText(LoginActivity.staticAccount.renter.username);
            display_address.setText(LoginActivity.staticAccount.renter.address);
            display_phone_number.setText(LoginActivity.staticAccount.renter.phoneNumber);
        }

            register_fixed_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card_layout_renter.setVisibility(View.VISIBLE);
                    card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
                    card_layout_input_register.setVisibility(View.INVISIBLE);
                    Renter renter = requestRegisterRenter(LoginActivity.staticAccount.id, editName.getText().toString(), editAddress.getText().toString(), editPhoneNumber.getText().toString());
                }
            });
            cancel_register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
                    card_layout_renter.setVisibility(View.INVISIBLE);
                    card_layout_input_register.setVisibility(View.VISIBLE);
                }
            });
        }
    protected Renter requestRegisterRenter(int id, String username, String address, String phoneNumber ) throws NullPointerException {
        System.out.println("Id: " + id);
        System.out.println("Username: " + username);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNumber);
        mBaseApiService.registerRenter(id, username, address, phoneNumber).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    System.out.println("Register succeed!");
                    LoginActivity.staticAccount.renter = response.body();
                    Intent intent = new Intent(AboutMeActivity.this,AboutMeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Register Renter Has Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    protected Boolean requestTopUp(int id, double balance ){
        mBaseApiService.topUp(id,balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Boolean display_topUp = response.body();
                    System.out.println("Top Up Successful!") ;
                    //LoginActivity.reloadAccount(LoginActivity.staticAccount.id);//
                    Toast.makeText(mContext, "Top Up Successful", Toast.LENGTH_SHORT).show();
                    LoginActivity.staticAccount.balance = LoginActivity.staticAccount.balance + balance;
                    recreate();
                    System.out.println("Top Up = DONE");
                    Intent intent = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("Top Up Error!");
                System.out.println(t.toString());
                Toast.makeText(mContext,"Top Up Failed",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        if (LoginActivity.staticAccount.renter != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.top_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addbox_button) {
            Intent intent = new Intent(AboutMeActivity.this, CreateRoomActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


