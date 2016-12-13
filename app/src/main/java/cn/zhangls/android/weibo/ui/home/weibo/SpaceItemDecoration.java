/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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

package cn.zhangls.android.weibo.ui.home.weibo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.zhangls.android.weibo.R;

/**
 * Created by zhangls on 2016/11/18.
 *
 * 为 PictureRecyclerView 创建空白分隔线
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 空间分割线的宽度
     */
    private float space;

    /**
     * 构造方法
     *
     * @param context 上下文对象
     */
    public SpaceItemDecoration(Context context) {
        space = context.getResources().getDimension(R.dimen.margin_4);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawDividerLine(c, parent);
    }

    /**
     * 绘制分割线
     * @param c onDraw传递的Canvas
     * @param parent RecyclerView
     */
    private void drawDividerLine(Canvas c, RecyclerView parent) {
        // 左侧
        int left = 0;
        // 顶部
        int top = 0;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            // RecyclerView Item
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // 如果不是每行的第一列时，添加分割线
            if (i % 3 != 0) {
                left = (int) space;
            } else {
                left = 0;
            }
            // 如果不是第一行时，添加分割线
            if (i >= 3) {
                top = (int) space;
            } else {
                top = 0;
            }
            params.setMargins(left, top, 0, 0);
            child.setLayoutParams(params);
        }
    }
}
