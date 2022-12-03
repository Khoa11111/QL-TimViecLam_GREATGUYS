package com.google.ql_timvieclam_greatguys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.Database.Database;

import java.util.ArrayList;

public class ViecLamDaUngTuyen extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    ImageView img;
    ListView listView;
    ArrayList<TinTuyenDung> mlistviewList;
    TuyenDungAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viec_lam_da_nopcv_layout);

        AnhXa();

        setListViewTinTuyenDung();

        BatSuKien();
    }

    private void AnhXa(){
        img = findViewById(R.id.arrback_vieclamdaungtuyen);
        listView = findViewById(R.id.lv_vieclamdaungtuyen);
    }

    private void BatSuKien(){
        onClickBack();
        onClickItemListView();
    }

    // hiển thị dữ liệu tin tuyển dụng lên listview
    private void setListViewTinTuyenDung(){
        mlistviewList = new ArrayList<>();
        HienDanhSachTinTuyenDung();
    }

    // Đọc dữ liệu từ DB và đẩy dữ liệu vào litsview
    private void HienDanhSachTinTuyenDung(){
        Cursor data = database.GetData("Select * from ViecLamDaUngtuyen");
        while (data.moveToNext()){
            String tuyendung = data.getString(2);
            String luong = data.getString(3);
            int hinhanh = data.getInt(4);
            String ten = data.getString(5);
            String diadiem = data.getString(6);
            String tinuutien = data.getString(7);

            mlistviewList.add(new TinTuyenDung(tuyendung,luong,hinhanh,ten,diadiem,tinuutien));
        }
        adapter = new TuyenDungAdapter(this, R.layout.dong_viec_lam, mlistviewList);
        listView.setAdapter(adapter);
    }

    // Sự kiện nhấn icon back
    private void onClickBack(){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViecLamDaUngTuyen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Sự kiện nhấn item trong listview
    private void onClickItemListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CkLogin.getIdTinTuyenDung(ViecLamDaUngTuyen.this,i);
                Intent intent = new Intent(ViecLamDaUngTuyen.this,ChiTietTinTuyenDung.class);
                startActivity(intent);
            }
        });
    }
}
