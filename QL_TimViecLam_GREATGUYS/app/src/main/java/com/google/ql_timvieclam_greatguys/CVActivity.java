package com.google.ql_timvieclam_greatguys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.Database.Database;

public class CVActivity extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    private ImageView arrback_main;
    private ImageButton edit_cv_button;
    private TextView ten, gioiTinh, ngaySinh, canCuoc, sdt, email, diaChi, bangCap, bangTA, soTruong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        AnhXa();
        setValues();
        BatSuKien();
    }

    private void AnhXa(){
        arrback_main = (ImageView) findViewById(R.id.arrback_main);
        edit_cv_button = (ImageButton) findViewById(R.id.edit_cv_button);
        ten = findViewById(R.id.ten);
        gioiTinh = findViewById(R.id.gioiTinh);
        ngaySinh = findViewById(R.id.ngaySinh);
        canCuoc = findViewById(R.id.canCuoc);
        sdt = findViewById(R.id.sdt);
        email = findViewById(R.id.email);
        diaChi = findViewById(R.id.diaChi);
        bangCap = findViewById(R.id.bangCap);
        bangTA = findViewById(R.id.bangTA);
        soTruong = findViewById(R.id.soTruong);
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
    private void setValues(){
        try {
            Cursor data = database.GetData("SELECT * From HoSoCV where id = '" + CkLogin.getId(this) + "'");
            while (data.moveToNext()) {
                ten.setText(TestEmpty(data.getString(1)));
                gioiTinh.setText(TestEmpty(data.getString(2)));
                ngaySinh.setText(TestEmpty(data.getString(3)));
                canCuoc.setText(TestEmpty(data.getString(4)));
                sdt.setText(TestEmpty(data.getString(5)));
                email.setText(TestEmpty(data.getString(6)));
                diaChi.setText(TestEmpty(data.getString(7)));
                bangCap.setText(TestEmpty(data.getString(8)));
                bangTA.setText(TestEmpty(data.getString(9)));
                soTruong.setText(TestEmpty(data.getString(10)));
            }
        } catch (Exception e) {
            Toast.makeText(this,"Xảy ra lỗi tại 73 lấy dữ liệu. Mời bạn kiểm tra lại",Toast.LENGTH_SHORT).show();
        }
    }
    private String TestEmpty(String text){
        if(text == null)
            return ("Để Trống");
        if(text.isEmpty())
            return ("Để Trống");
        return text;
    }
}
