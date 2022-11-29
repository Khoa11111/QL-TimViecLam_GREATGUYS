package com.google.ql_timvieclam_greatguys.Database;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.R;
import com.google.ql_timvieclam_greatguys.Database.Database;

public class DatabaseQuery extends AppCompatActivity {

    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_run);

        // Tạo database QLTimViecLam
        database = new Database(this,"QLTimViecLam",null,1);

        // Tạo bảng AccUser_Infor
        database.QueryData("CREATE TABLE IF NOT EXISTS AccUserInfor(" +
                "id integer primary key autoincrement," +
                "accEmail varchar(50) not null," +
                "accPassword varchar(50) not null," +
                "accName nvarchar(50) not null," +
                "accSDT varchar(15) not null," +
                "accGT nvarchar(10)," +
                "accThanhPho nvarchar(30))");

        database.QueryData("INSERT INTO AccUserInfor" +
                " VALUES(NULL,'khoa@gmail.com','maths2002','Khoa','0868833221',null,null)");
    }
}
