package com.google.ql_timvieclam_greatguys;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.database.Cursor;
import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.Database.Database;

public class EditProfileActivity extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    private ImageView arrback_profile;
    private Button udpUserInf;
    private EditText edtName, edtPhone, edtEmail, edtAddress;
    private TextView edtDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        AnhXa();
        LoadData();
        BatSuKien();
    }

    private void AnhXa(){
        arrback_profile = (ImageView) findViewById(R.id.arrback_profile);
        udpUserInf = (Button) findViewById(R.id.btn_udp_userInf);
        edtName = (EditText) findViewById(R.id.edt_accName);
        edtDOB = (TextView) findViewById(R.id.edt_accDoB);
        edtPhone = (EditText) findViewById(R.id.edt_accPhone);
        edtEmail = (EditText) findViewById(R.id.edt_accEmail);
        edtAddress = (EditText) findViewById(R.id.edt_accAddress);
    }

    private void LoadData(){
        Cursor data = database.GetData("Select accName, accDoB, accSDT, accEmail, accThanhPho " +
                "From AccUserInfor " +
                "Where accEmail = '"+CkLogin.ckLogin+"'");

        while (data.moveToNext()){
            String txt_accName = data.getString(0);
            String txt_accDoB = data.getString(1);
            String txt_accPhone = data.getString(2);
            String txt_accEmail = data.getString(3);
            String txt_accAddress = data.getString(4);
            edtName.setText(txt_accName);
            edtDOB.setText(txt_accDoB);
            edtPhone.setText(txt_accPhone);
            edtEmail.setText(txt_accEmail);
            edtAddress.setText(txt_accAddress);
        }
    }

    private void BatSuKien(){
        OnClickDob();
        OnClickBack();
        OnClickBtnUpdate();
    }

    private void OnClickDob(){
        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
    }

    private void OnClickBack(){
        arrback_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongBaoThoat();
            }
        });
    }

    private void OnClickBtnUpdate(){
        udpUserInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongBaoXacNhan();
            }
        });
    }
    private void ThongBaoThoat(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
        //Thi???t l???p ti??u ????? cho alert dialog
        alertDialogBuilder.setTitle("R???i kh???i ch???nh s???a");
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("B???n c?? ch???c tho??t?");
        alertDialogBuilder.setCancelable(false);
        //Button yes
        alertDialogBuilder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BackToProfile();
            }
        });
        //Button no
        alertDialogBuilder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ThongBaoXacNhan(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
        //Thi???t l???p ti??u ????? cho alert dialog
        alertDialogBuilder.setTitle("X??c nh???n ch???nh s???a");
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("B???n c?? ch???c mu???n c???p nh???t?");
        alertDialogBuilder.setCancelable(false);
        //Button yes
        alertDialogBuilder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UpdateValue();
                BackToProfile();
            }
        });
        //Button no
        alertDialogBuilder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ChonNgay(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //i: n??m, i1: th??ng, i2: ng??y
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDOB.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void  UpdateValue(){
        String txt_accName = edtName.getText().toString().trim();
        String txt_accDoB = edtDOB.getText().toString().trim();
        String txt_accPhone = edtPhone.getText().toString().trim();
        String txt_accEmail = edtEmail.getText().toString().trim();
        String txt_accAddress = edtAddress.getText().toString().trim();
        //C???p nh???t th??ng tin m???i v??o database
        String sql = "UPDATE AccUserInfor " +
                "SET accName  = '" + txt_accName + "'," +
                "accDoB  = '" + txt_accDoB + "'," +
                "accSDT  = '" + txt_accPhone + "'," +
                "accEmail  = '" + txt_accEmail + "'," +
                "accThanhPho  = '" + txt_accAddress + "' " +
                "WHERE id = " + CkLogin.getId(this) + ";";
        database.QueryData(sql);
        CkLogin.ckLogin = txt_accEmail;
    }

    private void BackToProfile(){
        //Chuy???n v??? trang th??ng tin c?? nh??n
        Intent profile = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(profile);
    }
}
