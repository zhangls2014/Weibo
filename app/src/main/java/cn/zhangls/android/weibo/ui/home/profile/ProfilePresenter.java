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

package cn.zhangls.android.weibo.ui.home.profile;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.network.api.UsersAPI;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.utils.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangls on 2016/11/16.
 *
 * ProfilePresenter
 */

class ProfilePresenter implements ProfileContract.Presenter {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 视图对象
     */
    private ProfileContract.View mProfileView;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    /**
     * 用户信息
     */
    private User mUser;
    /**
     * 用户接口方法
     */
    private UsersAPI mUsersAPI;

    /**
     * 构造方法
     *
     * @param profileView 视图
     * @param mContext 上下文对象
     */
    ProfilePresenter(ProfileContract.View profileView, Context mContext) {
        this.mContext = mContext;
        mProfileView = profileView;
        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);

        mProfileView.setPresenter(this);
    }

    @Override
    public void start() {
        mUsersAPI = new UsersAPI(mContext, mAccessToken);
    }

    @Override
    public void getUser() {
        if (mAccessToken.isSessionValid()) {
            getUserByService();
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserByService() {
        Observer<User> observer = new Observer<User>() {

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(mContext, "获取用户信息失败");
            }

            @Override
            public void onComplete() {
                mProfileView.loadData(mUser);
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                mUser = user;
            }
        };

        mUsersAPI.getUser(observer);
    }
}
