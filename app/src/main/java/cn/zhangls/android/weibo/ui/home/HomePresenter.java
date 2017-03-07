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

package cn.zhangls.android.weibo.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.UsersAPI;
import cn.zhangls.android.weibo.network.models.User;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

class HomePresenter extends ParentPresenter<HomeContract.View> implements HomeContract.Presenter {
    /**
     * 用户信息
     */
    private User mUser;
    /**
     * 用户接口方法
     */
    private UsersAPI mUsersAPI;

    HomePresenter(Context context, @NonNull HomeContract.View homeView) {
        super(context, homeView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mUsersAPI = new UsersAPI(mContext, mAccessToken);
    }

    /**
     * 获取 User 信息
     */
    @Override
    public void getUser() {
        if (!mAccessToken.isSessionValid()) {
            return;
        }

        BaseObserver<User> observer = new BaseObserver<User>(mContext) {

            @Override
            public void onComplete() {
                mSubView.loadUserInfo(mUser);
            }

            @Override
            public void onNext(User user) {
                mUser = user;
            }
        };

        mUsersAPI.getUser(observer);
    }
}
