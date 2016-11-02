package com.liteng.topbar.app;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

public class ScrollBehavior extends CoordinatorLayout.Behavior<View>{

    public ScrollBehavior() {
    }

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 这里返回true，才会接收到后续滑动事件
     *
     * @param coordinatorLayout
     * @param child             使用该Behavior的View
     * @param directTargetChild Coordinator的子View，它可能包含一些滑动的操作
     * @param target            初始化滑动动作的View
     * @param nestedScrollAxes  滑动的轴，根据这个值来判断是纵向滑动还是横向滑动;
     *                          {@linkplain ViewCompat#SCROLL_AXIS_VERTICAL} 竖向；
     *                          {@linkplain ViewCompat#SCROLL_AXIS_HORIZONTAL} 横向
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 这里可以获取到CoordinatorLayout的滑动
     *
     * @param coordinatorLayout
     * @param child             使用该Behavior的View
     * @param target            执行滑动动作的View
     * @param dx                横向滑动的距离,以像素为单位
     * @param dy                纵向滑动的距离,以像素为单位
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        final View finalChild = child;
        if (target instanceof NestedScrollView){
            NestedScrollView view = (NestedScrollView) target;
            view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    finalChild.setTranslationY(scrollY/5);
                }
            });
        }
    }


    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }
}
