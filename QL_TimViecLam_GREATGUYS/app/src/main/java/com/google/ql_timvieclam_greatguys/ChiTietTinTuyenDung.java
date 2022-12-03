package com.google.ql_timvieclam_greatguys;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ql_timvieclam_greatguys.Database.Database;

public class ChiTietTinTuyenDung extends AppCompatActivity {
    Database database = new Database(this,"QLTimViecLam",null,1);
    ImageView img, imgBack;
    TextView tvTitle, tvNguoiDang, tvHanNop, tvLuong, tvSLT, tvHinhThuc, tvCapbac, tvGT, tvKinhNghiem, tvDiaDiem, tvNopCV, tvLuuTin;
    LinearLayout lnNopCV, lnLuuTin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_tintuyendung);

        AnhXa();
        setDataChitiettuyenDung();
        BatSuKien();
    }

    private void AnhXa(){
        imgBack = findViewById(R.id.arrback_chitietTin);
        img = findViewById(R.id.img_chitiet);
        tvTitle = findViewById(R.id.tv_tenTinTD_chitiet);
        tvNguoiDang = findViewById(R.id.tv_tenNguoidang_chitiet);
        tvHanNop = findViewById(R.id.tv_hannop_chitiet);
        tvLuong = findViewById(R.id.tv_mucluong_chitiet);
        tvSLT = findViewById(R.id.tv_soluongtuyen_chitiet);
        tvHinhThuc = findViewById(R.id.tv_htlv_chitiet);
        tvCapbac = findViewById(R.id.tv_capbac_chitiet);
        tvGT = findViewById(R.id.tv_GT_chitiet);
        tvKinhNghiem = findViewById(R.id.tv_kinhnghiem_chitiet);
        tvDiaDiem = findViewById(R.id.tv_diadiem_chitiet);
        tvNopCV = findViewById(R.id.tv_ln_nopCV_chitiet);
        tvLuuTin = findViewById(R.id.tv_ln_luutin_chitiet);
        lnNopCV = findViewById(R.id.ln_nopCV_chitiet);
        lnLuuTin = findViewById(R.id.ln_luutin_chitiet);
    }

    private void BatSuKien(){
        onClickLuuTin();
        onClickNopCV();
        onClickBack();
    }

    // Đổ dữ liệu vào trang chi tiết tin tuyển dụng
    private void setDataChitiettuyenDung(){
        Cursor data = database.GetData("Select * from ChiTietTinTuyenDung where idTTD = '"+ CkLogin.id +"'");
        data.moveToFirst();
        int hinhanh = data.getInt(2);
        String title = TestEmpty(data.getString(3));
        String tenNguoiDang = TestEmpty(data.getString(4));
        String hanNop = TestEmpty(data.getString(5));
        String luong = TestEmpty(data.getString(6));
        String sLT = TestEmpty(data.getString(7));
        String hinhthuc = TestEmpty(data.getString(8));
        String capBac = TestEmpty(data.getString(9));
        String gioiTinh = TestEmpty(data.getString(10));
        String kinhNghiem = TestEmpty(data.getString(11));
        String diaDiem = TestEmpty(data.getString(12));

        img.setImageResource(hinhanh);
        tvTitle.setText(title);
        tvNguoiDang.setText(tenNguoiDang);
        tvHanNop.setText(hanNop);
        tvLuong.setText(luong);
        tvSLT.setText(sLT);
        tvHinhThuc.setText(hinhthuc);
        tvCapbac.setText(capBac);
        tvGT.setText(gioiTinh);
        tvKinhNghiem.setText(kinhNghiem);
        tvDiaDiem.setText(diaDiem);
    }

    // Sự kiện nhấn nút back
    private void onClickBack(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietTinTuyenDung.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Sự kiện nhấn nút nộp CV
    private void onClickNopCV(){
        lnNopCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin() == true){
                    Cursor data1 = database.GetData("Select * from ViecLamDaUngtuyen where idTTD="+CkLogin.id+"");
                    if (data1.getCount() == 0){
                        Cursor data = database.GetData("Select * from TinTuyenDung where id="+CkLogin.id+"");
                        data.moveToFirst();
                        int id = data.getInt(0);
                        String title = data.getString(1);
                        String luong = data.getString(2);
                        int hinhanh = data.getInt(3);
                        String tenNguoiDang = data.getString(4);
                        String diadiem = data.getString(5);
                        String tinuutien = data.getString(6);

                        database.QueryData("Insert into ViecLamDaUngtuyen" +
                                " values(null,"+id+",'"+title+"','"+luong+"',"+hinhanh+",'"+tenNguoiDang+"','"+diadiem+"','"+tinuutien+"')");

                        Toast.makeText(ChiTietTinTuyenDung.this, "Đã Nộp", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(ChiTietTinTuyenDung.this, "Bạn không thể nộp 2 lần", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(ChiTietTinTuyenDung.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Sự kiện nhấn nút Lưu tin
    private void onClickLuuTin(){
        lnLuuTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckLogin() == true){
                    Cursor data1 = database.GetData("Select * from ViecLamDaLuu where idTTD="+CkLogin.id+"");
                    if (data1.getCount() == 0){
                        Cursor data = database.GetData("Select * from TinTuyenDung where id="+CkLogin.id+"");
                        data.moveToFirst();
                        int id = data.getInt(0);
                        String title = data.getString(1);
                        String luong = data.getString(2);
                        int hinhanh = data.getInt(3);
                        String tenNguoiDang = data.getString(4);
                        String diadiem = data.getString(5);
                        String tinuutien = data.getString(6);

                        database.QueryData("Insert into ViecLamDaLuu" +
                                " values(null,"+id+",'"+title+"','"+luong+"',"+hinhanh+",'"+tenNguoiDang+"','"+diadiem+"','"+tinuutien+"')");

                        Toast.makeText(ChiTietTinTuyenDung.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                    } else  {
                        Toast.makeText(ChiTietTinTuyenDung.this, "Bạn không thể lưu 2 lần", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(ChiTietTinTuyenDung.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Kiểm tra người dùng đã đăng nhập chưa
    private Boolean CheckLogin(){
        if (CkLogin.ckLogin.isEmpty()){
            return false;
        }
        return true;
    }

    private String TestEmpty(String text){
        if(text == null)
            return ("");
        if(text.isEmpty())
            return ("");
        return text;
    }
}
