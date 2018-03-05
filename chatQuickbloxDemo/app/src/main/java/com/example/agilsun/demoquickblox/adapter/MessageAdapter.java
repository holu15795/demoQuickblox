package com.example.agilsun.demoquickblox.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************
 * * Created by HoLu on 05/03/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private List<MessageModel> mList = new ArrayList<>();
    private int mUserId ;

    public void setData(List<MessageModel> list){
        mList = list;
        notifyDataSetChanged();
    }

    public void setUserId(int userid){
        mUserId = userid;
    }
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_friend,parent,false));
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageModel model = mList.get(position);
        if (model.getSend()==mUserId){
            holder.fLinear.setGravity(Gravity.RIGHT);
        }
        else {
            holder.fLinear.setGravity(Gravity.LEFT);
        }
        holder.fTxt.setText(model.getBody());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView fTxt;
        LinearLayout fLinear;
        public MessageViewHolder(View itemView) {
            super(itemView);
            fTxt = itemView.findViewById(R.id.layout_chat_friends_txt);
            fLinear = itemView.findViewById(R.id.layout_chat_friends_linear);
        }
    }
}
