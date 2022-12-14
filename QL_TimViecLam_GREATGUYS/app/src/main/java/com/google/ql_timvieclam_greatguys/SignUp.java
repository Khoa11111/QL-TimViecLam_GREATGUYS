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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

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
                    Toast.makeText(SignUp.this,"B???n c???n ??i???n ?????y ????? th??ng tin",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtPass.getText().toString().equals(edtComfirmPass.getText().toString())){
                    checkEmailSignup();
                    return;
                }
                Toast.makeText(SignUp.this,"M???t kh???u x??c nh???n ch??a ch??nh x??c",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEmailSignup(){
        Cursor data = database.GetData("SELECT accEmail FROM AccUserInfor");
        if(data.getCount() == 0){
            InsertData();
            Toast.makeText(this, "????ng K?? th??nh c??ng", Toast.LENGTH_SHORT).show();
            CkLogin.ckLogin = edtEmail.getText().toString().trim();
            Intent intent = new Intent(SignUp.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            while(data.moveToNext()){
                String checkEmail = data.getString(0);
                if(checkEmail.equals(edtEmail.getText().toString().trim())){
                    Toast.makeText(SignUp.this,"T??i kho???n ???? t???n t???i",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            InsertData();
            CkLogin.ckLogin = edtEmail.getText().toString().trim();
            Toast.makeText(this, "????ng K?? th??nh c??ng", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void InsertData(){
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        try{

            database.QueryData("INSERT INTO AccUserInfor" +
                    " VALUES(null,'"+email+"','"+pass+"','"+name+"','"+sdt+"',null,null)");

            database.QueryData("INSERT INTO HoSoCV" +
                    " VALUES(null,'"+name+"',null,null,null,'"+sdt+"','"+email+"',null,null,null,null)");

        } catch (Exception e) {
            Toast.makeText(this, "X???y ra l???i khi Insert into", Toast.LENGTH_SHORT).show();
        }

    }

}