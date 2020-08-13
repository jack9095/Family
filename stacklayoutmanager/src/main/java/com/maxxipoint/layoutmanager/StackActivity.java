package com.maxxipoint.layoutmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxxipoint.layoutmanager.adapter.MyAdapter;
import com.maxxipoint.layoutmanager.adapter.StackAdapter;
import com.maxxipoint.layoutmanager.stack.CardConfig;
import com.maxxipoint.layoutmanager.stack.CardItemTouchHelperCallback;
import com.maxxipoint.layoutmanager.stack.CardLayoutManager;
import com.maxxipoint.layoutmanager.stack.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;



public class StackActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        initData();
        initView();
    }

    private void initView() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new StackAdapter());
//        recyclerView.setAdapter(new MyAdapter(list));
//        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
//        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {
//
//            @Override
//            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
//                if (direction == CardConfig.SWIPING_LEFT) {
//                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
//                } else if (direction == CardConfig.SWIPING_RIGHT) {
//                    myHolder.likeImageView.setAlpha(Math.abs(ratio));
//                } else {
//                    myHolder.dislikeImageView.setAlpha(0f);
//                    myHolder.likeImageView.setAlpha(0f);
//                }
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
//                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1f);
//                myHolder.dislikeImageView.setAlpha(0f);
//                myHolder.likeImageView.setAlpha(0f);
//                Toast.makeText(StackActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipedClear() {
//                Toast.makeText(StackActivity.this, "data clear", Toast.LENGTH_SHORT).show();
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initData();
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//                }, 3000L);
//            }
//
//        });
//        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
//        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
//        recyclerView.setLayoutManager(cardLayoutManager);
//        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        list.add(R.drawable.img_avatar_01);
        list.add(R.drawable.img_avatar_02);
        list.add(R.drawable.img_avatar_03);
        list.add(R.drawable.img_avatar_04);
        list.add(R.drawable.img_avatar_05);
        list.add(R.drawable.img_avatar_06);
        list.add(R.drawable.img_avatar_07);
    }

}
