package com.google.ql_timvieclam_greatguys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private ImageView arrback_main;
    private ImageButton edit_profile_button;
    String ckLogin= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        AnhXa();
        ckLogin = getBundleData();
        BatSuKien();
    }

    private void AnhXa(){
        arrback_main = (ImageView) findViewById(R.id.arrback_main);
        edit_profile_button = (ImageButton) findViewById(R.id.edit_profile_button);
    }

    private void BatSuKien(){
        onClickBack();
        onClickBtnEdit();
    }

    private void onClickBack(){
        arrback_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(ProfileActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("email",ckLogin);
                main.putExtra("data",bundle);
                startActivity(main);
            }
        });
    }

    private void onClickBtnEdit(){
        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(ProfileActivity.this, EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("email",ckLogin);
                editProfile.putExtra("data",bundle);
                startActivity(editProfile);
            }
        });
    }

    private String getBundleData(){
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getBundleExtra("data");
            if (bundle != null){
                String data = bundle.getString("email");
                return data;
            }
            return "";
        }
        return "";
    }

}
