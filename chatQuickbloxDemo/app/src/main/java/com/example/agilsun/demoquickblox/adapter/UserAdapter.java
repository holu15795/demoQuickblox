package com.example.agilsun.demoquickblox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.helper.OnItemClickListener;
import com.example.agilsun.demoquickblox.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************
 * * Created by HoLu on 28/02/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<UserModel> mList = new ArrayList<>();
    private OnItemClickListener mListener;

    public void setData(List<UserModel> list){
        mList = list;
        notifyDataSetChanged();
    }
    public void setListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_chat,parent,false));
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        UserModel name = mList.get(position);
        holder.fName.setText(name.getId());
        holder.flinear.setOnClickListener(new View.OnClickListener() {
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

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView fName;
        private LinearLayout flinear;
        public UserViewHolder(View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.adapter_txt_name);
            flinear = itemView.findViewById(R.id.adapter_linear);
        }
    }
}
