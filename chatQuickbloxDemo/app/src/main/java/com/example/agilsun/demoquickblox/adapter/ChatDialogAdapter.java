package com.example.agilsun.demoquickblox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.helper.OnItemClickListener;
import com.example.agilsun.demoquickblox.model.ChatDialogModel;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************
 * * Created by HoLu on 28/02/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class ChatDialogAdapter extends RecyclerView.Adapter<ChatDialogAdapter.ChatDialogViewHolder> {
    private List<ChatDialogModel> mList = new ArrayList<>();
    private OnItemClickListener mListener;

    public void setData(List<ChatDialogModel> list){
        mList = list;
        notifyDataSetChanged();
    }
    public void setListener(OnItemClickListener listener){
        mListener = listener;
    }
    @Override
    public ChatDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatDialogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_chat,parent,false));
    }

    @Override
    public void onBindViewHolder(final ChatDialogViewHolder holder, int position) {
        ChatDialogModel m = mList.get(position);
        holder.fname.setText(m.getNameDialog());
        holder.fCountUnRead.setText(String.valueOf(m.getUnRead()));
        holder.fLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList.size()>0){
            return mList.size();
        }
        else {
            return 0;
        }
    }

    public class ChatDialogViewHolder extends RecyclerView.ViewHolder {
        private TextView fname,fCountUnRead;
        private LinearLayout fLinear;
        public ChatDialogViewHolder(View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.adapter_txt_name);
            fCountUnRead = itemView.findViewById(R.id.adapter_txt_count_unread);
            fLinear = itemView.findViewById(R.id.adapter_linear);
        }
    }
}
