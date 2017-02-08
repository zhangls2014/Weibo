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

import android.content.Context;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/7.
 * <p>
 * Presenter 父类
 */

public class ParentPresenter<T extends BaseView> {

    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    protected Oauth2AccessToken mAccessToken;

    protected T mSubView;

    public ParentPresenter(Context context, @NonNull T subView) {
        mContext = context;
        mSubView = subView;
        mSubView.setPresenter(this);

        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
    }
}
