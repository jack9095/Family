package com.maxxipoint.layoutmanager.slide;

import android.annotation.*;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SlideLayoutManager<T> extends RecyclerView.LayoutManager {

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private OnSlideListener<T> mListener;

    public SlideLayoutManager(ItemTouchHelperCallback mItemTouchHelperCallback, @NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = checkIsNull(recyclerView);
        this.mItemTouchHelper = checkIsNull(itemTouchHelper);
        mItemTouchHelperCallback.setOnSlideListener(new OnSlideListener() {
            @Override
            public void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                if (direction == ItemConfig.SLIDING_LEFT) {
                } else if (direction == ItemConfig.SLIDING_RIGHT) {
                }
            }

            @Override
            public void onSlided(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                if (direction == ItemConfig.SLIDED_LEFT) {

                } else if (direction == ItemConfig.SLIDED_RIGHT) {

                }
                if (currentPosition >= getItemCount() - 1) {
                    currentPosition = 0;
                } else {
                    currentPosition++;
                }
            }

            @Override
            public void onClear() {

            }

            @Override
            public void onItemClick(int position) {

            }
        });
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

    public void setOnSlideListener(OnSlideListener<T> mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount > ItemConfig.DEFAULT_SHOW_ITEM) {
            for (int position = ItemConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                // 实际宽度 = 宽度 - 分割线的宽度
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 5 + getDecoratedMeasuredHeight(view));

                if (position == ItemConfig.DEFAULT_SHOW_ITEM) {
                    view.setScaleX(1 - (position - 1) * ItemConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * ItemConfig.DEFAULT_SCALE);
                    view.setTranslationX((position - 1) * view.getMeasuredWidth() / ItemConfig.DEFAULT_TRANSLATE);
//                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / ItemConfig.DEFAULT_TRANSLATE);
                } else if (position > 0) {
                    view.setScaleX(1 - position * ItemConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * ItemConfig.DEFAULT_SCALE);
//                    view.setTranslationY(position * view.getMeasuredHeight() / ItemConfig.DEFAULT_TRANSLATE);
                    view.setTranslationX(position * view.getMeasuredWidth() / ItemConfig.DEFAULT_TRANSLATE);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 5 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * ItemConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * ItemConfig.DEFAULT_SCALE);
//                    view.setTranslationY(position * view.getMeasuredHeight() / ItemConfig.DEFAULT_TRANSLATE);
                    view.setTranslationX(position * view.getMeasuredWidth() / ItemConfig.DEFAULT_TRANSLATE);
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

    int currentPosition = 0;
    private GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mListener != null) {
                mListener.onItemClick(currentPosition);
            }
//            Toast.makeText(mRecyclerView.getContext(),"点击事件" + childViewHolder.getLayoutPosition(),Toast.LENGTH_SHORT).show();
//            Toast.makeText(mRecyclerView.getContext(), "点击事件" + currentPosition, Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mItemTouchHelper.startSwipe(childViewHolder);
            return false;
        }
    });

}
