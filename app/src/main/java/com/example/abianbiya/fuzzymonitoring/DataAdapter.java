package com.example.abianbiya.fuzzymonitoring;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private Context context;
    private Data data;
    List<Data> dataList;


    public DataAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView noreg;
        public Button detailBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noreg = (TextView) itemView.findViewById(R.id.tv_nama);
            detailBtn = itemView.findViewById(R.id.btn_detail);
        }
    }

    @Override
    public DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.data_item, parent, false);

        return new DataAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.MyViewHolder myViewHolder, int i) {
        final Data data = dataList.get(i);

        LayoutInflater li = LayoutInflater.from(context);
        final View views = li.inflate(R.layout.dialog_detail, null);

        TextView kode = views.findViewById(R.id.tv_kodenya);
        TextView umur = views.findViewById(R.id.tv_umurnya);
        TextView bobot = views.findViewById(R.id.tv_bobotnya);
        TextView bcs = views.findViewById(R.id.tv_bcsnya);

        kode.setText(data.getId());

        myViewHolder.noreg.setText(data.getId());
        myViewHolder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(context)
                        .title("Detail Ternak")
                        .customView(views, true)
                        .positiveText("OK")
                        .positiveColor(R.color.text_darker_gray)
                        .show();
            }
        });


    }

}
