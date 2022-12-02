package com.google.ql_timvieclam_greatguys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CVActivity extends AppCompatActivity {
    private ImageView arrback_main;
    private ImageButton edit_cv_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        AnhXa();
        BatSuKien();
    }

    private void AnhXa(){
        arrback_main = (ImageView) findViewById(R.id.arrback_main);
        edit_cv_button = (ImageButton) findViewById(R.id.edit_cv_button);
    }

    private void BatSuKien(){
        onClickBack();
        onClickBtnEdit();
    }

    private void onClickBack(){
        arrback_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(CVActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }

    private void onClickBtnEdit(){
        edit_cv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(CVActivity.this, EditCvActivity.class);
                startActivity(editProfile);
            }
        });
    }
}
