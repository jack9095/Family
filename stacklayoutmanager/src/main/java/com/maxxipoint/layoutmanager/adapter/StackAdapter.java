package com.maxxipoint.layoutmanager.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.maxxipoint.layoutmanager.R;
import com.maxxipoint.layoutmanager.StackActivity;
import com.maxxipoint.layoutmanager.stack.CardConfig;
import com.maxxipoint.layoutmanager.stack.CardItemTouchHelperCallback;
import com.maxxipoint.layoutmanager.stack.CardLayoutManager;
import com.maxxipoint.layoutmanager.stack.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.MyViewHolder> {

    private List<Integer> list = new ArrayList<>();
    private List<String> stackLists;

    public StackAdapter() {
//        this.stackLists = stackLists;
        list.add(R.drawable.shape_constellation);
        list.add(R.drawable.shape_age);
        list.add(R.drawable.shape_constellation);
        list.add(R.drawable.shape_age);
        list.add(R.drawable.shape_constellation);
        list.add(R.drawable.shape_age);
        list.add(R.drawable.shape_constellation);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stack, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recyclerView.setAdapter(new MyAdapter(list));
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(holder.recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                StackActivity.MyAdapter.MyViewHolder myHolder = (StackActivity.MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
//                if (direction == CardConfig.SWIPING_LEFT) {
//                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
//                } else if (direction == CardConfig.SWIPING_RIGHT) {
//                    myHolder.likeImageView.setAlpha(Math.abs(ratio));
//                } else {
//                    myHolder.dislikeImageView.setAlpha(0f);
//                    myHolder.likeImageView.setAlpha(0f);
//                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
//                StackActivity.MyAdapter.MyViewHolder myHolder = (StackActivity.MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1f);
//                myHolder.dislikeImageView.setAlpha(0f);
//                myHolder.likeImageView.setAlpha(0f);
//                Toast.makeText(StackActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() { // 数据全部清空回调
//                Toast.makeText(StackActivity.this, "data clear", Toast.LENGTH_SHORT).show();
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initData();
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//                }, 3000L);
            }
        });

        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(holder.recyclerView, touchHelper);
        holder.recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }
}
