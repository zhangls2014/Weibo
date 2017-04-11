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

package cn.zhangls.android.weibo.ui.user;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.UsersAPI;
import cn.zhangls.android.weibo.network.models.User;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/4/10.
 */

class UserPresenter extends ParentPresenter<UserContract.UserView> implements UserContract.Presenter {
    /**
     * UserAPI
     */
    private UsersAPI mUsersAPI;
    /**
     * User 数据结构体
     */
    private User mUser;

    UserPresenter(Context context, @NonNull UserContract.UserView subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mUsersAPI = new UsersAPI(mContext, mAccessToken);
    }

    private BaseObserver<User> getObserver() {
        return new BaseObserver<User>(mContext) {
            @Override
            public void onNext(User value) {
                mUser = value;
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {
                mSubView.loadUserInfo(mUser);
            }
        };
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param uid 需要查询的用户ID
     */
    @Override
    public void requestUserInfo(long uid) {
        if (!mAccessToken.isSessionValid()) {
            mSubView.showLoginSnackbar();
        } else {
            mUsersAPI.getUser(getObserver());
        }
    }

    /**
     * 根据用户昵称获取用户信息
     *
     * @param screenName 需要查询的用户昵称
     */
    @Override
    public void requestUserInfo(String screenName) {
        if (!mAccessToken.isSessionValid()) {
            mSubView.showLoginSnackbar();
        } else {
            mUsersAPI.getUser(getObserver());
        }
    }
}
