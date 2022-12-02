package com.google.ql_timvieclam_greatguys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditCvActivity extends AppCompatActivity {
    private ImageView arrback_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_don);

        arrback_profile = (ImageView) findViewById(R.id.arrback_signup_guidon);

        arrback_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditCvActivity.this);
                //Thiết lập tiêu đề cho alert dialog
                alertDialogBuilder.setTitle("Rời khỏi chỉnh sửa");
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Bạn có chắc thoát?");
                alertDialogBuilder.setCancelable(false);
                //Button yes
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent profile = new Intent(EditCvActivity.this, CVActivity.class);
                        startActivity(profile);
                    }
                });
                //Button no
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
