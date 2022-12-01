package com.google.ql_timvieclam_greatguys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.CacViecLam.Adapter;
import com.google.ql_timvieclam_greatguys.CacViecLam.ViecLam;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageMenu;
    RecyclerView mList;
    List<ViecLam> appList;
    ListView lv;
    ArrayList<TinTuyenDung> arrayList;
    TuyenDungAdapter adapter;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    LinearLayout btnLogin, btnSignup;
    String ckLogin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ckLogin=getBundleData();
        setHeaderViewAndListener();
        setRecycleViewCacNganh();
        setListViewTinTuyenDung();
        BatSuKien();
    }

    private void AnhXa() {
        lv = findViewById(R.id.list_view);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav);
        imageMenu = findViewById(R.id.image_Menu);
        mList = findViewById(R.id.list);
    }

    private void setHeaderViewAndListener(){
        navigationView.inflateHeaderView(R.layout.layout_nav_header);

        View header = navigationView.getHeaderView(0);
        btnLogin = header.findViewById(R.id.btn_nav_login);
        btnSignup = header.findViewById(R.id.btn_nav_signup);
    }

    private void setRecycleViewCacNganh(){

        appList = new ArrayList<>();

        appList.add(new ViecLam(R.drawable.bao_ve,"Bảo vệ"));
        appList.add(new ViecLam(R.drawable.cong_nhan,"Công nhân"));
        appList.add(new ViecLam(R.drawable.nv_ban_hang,"Nhân viên bán hàng"));
        appList.add(new ViecLam(R.drawable.cong_nhan_may,"Công nhân may"));
        appList.add(new ViecLam(R.drawable.tai_xe_o_to,"Tài xế ô tô"));
        appList.add(new ViecLam(R.drawable.cham_soc_khach_hang,"Chăm sóc khách hàng"));
        appList.add(new ViecLam(R.drawable.nv_phuc_vu,"Nhân viên phục vụ"));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList.setLayoutManager(manager);

        Adapter adaptor = new Adapter(this,appList);
        mList.setAdapter(adaptor);
    }

    private void setListViewTinTuyenDung(){
        arrayList = new ArrayList<>();
        arrayList.add(new TinTuyenDung("Công ty A tuyển dụng", "6.000.000 - 50.000.000đ/ tháng", R.drawable.cham_soc_khach_hang, "Công ty A", "Đà Nẵng","4 ngày trước"));
        arrayList.add(new TinTuyenDung("Nhà hàng B tuyển dụng", "5.000.000 - 10.000.000đ/ tháng", R.drawable.nv_ban_hang, "B Group", "Huế","1 ngày trước"));
        arrayList.add(new TinTuyenDung("Cần tuyển tài xế", "11.000.000 - 15.000.000đ/ tháng", R.drawable.tai_xe_o_to, "Nguyễn C", "Hà Nội","7 ngày trước"));
        arrayList.add(new TinTuyenDung("Công ty X tuyển dụng", "40.000.000 - 50.000.000đ/ tháng", R.drawable.cong_nhan_may, "Công ty X", "Hồ Chí Minh","4 ngày trước"));

        adapter = new TuyenDungAdapter(this, R.layout.dong_viec_lam, arrayList);
        lv.setAdapter(adapter);
    }

    private void BatSuKien(){
        onClickMenu();
        onClickItemMenu();
        onClickLoginAndSignUpInHeaderView();
    }


    private void onClickMenu(){
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }

    private void onClickLoginAndSignUpInHeaderView(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void onClickItemMenu(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_DangXuat:
                        if (CheckLogin() == true){
                            Intent iDangxuat = new Intent(MainActivity.this, SignIn.class);
                            startActivity(iDangxuat);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                        break;
                    //Intent cho item Quản lý Người Dùng
                    case R.id.menu_QuanLyNguoiDung:
                        if (CheckLogin() == true){
                            Intent iQLND = new Intent(MainActivity.this, ProfileActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email",ckLogin);
                            iQLND.putExtra("data",bundle);
                            startActivity(iQLND);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private Boolean CheckLogin(){
        if (ckLogin.isEmpty()){
            return false;
        }
        return true;
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