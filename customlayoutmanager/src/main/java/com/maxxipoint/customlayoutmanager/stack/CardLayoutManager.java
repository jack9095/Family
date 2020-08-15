package com.maxxipoint.customlayoutmanager.stack;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CardLayoutManager extends RecyclerView.LayoutManager {

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    public void setParam(@NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = checkIsNull(recyclerView);
        this.mItemTouchHelper = checkIsNull(itemTouchHelper);
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        // 当数据源个数大于最大显示数时
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            for (int position = CardConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                    view.setScaleX(1 - (position - 1) * CardConfig.DEFAULT_SCALE_X);
                    view.setScaleY(1 - (position - 1) * CardConfig.DEFAULT_SCALE_Y);
                    view.setTranslationX((position - 1) * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE_X);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE_Y);
                    view.setTranslationX(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE_X);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE_Y);
                    view.setTranslationX(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }
    }

    private RecyclerView.ViewHolder childViewHolder;
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            childViewHolder = mRecyclerView.getChildViewHolder(v);
            gestureDetector.onTouchEvent(event);
            return true;
        }
    };

    private GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Toast.makeText(mRecyclerView.getContext(),"点击事件",Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mItemTouchHelper.startSwipe(childViewHolder);
            return false;
        }
    });

}
