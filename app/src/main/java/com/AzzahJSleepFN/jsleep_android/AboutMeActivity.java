package com.AzzahJSleepFN.jsleep_android;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
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

public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;

    LinearLayout layout_renter, cancel_register;
    TextView name, email, balance, show_address, show_name, show_phone_number;
    TextView display_phone_number, display_name, display_address;
    Button RegisterRenter_button,  cancel_register_button, register_fixed_button, topup_button;
    CardView card_layout_RegisterRenter, card_layout_input_register, card_layout_display;
    EditText amount,renter_address, renter_name, renter_phone_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        name = findViewById(R.id.name_aboutme);
        email = findViewById(R.id.email_aboutme);
        balance = findViewById(R.id.balance_aboutme);
        name.setText(LoginActivity.staticAccount.name);
        email.setText(LoginActivity.staticAccount.email);
        balance.setText(String.valueOf(LoginActivity.staticAccount.balance));

        RegisterRenter_button = findViewById(R.id.RegisterRenter_button);
        card_layout_RegisterRenter = findViewById(R.id.card_layout_RegisterRenter);

        card_layout_input_register = findViewById(R.id.card_layout_input_register);
        renter_address =findViewById(R.id.renter_address);
        renter_name = findViewById(R.id.renter_name);
        renter_phone_number = findViewById(R.id.renter_phone_number);
        register_fixed_button = findViewById(R.id.register_fixed_button);
        cancel_register_button = findViewById(R.id.cancel_register_button);

        card_layout_display = findViewById(R.id.card_layout_display);
        display_name = findViewById(R.id.display_name);
        display_address = findViewById(R.id.display_address);
        display_phone_number = findViewById(R.id.display_phone_number);
        show_name = findViewById(R.id.show_name);
        show_address = findViewById(R.id.show_address);
        show_phone_number = findViewById(R.id.show_phone_number);

        if(LoginActivity.staticAccount.renter == null){
            card_layout_RegisterRenter.setVisibility(View.VISIBLE);
            card_layout_display.setVisibility(View.INVISIBLE);
            card_layout_input_register.setVisibility(View.INVISIBLE);
            RegisterRenter_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
                    card_layout_display.setVisibility(View.INVISIBLE);
                    card_layout_input_register.setVisibility(View.VISIBLE);
                }
            });
            register_fixed_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Renter accountRenter = registerRenter();
                }
            });
            cancel_register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
                    card_layout_display.setVisibility(View.INVISIBLE);
                    card_layout_input_register.setVisibility(View.VISIBLE);
                }
            });
        }

        if(LoginActivity.staticAccount.renter != null){
            card_layout_RegisterRenter.setVisibility(View.INVISIBLE);
            card_layout_display.setVisibility(View.INVISIBLE);
            card_layout_input_register.setVisibility(View.VISIBLE);
        }
    }
    protected Renter registerRenter(){
        mApiService.registerRenter(
                LoginActivity.staticAccount.id,
                renter_name.getText().toString(),
                renter_phone_number.getText().toString(),
                renter_address.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    LoginActivity.staticAccount.renter = response.body();
                    Intent move = new Intent(AboutMeActivity.this,AboutMeActivity.class);
                    startActivity(move);
                }
                else{
                    System.out.println("====failed?====");
                }

            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Register Renter Has Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}



    }
}