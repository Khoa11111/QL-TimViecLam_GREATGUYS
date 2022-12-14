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
                    case R.id.menu_ViecLamUngTuyen:
                        if (CheckLogin() == true){
                            Intent iVLDUT = new Intent(MainActivity.this, ViecLamDaUngTuyen.class);
                            startActivity(iVLDUT);
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
                CkLogin.idTTD = 0;
                break;
            case "Bảo vệ":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Bảo vệ");
                CkLogin.idTTD = 5;
                break;
            case "Công nhân":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Công nhân");
                CkLogin.idTTD = 6;
                break;
            case "Nhân viên bán hàng":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Nhân viên bán hàng");
                CkLogin.idTTD = 2;
                break;
            case "Công nhân may":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Công nhân may");
                CkLogin.idTTD = 4;
                break;
            case "Tài xế ô tô":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Tài xế ô tô");
                CkLogin.idTTD = 3;
                break;
            case "Chăm sóc khách hàng":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Chăm sóc khách hàng");
                CkLogin.idTTD = 1;
                break;
            case "Nhân viên phục vụ":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Nhân viên phục vụ");
                CkLogin.idTTD = 7;
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
                if(CkLogin.idTTD != 0)
                {
                    if(CkLogin.getIdTinTuyenDung(MainActivity.this, i, CkLogin.idTTD)){
                        Intent intent = new Intent(MainActivity.this, ChiTietTinTuyenDung.class);
                        startActivity(intent);
                    }
                }else
                if(CkLogin.getIdTinTuyenDung(MainActivity.this,i)){
                    Intent intent = new Intent(MainActivity.this, ChiTietTinTuyenDung.class);
                    startActivity(intent);
                }
            }
        });
    }


    // Câu lệnh SQLite
    private void SQLiteQuery(){
        database.QueryData("CREATE TABLE IF NOT EXISTS AccUserInfor(" +
                "id integer primary key autoincrement," +
                "accEmail varchar(50) not null," +
                "accPassword varchar(50) not null," +
                "accName nvarchar(50) not null," +
                "accSDT varchar(15) not null," +
                "accDoB nvarchar(10)," +
                "accThanhPho nvarchar(30))");

        database.QueryData("CREATE TABLE IF NOT EXISTS HoSoCV(" +
                "id integer primary key autoincrement," +
                "accName nvarchar(50)," +
                "accGT nvarchar(10)," +
                "accNgaySinh varchar(50)," +
                "accCMND varchar(50)," +
                "accSDT varchar(15)," +
                "accEmail varchar(50)," +
                "accDiaChi nvarchar(30)," +
                "accBangCap nvarchar(10)," +
                "accBangTA nvarchar(10)," +
                "accSoTruong nvarchar(10))");

        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuyenDung(" +
                "id integer primary key autoincrement," +
                "TuyenDung nvarchar(50) not null," +
                "Luong nvarchar(50) null," +
                "Hinhanh int null," +
                "Ten nvarchar(50) null," +
                "DiaDiem nvarchar(50) null," +
                "TinUuTien nvarchar(50) null," +
                "Nganh nvarchar(50) not null)");

        database.QueryData("CREATE TABLE IF NOT EXISTS ViecLamDaLuu(" +
                "id integer primary key autoincrement," +
                "idTTD integer not null," +
                "TuyenDung nvarchar(50) not null," +
                "Luong nvarchar(50) null," +
                "Hinhanh int null," +
                "Ten nvarchar(50) null," +
                "DiaDiem nvarchar(50) null," +
                "TinUuTien nvarchar(50) null)");

        database.QueryData("CREATE TABLE IF NOT EXISTS ViecLamDaUngtuyen(" +
                "id integer primary key autoincrement," +
                "idTTD integer not null," +
                "TuyenDung nvarchar(50) not null," +
                "Luong nvarchar(50) null," +
                "Hinhanh int null," +
                "Ten nvarchar(50) null," +
                "DiaDiem nvarchar(50) null," +
                "TinUuTien nvarchar(50) null)");

        database.QueryData("CREATE TABLE IF NOT EXISTS ChiTietTinTuyenDung(" +
                "id integer primary key autoincrement," +
                "idTTD integer not null," +
                "hinhAnh integer not null," +
                "title nvarchar(50) not null," +
                "tenNguoiDang nvarchar(50) not null," +
                "hanNopCV nvarchar(50) not null," +
                "luong nvarchar(50) not null," +
                "soLTuyen nvarchar(50) null," +
                "hinhThuc nvarchar(50) null," +
                "capBac nvarchar(50) null," +
                "gioiTinh nvarchar(20) null," +
                "kinhNghiem nvarchar(50) null," +
                "diaDiem nvarchar(50) not null)");

        Cursor data = database.GetData("Select * from TinTuyenDung");
        if (data.getCount() == 0){
            database.QueryData("INSERT INTO TinTuyenDung" +
                    " VALUES(null,'Nhân viên chăm sóc khách hàng tại Quận 3','6 - 20 triệu vnd/tháng',"+R.drawable.cham_soc_khach_hang+",'Công ty A','Đà Nẵng','4 ngày trước','Chăm sóc khách hàng')," +
                    "(null,'Nhân viên bán hàng tại siêu thị Go tại Đà Nẵng','5 - 10 triệu vnd/tháng',"+R.drawable.nv_ban_hang+",'B Group','Đằ nẵng','1 ngày trước','Nhân viên bán hàng')," +
                    "(null,'Tài xế tại khách sạn Hoàng Anh Gia Lai','11 - 15 triệu vnd/tháng',"+R.drawable.tai_xe_o_to+",'Nguyễn C','Đà nẵng','7 ngày trước','Tài xế ô tô')," +
                    "(null,'Công nhân may vá cho danh nghiệp','40 - 50 triệu vnd/tháng',"+R.drawable.cong_nhan_may+",'Công ty X','Hồ Chí Minh','4 ngày trước','Công nhân may')," +
                    "(null,'Bảo vệ tại công ty Sun','4 - 7 triệu vnd/tháng',"+R.drawable.bao_ve+",'Công ty Sun','Hà Nội','1 ngày trước','Bảo vệ')," +
                    "(null,'Công nhân nhà máy thủy điện Hòa Bình','20 - 50 triệu vnd/tháng',"+R.drawable.cong_nhan+",'Nhà máy thủy điện HB','Tp.Hòa Bình','2 ngày trước','Công nhân')," +
                    "(null,'Nhân viên phục vụ tại nhà hàng Ngon','7 - 12 triệu vnd/tháng',"+R.drawable.nv_phuc_vu+",'Nhà hàng Ngon','Cà Mau','7 ngày trước','Nhân viên phục vụ')");
        }

        Cursor data1 = database.GetData("Select * from ChiTietTinTuyenDung");
        if (data1.getCount() == 0){
            database.QueryData("INSERT INTO ChiTietTinTuyenDung" +
                    " VALUES(null,1,"+R.drawable.cham_soc_khach_hang+",'Nhân viên chăm sóc khách hàng tại Quận 3','Công ty A','27/2/2022','6 - 20 triệu vnd/tháng','20 người','toàn thời gian',null,'Không yêu cầu','Không yêu cầu','Đà Nẵng')," +
                    "(null,2,"+R.drawable.nv_ban_hang+",'Nhân viên bán hàng tại siêu thị Go tại Đà Nẵng','B Group','30/7/2022','5 - 10 triệu vnd/tháng','25 người','toàn thời gian',null,'Không yêu cầu','Không yêu cầu','Đằ nẵng')," +
                    "(null,3,"+R.drawable.tai_xe_o_to+",'Tài xế tại khách sạn Hoàng Anh Gia Lai','Nguyễn C','30/7/2022','11 - 15 triệu vnd/tháng','10 người','bán thời gian',null,'Không yêu cầu','Không yêu cầu','Đằ nẵng')," +
                    "(null,4,"+R.drawable.cong_nhan_may+",'Công nhân may vá cho danh nghiệp','Công ty X','30/7/2022','40 - 50 triệu vnd/tháng','100 người','toàn thời gian',null,'Không yêu cầu','Không yêu cầu','Hồ Chí Minh')," +
                    "(null,5,"+R.drawable.bao_ve+",'Bảo vệ tại công ty Sun','Công ty Sun','30/7/2022','4 - 7 triệu vnd/tháng','5 người','bán thời gian',null,'Không yêu cầu','Không yêu cầu','Hà Nội')," +
                    "(null,6,"+R.drawable.cong_nhan+",'Công nhân nhà máy thủy điện Hòa Bình','Nhà máy thủy điện HB','30/7/2022','20 - 50 triệu vnd/tháng','75 người','toàn thời gian',null,'Không yêu cầu','Không yêu cầu','Tp.Hòa Bình')," +
                    "(null,7,"+R.drawable.nv_phuc_vu+",'Nhân viên phục vụ tại nhà hàng Ngon','Nhà hàng Ngon','30/7/2022','7 - 12 triệu vnd/tháng','12 người','toàn thời gian',null,'Không yêu cầu','Không yêu cầu','Cà Mau')");
        };

        //database.QueryData("delete from TinTuyenDung");
        //database.QueryData("drop table ViecLamDaLuu");
    }
    // select * from TinTuyenDung limit 3
}