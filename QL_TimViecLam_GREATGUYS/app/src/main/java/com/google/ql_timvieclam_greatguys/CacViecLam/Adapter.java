package com.google.ql_timvieclam_greatguys.CacViecLam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ql_timvieclam_greatguys.R;
import com.google.ql_timvieclam_greatguys.onClickItemViecLamListener;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private final Context context;
    private final List<ViecLam> apps;
    private onClickItemViecLamListener listener;

    public Adapter(Context context, List<ViecLam> apps, onClickItemViecLamListener listener) {
        this.context = context;
        this.apps = apps;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ViecLam viecLam;
        TextView mName;
        ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItemViecLam(viecLam);
                }
            });

            mName = itemView.findViewById(R.id.name);
            mImage = itemView.findViewById(R.id.image);
        }

        private void binhData(ViecLam viecLam){
            this.viecLam = viecLam;
            mName.setText(viecLam.getName());
            mImage.setImageResource(viecLam.getImage());
        }
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        holder.binhData(apps.get(position));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }
}
