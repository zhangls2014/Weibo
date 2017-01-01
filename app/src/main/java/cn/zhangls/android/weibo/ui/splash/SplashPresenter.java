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

package cn.zhangls.android.weibo.ui.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;

/**
 * Created by zhangls on 2016/10/28.
 *
 */
class SplashPresenter implements SplashContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * View对象
     */
    @NonNull
    private SplashContract.View mSplashView;
    /**
     * 封装授权信息
     */
    private Oauth2AccessToken mAccessToken;

    SplashPresenter(Context context, @NonNull SplashContract.View splashView) {
        mSplashView = splashView;
        mSplashView.setPresenter(this);
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
    }

    /**
     * 开始
     */
    @Override
    public void start() {
        //初始化日志设置
        initLogger();

        if (mAccessToken.isSessionValid()) {
            mSplashView.toHomeActivity();
        } else {
            mSplashView.toLoginActivity();
        }
    }

    /**
     * 初始化日志工具
     */
    private void initLogger() {
        Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);                // default 0
//                .logAdapter(new AndroidAdapter()); //default AndroidLogAdapter
    }
}
