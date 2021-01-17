package com.example.chatakudong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatakudong.R;
//import com.example.chatakudong.model.Chat;
import com.example.chatakudong.menu.Chatting;
import com.example.chatakudong.model.Tab;

import java.util.List;

public class AdapterTab extends RecyclerView.Adapter<AdapterTab.Holder> {

    private List<Tab> list;
    private Context context;

    public AdapterTab(List<Tab> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout,parent,false);
//        return null;
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Tab chat = list.get(position);

        holder.textViewNama.setText(chat.getNama());
        holder.textViewTanggal.setText(chat.getTanggal());
        holder.textViewKeterangan.setText(chat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Chatting.class).
                        putExtra("ID", chat.getID()).
                        putExtra("nama", chat.getNama()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView textViewNama, textViewTanggal, textViewKeterangan;
        public Holder(@NonNull View itemView) {
            super(itemView);

            textViewNama = itemView.findViewById(R.id.text_view_nama);
            textViewTanggal = itemView.findViewById(R.id.text_view_tanggal);
            textViewKeterangan = itemView.findViewById(R.id.text_view_keterangan);
        }
    }
}
