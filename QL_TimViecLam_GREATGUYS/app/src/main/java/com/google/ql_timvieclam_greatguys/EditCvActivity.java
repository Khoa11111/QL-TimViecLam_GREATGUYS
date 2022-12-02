package com.google.ql_timvieclam_greatguys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.ql_timvieclam_greatguys.Database.Database;

public class EditCvActivity extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    private ImageView arrback_profile;
    private AppCompatButton xacNhan;
    private EditText ten, gioiTinh, ngaySinh, canCuoc, sdt, email, diaChi, bangCap, bangTA, soTruong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_don);

        AnhXa();
        BatSuKien();
    }

    private void AnhXa(){
        arrback_profile = (ImageView) findViewById(R.id.arrback_signup_guidon);
        xacNhan = findViewById(R.id.xacNhan);
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
        onClickXacNhan();
    }

    private void onClickBack(){
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

    private void onClickXacNhan(){
        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditCvActivity.this);
                //Thiết lập tiêu đề cho alert dialog
                alertDialogBuilder.setTitle("Xác nhận chỉnh sửa");
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Bạn có chắc muốn sửa?");
                alertDialogBuilder.setCancelable(false);
                //Button yes
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UpdateValues();
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
    private void UpdateValues(){
        try{
            database.QueryData("UPDATE HoSoCV\n" +
                            "SET accName = '" + ten.getText().toString() + "'," +
                            "accGT  = '" + gioiTinh.getText().toString() + "'," +
                            "accNgaySinh  = '" + ngaySinh.getText().toString() + "'," +
                            "accCMND  = '" + canCuoc.getText().toString() + "'," +
                            "accSDT  = '" + sdt.getText().toString() + "'," +
                            "accEmail  = '" + email.getText().toString() + "'," +
                            "accDiaChi  = '" + diaChi.getText().toString() + "'," +
                            "accBangCap  = '" + bangCap.getText().toString() + "'," +
                            "accBangTA  = '" + bangTA.getText().toString() + "'," +
                            "accSoTruong  = '" + soTruong.getText().toString() + "'\n" +
                            "WHERE id = " + CkLogin.getId(this) + ";");
            Toast.makeText(this,"Thay đổi thành công.",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"Xảy ra lỗi tại 109 lấy dữ liệu. Mời bạn kiểm tra lại",Toast.LENGTH_SHORT).show();
        }
    }
}
