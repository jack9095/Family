package com.maxxipoint.layoutmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.maxxipoint.layoutmanager.R;
import com.maxxipoint.layoutmanager.bean.SlideBean;
import com.maxxipoint.layoutmanager.slide.ItemTouchHelperCallback;
import com.maxxipoint.layoutmanager.slide.OnSlideListener;
import com.maxxipoint.layoutmanager.slide.SlideLayoutManager;
import com.maxxipoint.layoutmanager.use.tool.TouchRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnSlideListener {

    private  List<SlideBean> mList = new ArrayList<>();

    public SlideAdapter() {
        int[] bgs = {
                R.mipmap.img_slide_1,
                R.mipmap.img_slide_2,
                R.mipmap.img_slide_3,
                R.mipmap.img_slide_4,
                R.mipmap.img_slide_5,
                R.mipmap.img_slide_6
        };

        String[] titles = {
                "标题1",
                "标题2",
                "标题3",
                "标题4",
                "标题5",
                "标题6"
        };

        for (int i = 0; i < 6; i++) {
            mList.add(new SlideBean(bgs[i],titles[i]));
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

            ItemTouchHelperCallback<SlideBean> mItemTouchHelperCallback = new ItemTouchHelperCallback(holder.recyclerView.getAdapter(), mList);
            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
            SlideLayoutManager<SlideBean> mSlideLayoutManager = new SlideLayoutManager(mItemTouchHelperCallback,holder.recyclerView, mItemTouchHelper);
            mSlideLayoutManager.setOnSlideListener(this);
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

    @Override
    public void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

    }

    @Override
    public void onSlided(RecyclerView.ViewHolder viewHolder, Object o, int direction) {

    }

    @Override
    public void onClear() {

    }

    @Override
    public void onItemClick(int position) {
        Log.e("SlideAdapter", "position = "+ position);
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
