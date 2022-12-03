package com.google.ql_timvieclam_greatguys;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.Database.Database;

import java.util.ArrayList;

public class ViecLamDaLuu extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    ImageView imgBack;
    ListView listView;
    ArrayList<TinTuyenDung> mlistviewList;
    TuyenDungAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luu_viec_lam_layout);

        AnhXa();

        setListViewTinTuyenDung();

        BatSuKien();
    }

    private void AnhXa(){
        imgBack = findViewById(R.id.arrback_vieclamdaluu);
        listView = findViewById(R.id.lv_vieclamdaluu);
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
        Cursor data = database.GetData("Select * from ViecLamDaLuu");
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
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViecLamDaLuu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Sự kiện nhấn item trong listview
    private void onClickItemListView(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int ii, long l) {
                Delete(ii);
                return false;
            }
        });
    }

    // Xóa item chọn
    private void Delete(int ii){
        int count = ii + 1;
        Cursor data = database.GetData("select * from ViecLamDaLuu limit "+count+"");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViecLamDaLuu.this);
        //Thiết lập tiêu đề cho alert dialog
        alertDialogBuilder.setTitle("Xóa tin đã lưu");
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Bạn có chắc muốn xóa tin này?");
        alertDialogBuilder.setCancelable(false);
        //Button yes
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                while (data.moveToNext()){
                    if (data.isLast()){
                        int id = data.getInt(0);
                        database.QueryData("delete from ViecLamDaLuu where id='"+id+"'");
                    }
                }
                Toast.makeText(ViecLamDaLuu.this,"Đã Xóa",Toast.LENGTH_SHORT).show();
                mlistviewList.clear();
                HienDanhSachTinTuyenDung();
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
}
