package com.pagedscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by sergio on 1/22/16.
 */
public class PagedScrollView extends HorizontalScrollView {
    private float FADING_EDGE_STRENGTH = 0.0f;
    private LinearLayout mContainer;
    private OnPagedScrollViewListener mListener;
    private float x1, x2, dx;

    public PagedScrollView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        mContainer = new LinearLayout(ctx);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        addView(mContainer);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        for (int i = 0; i < mContainer.getChildCount(); i++) {
            View child = mContainer.getChildAt(i);
            if (child.getLayoutParams().width != width) {
                child.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.FILL_PARENT));
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return FADING_EDGE_STRENGTH;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return FADING_EDGE_STRENGTH;
    }

    public void addPage(View child) {
        child.setLayoutParams(new LayoutParams(getWidth(), LayoutParams.FILL_PARENT));
        mContainer.addView(child);
        mContainer.requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            dx = x2-x1;
            int direction = (dx>0)?-1:1;

            int width = getWidth();
            int xx = ((int)(width * 0.2)) * direction;
            int pg = (getScrollX() + xx + width / 2) / width;
            smoothScrollTo(pg * width, 0);
        }

        return result;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mListener != null) {
            mListener.onPageChange(this);
        }
    }

    public boolean hasPage(View v) {
        return mContainer.indexOfChild(v) != -1;
    }

    public void removePage(View v) {
        mContainer.removeView(v);
    }

    public int getCurrentPage() {
        int width = getWidth();
        return (getScrollX() + width/2) / width;
    }

    public int getPageCount() {
        return mContainer.getChildCount();
    }

    public void removeAllPages() {
        mContainer.removeAllViews();
    }

    public void addOnPageChangeListener(OnPagedScrollViewListener listener) {
        this.mListener = listener;
    }

    public void removeOnPageChangeListener(OnPagedScrollViewListener l) {
        this.mListener = null;
    }
}
