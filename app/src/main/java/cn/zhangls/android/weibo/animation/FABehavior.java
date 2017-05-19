/*
 * MIT License
 *
 * Copyright (c) 2017 zhangls2014
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.zhangls.android.weibo.animation;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/4/1.
 *
 */

public class FABehavior extends FloatingActionButton.Behavior {

    /**
     * 隐藏动画是否正在执行
     */
    private boolean mIsAnimating = false;

    public FABehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        // 如果是在垂直方向滚动，则返回 true
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE && !mIsAnimating) { // 手指上滑，隐藏FAB
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() == View.INVISIBLE && !mIsAnimating) { // 手指下滑，显示FAB
            animateIn(child);
        }
    }

    private void animateIn(final FloatingActionButton fab) {
        ViewCompat.animate(fab)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        mIsAnimating = true;
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        mIsAnimating = false;
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        mIsAnimating = false;
                        fab.setVisibility(View.INVISIBLE);
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .translationY(-fab.getHeight())
                .start();
    }

    private void animateOut(final FloatingActionButton fab) {
        ViewCompat.animate(fab)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        mIsAnimating = true;
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        mIsAnimating = false;
                        fab.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        mIsAnimating = false;
                        fab.setVisibility(View.VISIBLE);
                    }
                })
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(300)
                .translationY(fab.getHeight())
                .start();
    }
}
