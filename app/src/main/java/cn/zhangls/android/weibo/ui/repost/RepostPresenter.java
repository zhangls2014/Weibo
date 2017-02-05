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
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.api.UsersAPI;
import cn.zhangls.android.weibo.network.models.Status;
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

    private StatusesAPI mStatusesAPI;

    RepostPresenter(Context context, RepostContract.RepostView repostView) {
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        mContext = context;
        mRepostView = repostView;
        mRepostView.setHomePresenter(this);

    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mUsersAPI = new UsersAPI(mContext, mAccessToken);
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
    }

    /**
     * 从服务器获取用户信息
     */
    @Override
    public void getUserByService() {
        if (mAccessToken.isSessionValid()) {
            getUser();
        }
    }

    /**
     * 转发一条微博
     *
     * @param id         要转发的微博ID
     * @param status     添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”
     * @param is_comment 是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
     *                   <li> {@link StatusesAPI#COMMENTS_NONE}
     *                   <li> {@link StatusesAPI#COMMENTS_CUR_STATUSES}
     *                   <li> {@link StatusesAPI#COMMENTS_RIGAL_STATUSES}
     *                   <li> {@link StatusesAPI#COMMENTS_BOTH}
     * @param rip        开发者上报的操作用户真实IP，形如：211.156.0.1
     */
    @Override
    public void repost(long id, String status, int is_comment, String rip) {
        Observer<Status> observer = new Observer<Status>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Status value) {
                ToastUtil.showShortToast(mContext, "转发微博成功");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(mContext, "转发微博失败");
            }

            @Override
            public void onComplete() {

            }
        };
        mStatusesAPI.repost(observer, id, status, is_comment, rip);
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
