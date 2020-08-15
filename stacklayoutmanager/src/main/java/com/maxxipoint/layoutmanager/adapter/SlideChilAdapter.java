package com.maxxipoint.layoutmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maxxipoint.layoutmanager.R;
import com.maxxipoint.layoutmanager.bean.SlideBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlideChilAdapter extends RecyclerView.Adapter<SlideChilAdapter.ViewHolder> {

    private List<SlideBean> mList;

    public SlideChilAdapter(List<SlideBean> list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SlideBean bean = mList.get(position);
        holder.imgBg.setImageResource(bean.getItemBg());
//        holder.tvTitle.setText(bean.getTitle());
//        holder.userIcon.setImageResource(bean.getUserIcon());
//        holder.userSay.setText(bean.getUserSay());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBg;
        ImageView userIcon;
        TextView tvTitle;
        TextView userSay;

        public ViewHolder(View itemView) {
            super(itemView);
            imgBg = itemView.findViewById(R.id.img_bg);
//            userIcon = itemView.findViewById(R.id.img_user);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//            userSay = itemView.findViewById(R.id.tv_user_say);
        }
    }
}
