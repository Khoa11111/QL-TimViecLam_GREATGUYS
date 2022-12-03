package com.google.ql_timvieclam_greatguys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.CacViecLam.Adapter;
import com.google.ql_timvieclam_greatguys.CacViecLam.ViecLam;
import com.google.ql_timvieclam_greatguys.Database.Database;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onClickItemViecLamListener{

    Database database = new Database(this,"QLTimViecLam",null,1);
    ImageView imageMenu;
    RecyclerView mList;
    List<ViecLam> appList;
    ListView lv;
    ArrayList<TinTuyenDung> arrayList;
    TuyenDungAdapter adapter;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    LinearLayout btnLogin, btnSignup;
    TextView tvNameUser, tvEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteQuery();

        AnhXa();

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

    private void BatSuKien(){
        onClickMenu();
        onClickItemMenu();
        onClickItemListview();
    }

    // thiết lập layout headerview và sự kiện nhấn nút
    private void setHeaderViewAndListener(){
        if(CkLogin.ckLogin.isEmpty()){
            navigationView.inflateHeaderView(R.layout.layout_nav_header_logout);
            View header = navigationView.getHeaderView(0);
            btnLogin = header.findViewById(R.id.btn_nav_login);
            btnSignup = header.findViewById(R.id.btn_nav_signup);
            onClickLoginAndSignUpInHeaderView();
        }
        else{
            navigationView.inflateHeaderView(R.layout.layout_nav_header_login);
            View header = navigationView.getHeaderView(0);
            tvNameUser = header.findViewById(R.id.txt_nav_name);
            tvEmailUser = header.findViewById(R.id.txt_nav_email);
            setNameAndEmailHeaderView();
        }
    }

    // Đẩy dữ liệu từ DB vào và hiển thị trên Recycleview
    private void setRecycleViewCacNganh(){

        appList = new ArrayList<>();

        appList.add(new ViecLam(R.drawable.ic_baseline_list_24,"Hiện tất cả"));
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

        Adapter adaptor = new Adapter(this,appList,this);
        mList.setAdapter(adaptor);
    }

    // hiển thị dữ liệu tin tuyển dụng lên listview
    private void setListViewTinTuyenDung(){
        arrayList = new ArrayList<>();
        HienDanhSachTinTuyenDung();
    }

    // Đọc dữ liệu từ DB và đẩy dữ liệu vào litsview
    private void HienDanhSachTinTuyenDung(){
        Cursor data = database.GetData("Select * from TinTuyenDung");
        while (data.moveToNext()){
                String tuyendung = data.getString(1);
                String luong = data.getString(2);
                int hinhanh = data.getInt(3);
                String ten = data.getString(4);
                String diadiem = data.getString(5);
                String tinuutien = data.getString(6);

                arrayList.add(new TinTuyenDung(tuyendung,luong,hinhanh,ten,diadiem,tinuutien));
        }
        adapter = new TuyenDungAdapter(this, R.layout.dong_viec_lam, arrayList);
        lv.setAdapter(adapter);
    }


    // Sự kiện bấm vào icon menu
    private void onClickMenu(){
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }

    // Sự kiện bấm nút đăng nhập và đăng ký trong headerview
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

    // hiển thị tên người dùng và email của người dùng lên headerview
    private void setNameAndEmailHeaderView(){
        Cursor data = database.GetData("Select accEmail,accName " +
                "From AccUserInfor " +
                "Where accEmail = '"+CkLogin.ckLogin+"'");
        while (data.moveToNext()){
            String email = data.getString(0);
            String name = data.getString(1);
            tvEmailUser.setText(email);
            tvNameUser.setText(name);
        }
    }

    // Sự kiện bấm vào các item trong menu
    private void onClickItemMenu(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_DangXuat:
                        if (CheckLogin() == true){
                            View header = navigationView.getHeaderView(0);
                            navigationView.removeHeaderView(header);
                            navigationView.inflateHeaderView(R.layout.layout_nav_header_logout);
                            View header2 = navigationView.getHeaderView(0);
                            btnLogin = header2.findViewById(R.id.btn_nav_login);
                            btnSignup = header2.findViewById(R.id.btn_nav_signup);
                            onClickLoginAndSignUpInHeaderView();
                            drawerLayout.closeDrawer(GravityCompat.END);
                            CkLogin.ckLogin = "";
                            break;
                        }
                        Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                        break;
                    //Intent cho item Quản lý Người Dùng
                    case R.id.menu_QuanLyNguoiDung:
                        if (CheckLogin() == true){
                            Intent iQLND = new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(iQLND);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_QuanLyCV:
                        if (CheckLogin() == true){
                            Intent CvIn = new Intent(MainActivity.this, CVActivity.class);
                            startActivity(CvIn);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_ViecLamDaLuu:
                        if (CheckLogin() == true){
                            Intent iVLDL = new Intent(MainActivity.this, ViecLamDaLuu.class);
                            startActivity(iVLDL);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
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

    // Sự kiện bấm vào các item trong recycleview các ngành
    @Override
    public void onClickItemViecLam(ViecLam viecLam) {
        switch (viecLam.getName()){
            case "Hiện tất cả":
                arrayList.clear();
                HienDanhSachTinTuyenDung();
                break;
            case "Bảo vệ":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Bảo vệ");
                break;
            case "Công nhân":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Công nhân");
                break;
            case "Nhân viên bán hàng":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Nhân viên bán hàng");
                break;
            case "Công nhân may":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Công nhân may");
                break;
            case "Tài xế ô tô":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Tài xế ô tô");
                break;
            case "Chăm sóc khách hàng":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Chăm sóc khách hàng");
                break;
            case "Nhân viên phục vụ":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Nhân viên phục vụ");
                break;
        }
    }

    // thiết lập dữ liệu đổ vào listview khi bấm từng nút trong recycleview ngành
    private void setDataListviewTinTuyenDung_onClickItemRecycleview(String dk){
        Cursor data = database.GetData("SELECT * From TinTuyenDung where Nganh = '"+dk+"'");
        while (data.moveToNext()){
            String tuyendung = data.getString(1);
            String luong = data.getString(2);
            int hinhanh = data.getInt(3);
            String ten = data.getString(4);
            String diadiem = data.getString(5);
            String tinuutien = data.getString(6);

            arrayList.add(new TinTuyenDung(tuyendung,luong,hinhanh,ten,diadiem,tinuutien));
        }
        adapter = new TuyenDungAdapter(this, R.layout.dong_viec_lam, arrayList);
        lv.setAdapter(adapter);
    }

    // Sự kiện nhấn các item trong listview tin tuyển dụng
    private void onClickItemListview(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toList(i);
            }
        });
    }

    // Thêm dữ liệu vào bảng ViecLamDaLuu
    private void toList(int i){
        int count = i +1;
        Cursor data = database.GetData("select * from TinTuyenDung limit "+count+"");
        while (data.moveToNext()){
            if (data.isLast()){
                int id = data.getInt(0);
                String tuyendung = data.getString(1);
                String luong = data.getString(2);
                int hinhanh = data.getInt(3);
                String ten = data.getString(4);
                String diadiem = data.getString(5);
                String tinuutien = data.getString(6);

                Cursor data2 = database.GetData("Select id from ViecLamDaLuu where idTTD='"+id+"'");
                if (data2.getCount() == 0){
                    database.QueryData("INSERT INTO ViecLamDaLuu " +
                            "VALUES(null,'"+id+"','"+tuyendung+"','"+luong+"',"+hinhanh+",'"+ten+"','"+diadiem+"','"+tinuutien+"')");
                }
            }
        }
    }

    // Câu lệnh SQLite
    private void SQLiteQuery(){
        database.QueryData("CREATE TABLE IF NOT EXISTS AccUserInfor(" +
                "id integer primary key autoincrement," +
                "accEmail varchar(50) not null," +
                "accPassword varchar(50) not null," +
                "accName nvarchar(50) not null," +
                "accSDT varchar(15) not null," +
                "accGT nvarchar(10)," +
                "accThanhPho nvarchar(30))");

        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuyenDung(" +
                "id integer primary key autoincrement," +
                "TuyenDung nvarchar(50) not null," +
                "Luong nvarchar(50) null," +
                "Hinhanh int null," +
                "Ten nvarchar(50) null," +
                "DiaDiem nvarchar(50) null," +
                "TinUuTien nvarchar(50) null," +
                "Nganh nvarchar(50) not null)");

        Cursor data = database.GetData("Select * from TinTuyenDung");
        if (data.getCount() == 0){
            database.QueryData("INSERT INTO TinTuyenDung" +
                    " VALUES(null,'Nhân viên chăm sóc khách hàng tại Quận 3','6.000.000 - 50.000.000đ/ tháng',"+R.drawable.cham_soc_khach_hang+",'Công ty A','Đà Nẵng','4 ngày trước','Chăm sóc khách hàng')," +
                    "(null,'Nhân viên bán hàng tại siêu thị Go tại Đà Nẵng','5.000.000 - 10.000.000đ/ tháng',"+R.drawable.nv_ban_hang+",'B Group','Đằ nẵng','1 ngày trước','Nhân viên bán hàng')," +
                    "(null,'Tài xế tại khách sạn Hoàng Anh Gia Lai','11.000.000 - 15.000.000đ/ tháng',"+R.drawable.tai_xe_o_to+",'Nguyễn C','Đà nẵng','7 ngày trước','Tài xế ô tô')," +
                    "(null,'Công nhân may vá cho danh nghiệp','40.000.000 - 50.000.000đ/ tháng',"+R.drawable.cong_nhan_may+",'Công ty X','Hồ Chí Minh','4 ngày trước','Công nhân may')," +
                    "(null,'Bảo vệ tại công ty Sun','4.000.000 - 7.000.000/ tháng',"+R.drawable.bao_ve+",'Công ty Sun','Hà Nội','1 ngày trước','Bảo vệ')," +
                    "(null,'Công nhân nhà máy thủy điện Hòa Bình','20.000.00 - 50.000.000/ tháng',"+R.drawable.cong_nhan+",'Nhà máy thủy điện HB','Tp.Hòa Bình','2 ngày trước','Công nhân')," +
                    "(null,'Nhân viên phục vụ tại nhà hàng Ngon','7.000.000 - 12.000.000/ tháng',"+R.drawable.nv_phuc_vu+",'Nhà hàng Ngon','Cà Mau','7 ngày trước','Nhân viên phục vụ')");
        }

        database.QueryData("CREATE TABLE IF NOT EXISTS ViecLamDaLuu(" +
                "id integer primary key autoincrement," +
                "idTTD integer not null," +
                "TuyenDung nvarchar(50) not null," +
                "Luong nvarchar(50) null," +
                "Hinhanh int null," +
                "Ten nvarchar(50) null," +
                "DiaDiem nvarchar(50) null," +
                "TinUuTien nvarchar(50) null)");

        //database.QueryData("delete from TinTuyenDung");
        //database.QueryData("drop table ViecLamDaLuu");
    }
    // select * from TinTuyenDung limit 3
}