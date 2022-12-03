package com.google.ql_timvieclam_greatguys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.Database.Database;

public class ProfileActivity extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    private ImageView arrback_main;
    private ImageButton edit_profile_button;
    private TextView tvCountCv,tvUsername, tvName, tvDoB, tvPhone, tvEmail, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AnhXa();
        LoadData();
        BatSuKien();
    }

    private void AnhXa(){
        arrback_main = (ImageView) findViewById(R.id.arrback_main);
        edit_profile_button = (ImageButton) findViewById(R.id.edit_profile_button);
        tvCountCv = (TextView) findViewById(R.id.tv_countCv);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvName = (TextView) findViewById(R.id.tv_accName);
        tvDoB = (TextView) findViewById(R.id.tv_accDoB);
        tvPhone = (TextView) findViewById(R.id.tv_accPhone);
        tvEmail = (TextView) findViewById(R.id.tv_accEmail);
        tvAddress = (TextView) findViewById(R.id.tv_accAddress);
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
                startActivity(main);
            }
        });
    }

    private void onClickBtnEdit(){
        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(editProfile);
            }
        });
    }

    private  void LoadData(){
        Cursor data = database.GetData("Select accName, accDoB, accSDT, accEmail, accThanhPho " +
                "From AccUserInfor " +
                "Where accEmail = '"+CkLogin.ckLogin+"'");

        while (data.moveToNext()){
            String txt_accName = data.getString(0);
            String txt_accDoB = data.getString(1);
            String txt_accPhone = data.getString(2);
            String txt_accEmail = data.getString(3);
            String txt_accAddress = data.getString(4);
            tvUsername.setText(txt_accName);
            tvName.setText(txt_accName);
            tvDoB.setText(txt_accDoB);
            tvPhone.setText(txt_accPhone);
            tvEmail.setText(txt_accEmail);
            tvAddress.setText(txt_accAddress);
        }

        data = database.GetData("Select count(id) " +
                "From HoSoCV " +
                "Where accEmail = '"+CkLogin.ckLogin+"'");

        while (data.moveToNext()){
            String txt_countCV = data.getString(0);
            tvCountCv.setText(txt_countCV + " đơn xin việc");
        }
    }
}
