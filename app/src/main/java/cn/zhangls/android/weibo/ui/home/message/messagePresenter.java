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

package cn.zhangls.android.weibo.ui.home.message;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/6.
 */

public class MessagePresenter implements MessageContract.Presenter {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * MessageView
     */
    private MessageContract.MessageView mMessageView;
    /**
     * AccessToken 对象
     */
    private Oauth2AccessToken mAccessToken;

    public MessagePresenter(Context context, MessageContract.MessageView messageView) {
        mContext = context;
        mMessageView = messageView;
        mAccessToken = AccessTokenKeeper.readAccessToken(context);

        messageView.setPresenter(this);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {

    }

    @Override
    public void getUnreadMsg() {

    }
}
