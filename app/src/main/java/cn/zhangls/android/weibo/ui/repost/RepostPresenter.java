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

package cn.zhangls.android.weibo.ui.repost;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.network.api.UsersAPI;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.utils.SharedPreferenceInfo;
import cn.zhangls.android.weibo.utils.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/27.
 * <p>
 * repost presenter
 */

class RepostPresenter implements RepostContract.RepostPresenter {

    /**
     * RepostView
     */
    private RepostContract.RepostView mRepostView;
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    private User mUser;
    /**
     * 用户接口方法类
     */
    private UsersAPI mUsersAPI;

    RepostPresenter(Context context, RepostContract.RepostView repostView) {
        mContext = context;
        mRepostView = repostView;
        mRepostView.setPresenter(this);

        mAccessToken = AccessTokenKeeper.readAccessToken(context);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mUsersAPI = new UsersAPI(mContext, mAccessToken);
    }

    /**
     * 从服务器获取用户信息
     */
    @Override
    public void getUserByService() {
        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
        if (mAccessToken.isSessionValid()) {
            getUser();
        }
    }

    /**
     * 获取用户信息
     */
    private void getUser() {
        Observer<User> observer = new Observer<User>() {

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(mContext, "获取用户信息失败");
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                mUser = user;
                saveUserInfo();
                mRepostView.setSubTitle();
            }
        };

        mUsersAPI.getUser(observer);
    }

    /**
     * 保存用户信息
     */
    private void saveUserInfo() {
        SharedPreferenceInfo sharedPreferenceInfo = new SharedPreferenceInfo(mContext);
        sharedPreferenceInfo.setUserName(mUser.getScreen_name());
    }
}
