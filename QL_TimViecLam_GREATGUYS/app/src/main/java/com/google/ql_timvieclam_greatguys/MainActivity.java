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

    // thi???t l???p layout headerview v?? s??? ki???n nh???n n??t
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

    // ?????y d??? li???u t??? DB v??o v?? hi???n th??? tr??n Recycleview
    private void setRecycleViewCacNganh(){

        appList = new ArrayList<>();

        appList.add(new ViecLam(R.drawable.ic_baseline_list_24,"Hi???n t???t c???"));
        appList.add(new ViecLam(R.drawable.bao_ve,"B???o v???"));
        appList.add(new ViecLam(R.drawable.cong_nhan,"C??ng nh??n"));
        appList.add(new ViecLam(R.drawable.nv_ban_hang,"Nh??n vi??n b??n h??ng"));
        appList.add(new ViecLam(R.drawable.cong_nhan_may,"C??ng nh??n may"));
        appList.add(new ViecLam(R.drawable.tai_xe_o_to,"T??i x??? ?? t??"));
        appList.add(new ViecLam(R.drawable.cham_soc_khach_hang,"Ch??m s??c kh??ch h??ng"));
        appList.add(new ViecLam(R.drawable.nv_phuc_vu,"Nh??n vi??n ph???c v???"));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList.setLayoutManager(manager);

        Adapter adaptor = new Adapter(this,appList,this);
        mList.setAdapter(adaptor);
    }

    // hi???n th??? d??? li???u tin tuy???n d???ng l??n listview
    private void setListViewTinTuyenDung(){
        arrayList = new ArrayList<>();
        HienDanhSachTinTuyenDung();
    }

    // ?????c d??? li???u t??? DB v?? ?????y d??? li???u v??o litsview
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


    // S??? ki???n b???m v??o icon menu
    private void onClickMenu(){
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }

    // S??? ki???n b???m n??t ????ng nh???p v?? ????ng k?? trong headerview
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

    // hi???n th??? t??n ng?????i d??ng v?? email c???a ng?????i d??ng l??n headerview
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

    // S??? ki???n b???m v??o c??c item trong menu
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
                        Toast.makeText(MainActivity.this,"B???n ch??a ????ng nh???p",Toast.LENGTH_SHORT).show();
                        break;
                    //Intent cho item Qu???n l?? Ng?????i D??ng
                    case R.id.menu_QuanLyNguoiDung:
                        if (CheckLogin() == true){
                            Intent iQLND = new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(iQLND);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"B???n ch??a ????ng nh???p",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_QuanLyCV:
                        if (CheckLogin() == true){
                            Intent CvIn = new Intent(MainActivity.this, CVActivity.class);
                            startActivity(CvIn);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"B???n ch??a ????ng nh???p",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_ViecLamDaLuu:
                        if (CheckLogin() == true){
                            Intent iVLDL = new Intent(MainActivity.this, ViecLamDaLuu.class);
                            startActivity(iVLDL);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"B???n ch??a ????ng nh???p",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_ViecLamUngTuyen:
                        if (CheckLogin() == true){
                            Intent iVLDUT = new Intent(MainActivity.this, ViecLamDaUngTuyen.class);
                            startActivity(iVLDUT);
                            break;
                        }
                        Toast.makeText(MainActivity.this,"B???n ch??a ????ng nh???p",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    // Ki???m tra ng?????i d??ng ???? ????ng nh???p ch??a
    private Boolean CheckLogin(){
        if (CkLogin.ckLogin.isEmpty()){
            return false;
        }
        return true;
    }

    // S??? ki???n b???m v??o c??c item trong recycleview c??c ng??nh
    @Override
    public void onClickItemViecLam(ViecLam viecLam) {
        switch (viecLam.getName()){
            case "Hi???n t???t c???":
                arrayList.clear();
                HienDanhSachTinTuyenDung();
                CkLogin.idTTD = 0;
                break;
            case "B???o v???":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("B???o v???");
                CkLogin.idTTD = 5;
                break;
            case "C??ng nh??n":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("C??ng nh??n");
                CkLogin.idTTD = 6;
                break;
            case "Nh??n vi??n b??n h??ng":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Nh??n vi??n b??n h??ng");
                CkLogin.idTTD = 2;
                break;
            case "C??ng nh??n may":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("C??ng nh??n may");
                CkLogin.idTTD = 4;
                break;
            case "T??i x??? ?? t??":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("T??i x??? ?? t??");
                CkLogin.idTTD = 3;
                break;
            case "Ch??m s??c kh??ch h??ng":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Ch??m s??c kh??ch h??ng");
                CkLogin.idTTD = 1;
                break;
            case "Nh??n vi??n ph???c v???":
                arrayList.clear();
                setDataListviewTinTuyenDung_onClickItemRecycleview("Nh??n vi??n ph???c v???");
                CkLogin.idTTD = 7;
                break;
        }
    }

    // thi???t l???p d??? li???u ????? v??o listview khi b???m t???ng n??t trong recycleview ng??nh
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

    // S??? ki???n nh???n c??c item trong listview tin tuy???n d???ng
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


    // C??u l???nh SQLite
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
                    " VALUES(null,'Nh??n vi??n ch??m s??c kh??ch h??ng t???i Qu???n 3','6 - 20 tri???u vnd/th??ng',"+R.drawable.cham_soc_khach_hang+",'C??ng ty A','???? N???ng','4 ng??y tr?????c','Ch??m s??c kh??ch h??ng')," +
                    "(null,'Nh??n vi??n b??n h??ng t???i si??u th??? Go t???i ???? N???ng','5 - 10 tri???u vnd/th??ng',"+R.drawable.nv_ban_hang+",'B Group','????? n???ng','1 ng??y tr?????c','Nh??n vi??n b??n h??ng')," +
                    "(null,'T??i x??? t???i kh??ch s???n Ho??ng Anh Gia Lai','11 - 15 tri???u vnd/th??ng',"+R.drawable.tai_xe_o_to+",'Nguy???n C','???? n???ng','7 ng??y tr?????c','T??i x??? ?? t??')," +
                    "(null,'C??ng nh??n may v?? cho danh nghi???p','40 - 50 tri???u vnd/th??ng',"+R.drawable.cong_nhan_may+",'C??ng ty X','H??? Ch?? Minh','4 ng??y tr?????c','C??ng nh??n may')," +
                    "(null,'B???o v??? t???i c??ng ty Sun','4 - 7 tri???u vnd/th??ng',"+R.drawable.bao_ve+",'C??ng ty Sun','H?? N???i','1 ng??y tr?????c','B???o v???')," +
                    "(null,'C??ng nh??n nh?? m??y th???y ??i???n H??a B??nh','20 - 50 tri???u vnd/th??ng',"+R.drawable.cong_nhan+",'Nh?? m??y th???y ??i???n HB','Tp.H??a B??nh','2 ng??y tr?????c','C??ng nh??n')," +
                    "(null,'Nh??n vi??n ph???c v??? t???i nh?? h??ng Ngon','7 - 12 tri???u vnd/th??ng',"+R.drawable.nv_phuc_vu+",'Nh?? h??ng Ngon','C?? Mau','7 ng??y tr?????c','Nh??n vi??n ph???c v???')");
        }

        Cursor data1 = database.GetData("Select * from ChiTietTinTuyenDung");
        if (data1.getCount() == 0){
            database.QueryData("INSERT INTO ChiTietTinTuyenDung" +
                    " VALUES(null,1,"+R.drawable.cham_soc_khach_hang+",'Nh??n vi??n ch??m s??c kh??ch h??ng t???i Qu???n 3','C??ng ty A','27/2/2022','6 - 20 tri???u vnd/th??ng','20 ng?????i','to??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','???? N???ng')," +
                    "(null,2,"+R.drawable.nv_ban_hang+",'Nh??n vi??n b??n h??ng t???i si??u th??? Go t???i ???? N???ng','B Group','30/7/2022','5 - 10 tri???u vnd/th??ng','25 ng?????i','to??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','????? n???ng')," +
                    "(null,3,"+R.drawable.tai_xe_o_to+",'T??i x??? t???i kh??ch s???n Ho??ng Anh Gia Lai','Nguy???n C','30/7/2022','11 - 15 tri???u vnd/th??ng','10 ng?????i','b??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','????? n???ng')," +
                    "(null,4,"+R.drawable.cong_nhan_may+",'C??ng nh??n may v?? cho danh nghi???p','C??ng ty X','30/7/2022','40 - 50 tri???u vnd/th??ng','100 ng?????i','to??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','H??? Ch?? Minh')," +
                    "(null,5,"+R.drawable.bao_ve+",'B???o v??? t???i c??ng ty Sun','C??ng ty Sun','30/7/2022','4 - 7 tri???u vnd/th??ng','5 ng?????i','b??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','H?? N???i')," +
                    "(null,6,"+R.drawable.cong_nhan+",'C??ng nh??n nh?? m??y th???y ??i???n H??a B??nh','Nh?? m??y th???y ??i???n HB','30/7/2022','20 - 50 tri???u vnd/th??ng','75 ng?????i','to??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','Tp.H??a B??nh')," +
                    "(null,7,"+R.drawable.nv_phuc_vu+",'Nh??n vi??n ph???c v??? t???i nh?? h??ng Ngon','Nh?? h??ng Ngon','30/7/2022','7 - 12 tri???u vnd/th??ng','12 ng?????i','to??n th???i gian',null,'Kh??ng y??u c???u','Kh??ng y??u c???u','C?? Mau')");
        };

        //database.QueryData("delete from TinTuyenDung");
        //database.QueryData("drop table ViecLamDaLuu");
    }
    // select * from TinTuyenDung limit 3
}