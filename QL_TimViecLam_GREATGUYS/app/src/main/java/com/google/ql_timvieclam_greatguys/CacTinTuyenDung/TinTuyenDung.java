package com.google.ql_timvieclam_greatguys.CacTinTuyenDung;

public class TinTuyenDung {
    private String TuyenDung;
    private String Luong;
    private int Hinh;
    private String Ten;
    private String DiaDiem;
    private String TinUuTien;

    public TinTuyenDung(String tuyenDung, String luong, int hinh, String ten, String diaDiem, String tinUuTien) {
        TuyenDung = tuyenDung;
        Luong = luong;
        Hinh = hinh;
        Ten = ten;
        DiaDiem = diaDiem;
        TinUuTien = tinUuTien;
    }

    public String getTuyenDung() {
        return TuyenDung;
    }

    public void setTuyenDung(String tuyenDung) {
        TuyenDung = tuyenDung;
    }

    public String getLuong() {
        return Luong;
    }

    public void setLuong(String luong) {
        Luong = luong;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        DiaDiem = diaDiem;
    }

    public String getTinUuTien() {
        return TinUuTien;
    }

    public void setTinUuTien(String tinUuTien) {
        TinUuTien = tinUuTien;
    }
}
