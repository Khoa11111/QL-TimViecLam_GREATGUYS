package com.google.ql_timvieclam_greatguys.CacTinTuyenDung;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.ql_timvieclam_greatguys.R;

import java.util.List;


public class TuyenDungAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TinTuyenDung> tinTuyenDungList;

    public TuyenDungAdapter(Context context, int layout, List<TinTuyenDung> tinTuyenDungList) {
        this.context = context;
        this.layout = layout;
        this.tinTuyenDungList = tinTuyenDungList;
    }

    @Override
    public int getCount() {
        return tinTuyenDungList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        ImageView imgHinh;
        TextView txtTen, txtTuyendung, txtLuong, txtDiadiem, txtTinuutien;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();

            holder.txtTuyendung =(TextView)view.findViewById(R.id.textviewTuyendung);
            holder.txtLuong =(TextView)view.findViewById(R.id.textviewLuong);
            holder.imgHinh = (ImageView) view.findViewById(R.id.imageviewHinh);
            holder.txtTen =(TextView)view.findViewById(R.id.textviewCty);
            holder.txtDiadiem =(TextView)view.findViewById(R.id.textviewDiadiem);
            holder.txtTinuutien =(TextView)view.findViewById(R.id.textviewTinuutien);
            view.setTag(holder);
        }else  {
            holder = (ViewHolder) view.getTag();

        }

        TinTuyenDung tinTuyenDung = tinTuyenDungList.get(i);

        holder.txtTuyendung.setText(tinTuyenDung.getTuyenDung());
        holder.txtLuong.setText(tinTuyenDung.getLuong());
        holder.imgHinh.setImageResource(tinTuyenDung.getHinh());
        holder.txtTen.setText(tinTuyenDung.getTen());
        holder.txtDiadiem.setText(tinTuyenDung.getDiaDiem());
        holder.txtTinuutien.setText(tinTuyenDung.getTinUuTien());

        return view;
    }
}
