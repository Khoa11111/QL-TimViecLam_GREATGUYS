package com.google.ql_timvieclam_greatguys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ql_timvieclam_greatguys.Database.Database;

public class SignIn extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    ImageView imageView;
    TextView tvSignup;
    EditText edtEmail, edtPass;
    AppCompatButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        AnhXa();
        BatSuKien();
    }

    private void AnhXa(){
        imageView = findViewById(R.id.arrback_signin);
        tvSignup = findViewById(R.id.tv_signup_signin);
        edtEmail = findViewById(R.id.edt_email_signin);
        edtPass = findViewById(R.id.edt_pass_signin);
        btnSignIn = findViewById(R.id.btn_signin);
    }

    private void BatSuKien(){
        onClickBack();
        onClickTvDontHaveAccount();
        onClickBtnSignIn();
    }

    private void onClickBack(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickTvDontHaveAccount(){
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void onClickBtnSignIn(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty()){
                    checkEmailAndPass();
                }

            }
        });
    }
    private boolean checkEmpty(){
        if (edtEmail.getText().toString().isEmpty()){
            edtEmail.setError("Email kh??ng ???????c ????? tr???ng");
            edtEmail.requestFocus();
            return false;
        }
        if(edtPass.getText().toString().isEmpty()){
            edtPass.setError("M???t kh???u kh??ng ???????c ????? tr???ng");
            edtPass.requestFocus();
            return false;
        }
        return true;
    }
    private void checkEmailAndPass(){
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        Cursor data = database.GetData("SELECT accEmail,accPassword" +
                            " FROM AccUserInfor" +
                            " WHERE accEmail='"+email+"' and accPassword='"+pass+"'");
        if (data.getCount() == 0){
            Toast.makeText(this,"Email ho???c m???t kh???u ch??a ch??nh x??c. M???i b???n ki???m tra l???i",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "????ng nh???p th??nh c??ng", Toast.LENGTH_SHORT).show();
        CkLogin.ckLogin = email;
        Intent intent = new Intent(SignIn.this,MainActivity.class);
        startActivity(intent);
    }

}
