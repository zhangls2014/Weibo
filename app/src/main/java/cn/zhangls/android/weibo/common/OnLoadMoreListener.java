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

package cn.zhangls.android.weibo.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/4/4.
 * <p>
 * 自定义 RecyclerView 滚动监听器，实现加载更多功能
 */

public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "OnLoadMoreListener";
    /**
     * LinearLayoutManager
     */
    private LinearLayoutManager mLayoutManager;
    /**
     * RecyclerView Item 数量
     */
    private int mItemCount;
    /**
     * 加载更多是否可用
     */
    private boolean canLoadMore;
    /**
     * RecyclerView 最后完全可见的 Item 位置
     */
    private int mLastPosition;

    public OnLoadMoreListener(boolean canLoadMore) {
        super();
        this.canLoadMore = canLoadMore;
    }

    public abstract void onLoadMore();

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled. This will be
     * called after the scroll has completed.
     * <p>
     * This callback will also be called if visible item range changes after a layout
     * calculation. In that case, dx and dy will be 0.
     *
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx           The amount of horizontal scroll.
     * @param dy           The amount of vertical scroll.
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            mItemCount = mLayoutManager.getItemCount();
            mLastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
        } else {
            Log.e(TAG, "The OnLoadMoreListener only support LinearLayoutManager");
            return;
        }
        // 当加载更多可用、且 RecyclerView 已经滚动到了底部时，调用加载更多的方法
        if (canLoadMore && mLastPosition == mItemCount - 1 && mItemCount > 0 && mLastPosition > 0) {
            onLoadMore();
        }
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }
}
