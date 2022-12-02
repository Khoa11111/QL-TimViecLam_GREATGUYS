package com.google.ql_timvieclam_greatguys;

import android.content.Context;
import android.database.Cursor;

import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TinTuyenDung;
import com.google.ql_timvieclam_greatguys.CacTinTuyenDung.TuyenDungAdapter;
import com.google.ql_timvieclam_greatguys.Database.Database;

public class CkLogin {
    public static String ckLogin = "";
    public static String nganh;

    public static int getId(Context context){
        Database database = new Database(context,"QLTimViecLam",null,1);
        Cursor data = database.GetData("SELECT * " +
                " FROM AccUserInfor" +
                " WHERE accEmail='" + ckLogin + "'");
        data.moveToFirst();
        return data.getInt(0);
    }

    public static int getCount(Context context){
        Database database = new Database(context,"QLTimViecLam",null,1);
        Cursor data = database.GetData("SELECT count(*) \n" +
                                            "FROM AccUserInfor");
        data.moveToFirst();
        return data.getInt(0);
    }
}
