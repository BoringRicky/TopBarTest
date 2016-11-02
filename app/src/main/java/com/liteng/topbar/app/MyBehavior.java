package com.liteng.topbar.app;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright © 2016 . All rights reserved.
 *
 * @author: 李腾
 * @date: 16/11/1 上午11:14
 * @version:
 */
public class MyBehavior extends CoordinatorLayout.Behavior<View> {

    //用来在代码里实例化我们自定义的Behavior的构造
    public MyBehavior() {
    }

    //如果要在布局中使用Behavior，这个构造是必须的
    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 指定依赖的View,在这里指定依赖的View之后，
     * 我们去{@linkplain #onDependentViewChanged(CoordinatorLayout, View, View)}里去处理这两个依赖的View之间的关系
     *
     * @param parent
     * @param child      使用该Behavior的View
     * @param dependency 依赖的View
     * @return 当指定的View是我们需要的View时，返回true
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    /**
     * 当我们指定依赖的View 有变化时，调用这个方法
     * @param parent
     * @param child 使用该Behavior的View
     * @param dependency 依赖的View
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        final View finalChild = child;
        AppBarLayout finalDependency = (AppBarLayout) dependency;
        finalDependency.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float tranlationY = Math.abs(verticalOffset / 3);
                finalChild.setTranslationY(tranlationY);
            }
        });
        return true;
    }
}
