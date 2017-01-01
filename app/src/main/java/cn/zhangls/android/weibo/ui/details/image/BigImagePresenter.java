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

package cn.zhangls.android.weibo.ui.details.image;

import android.content.Context;

import cn.zhangls.android.weibo.ui.details.image.BigImageContract;

/**
 * Created by zhangls on 2016/12/6.
 *
 */

class BigImagePresenter implements BigImageContract.Presenter {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 视图对象
     */
    private BigImageContract.BigImageView mBigImageView;

    public BigImagePresenter(Context context, BigImageContract.BigImageView bigImageView) {
        mContext = context;
        mBigImageView = bigImageView;
        mBigImageView.setPresenter(this);
    }

    /**
     * Image 点击事件
     */
    @Override
    public void onClick() {

    }

    /**
     * Image 长按事件
     */
    @Override
    public void onLongClick() {

    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {

    }
}
