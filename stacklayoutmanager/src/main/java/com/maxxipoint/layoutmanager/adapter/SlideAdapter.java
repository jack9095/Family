package com.maxxipoint.layoutmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.maxxipoint.layoutmanager.R;
import com.maxxipoint.layoutmanager.bean.SlideBean;
import com.maxxipoint.layoutmanager.slide.ItemTouchHelperCallback;
import com.maxxipoint.layoutmanager.slide.SlideLayoutManager;
import com.maxxipoint.layoutmanager.widget.TouchRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  List<SlideBean> mList = new ArrayList<>();

    public SlideAdapter() {
        int[] icons = {R.mipmap.header_icon_1, R.mipmap.header_icon_2, R.mipmap.header_icon_3,
                R.mipmap.header_icon_4, R.mipmap.header_icon_1, R.mipmap.header_icon_2};
        String[] titles = {"Acknowledging", "Belief", "Confidence", "Dreaming", "Happiness", "Confidence"};
        String[] says = {
                "Do one thing at a time, and do well.",
                "Keep on going never give up.",
                "Whatever is worth doing is worth doing well.",
                "I can because i think i can.",
                "Jack of all trades and master of none.",
                "Keep on going never give up.",
                "Whatever is worth doing is worth doing well.",
        };
        int[] bgs = {
                R.mipmap.img_slide_1,
                R.mipmap.img_slide_2,
                R.mipmap.img_slide_3,
                R.mipmap.img_slide_4,
                R.mipmap.img_slide_5,
                R.mipmap.img_slide_6
        };

        for (int i = 0; i < 6; i++) {
            mList.add(new SlideBean(bgs[i],titles[i],icons[i],says[i]));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stack, parent, false);
            return new MyViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new TwoViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mHolder, int position) {

        if (mHolder instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) mHolder;
            holder.recyclerView.setAdapter(new SlideChilAdapter(mList));

            ItemTouchHelperCallback mItemTouchHelperCallback = new ItemTouchHelperCallback(holder.recyclerView.getAdapter(), mList);
            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
            SlideLayoutManager mSlideLayoutManager = new SlideLayoutManager(holder.recyclerView, mItemTouchHelper);
            mItemTouchHelper.attachToRecyclerView(holder.recyclerView);
            holder.recyclerView.setLayoutManager(mSlideLayoutManager);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TouchRecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {

        public TwoViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
