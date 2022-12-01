package com.google.ql_timvieclam_greatguys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ql_timvieclam_greatguys.Database.Database;

public class SignUp extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    ImageView imvBack;
    EditText edtEmail, edtPass, edtComfirmPass, edtName, edtSDT;
    AppCompatButton btnSignUp;
    String ckLogin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        ckLogin = getBundleData();
        AnhXa();
        BatSuKien();
    }

    private void AnhXa(){
        imvBack = findViewById(R.id.arrback_signup);
        edtEmail = findViewById(R.id.edt_email_signup);
        edtPass = findViewById(R.id.edt_pass_signup);
        edtComfirmPass = findViewById(R.id.edt_comfirmPass_signup);
        edtName = findViewById(R.id.edt_nameUser_signup);
        edtSDT = findViewById(R.id.edt_sdt_signup);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    private void BatSuKien(){
        onClickBack();
        onClickBtnSignUp();
    }

    private void onClickBack(){
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("email",ckLogin);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
    }

    private void onClickBtnSignUp(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtEmail.getText().toString().isEmpty()
                        || edtPass.getText().toString().isEmpty()
                        || edtComfirmPass.getText().toString().isEmpty()
                        || edtName.getText().toString().isEmpty()
                        || edtSDT.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this,"Bạn cần điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtPass.getText().toString().equals(edtComfirmPass.getText().toString())){
                    checkEmailSignup();
                } else {
                    Toast.makeText(SignUp.this,"Mật khẩu xác nhận chưa chính xác",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void checkEmailSignup(){
        Cursor data = database.GetData("SELECT accEmail FROM AccUserInfor");
        if(data.getCount() == 0){
            InsertData();
            Toast.makeText(this, "Đăng Ký thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("email",edtEmail.getText().toString().trim());
            intent.putExtra("data",bundle);
            startActivity(intent);
        }
        while(data.moveToNext()){
            String checkEmail = data.getString(0);
            if(checkEmail.equals(edtEmail.getText().toString().trim())){
                Toast.makeText(SignUp.this,"Tài khoản đã tồn tại",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        InsertData();
        Toast.makeText(this, "Đăng Ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email",edtEmail.getText().toString().trim());
        intent.putExtra("data",bundle);
        startActivity(intent);
    }

    private void InsertData(){
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();

        database.QueryData("INSERT INTO AccUserInfor" +
                " VALUES(null,'"+email+"','"+pass+"','"+name+"','"+sdt+"',null,null)");
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