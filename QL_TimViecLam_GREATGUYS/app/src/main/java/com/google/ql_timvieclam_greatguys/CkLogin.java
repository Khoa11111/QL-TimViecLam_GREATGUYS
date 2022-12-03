package com.google.ql_timvieclam_greatguys;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.Database.Database;

public class CkLogin {
    public static String ckLogin = "";
    public static int id, idTTD;

    public static int getId(Context context){
        Database database = new Database(context,"QLTimViecLam",null,1);
        Cursor data = database.GetData("SELECT * " +
                " FROM AccUserInfor" +
                " WHERE accEmail='" + ckLogin + "'");
        data.moveToFirst();
        return data.getInt(0);
    }

    public static int getCount(Context context,String table){
        try{
            Database database = new Database(context,"QLTimViecLam",null,1);
            Cursor data = database.GetData("SELECT count(*) \n" +
                    "FROM " + table);
            data.moveToFirst();
            return data.getInt(0);
        } catch (Exception e) {
            Toast.makeText(context,"Lỗi Count",Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public static int getCount(Context context,String table,String dieukien){
        try{
            Database database = new Database(context,"QLTimViecLam",null,1);
            Cursor data = database.GetData("SELECT * \n" +
                    "FROM " + table + "\n" +
                    "WHERE " + dieukien);
            data.moveToFirst();
            return data.getCount();
        } catch (Exception e) {
            Toast.makeText(context,"Lỗi Count",Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public static boolean getIdTinTuyenDung(Context context, int i){
        try{
            Database database = new Database(context,"QLTimViecLam",null,1);
            int count, maxCount;
            maxCount = getCount(context, "ChiTietTinTuyenDung");
            if(i + 1 > maxCount)
                count = maxCount;
            else
                count = i + 1;
            Cursor data = database.GetData("select * from TinTuyenDung limit "+ count );
            data.moveToLast();
            int idT = data.getInt(0);
            CkLogin.id = idT;
            data.isLast();
            return true;
        } catch (Exception e) {
            Toast.makeText(context,"Lỗi set id",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean getIdTTD(Context context, int i, String table){
        try{
            Database database = new Database(context,"QLTimViecLam",null,1);
            int count, maxCount;
            maxCount = getCount(context, table);
            if(i + 1 > maxCount)
                count = maxCount;
            else
                count = i + 1;
            Cursor data = database.GetData("select * from " + table + " limit "+ count );
            data.moveToLast();
            int idT = data.getInt(1);
            CkLogin.id = idT;
            data.isLast();
            return true;
        } catch (Exception e) {
            Toast.makeText(context,"Lỗi set id",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean getIdTinTuyenDung(Context context, int i,int idTTD){
        try{
            Database database = new Database(context,"QLTimViecLam",null,1);
            int count, maxCount;
            maxCount = getCount(context, "ChiTietTinTuyenDung", "idTTD = " + idTTD);
            if(i + 1 > maxCount)
                count = maxCount;
            else
                count = i + 1;
            Cursor data = database.GetData("select * from TinTuyenDung \n" +
                                                "WHERE id = " + idTTD +" \n" +
                                                "limit "+ count);
            data.moveToLast();
            int idT = data.getInt(0);
            CkLogin.id = idT;
            data.isLast();
            return true;
        } catch (Exception e) {
            Toast.makeText(context,"Không tìm thấy trang chi tiết",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
