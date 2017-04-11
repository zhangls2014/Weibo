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

package cn.zhangls.android.weibo.ui.edit;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.api.UsersAPI;
import cn.zhangls.android.weibo.network.models.Comment;
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

class EditPresenter extends ParentPresenter<EditContract.EditView> implements EditContract.EditPresenter {

    private User mUser;
    /**
     * 用户接口方法类
     */
    private UsersAPI mUsersAPI;
    /**
     * 微博接口方法类
     */
    private StatusesAPI mStatusesAPI;
    /**
     * 评论接口方法类
     */
    private CommentsAPI mCommentsAPI;

    EditPresenter(Context context, @NonNull EditContract.EditView subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mUsersAPI = new UsersAPI(mContext, mAccessToken);
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
        mCommentsAPI = new CommentsAPI(mContext, mAccessToken);
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
        BaseObserver<Status> observer = new BaseObserver<Status>(mContext) {
            @Override
            public void onComplete() {
                ToastUtil.showShortToast(mContext, "转发微博成功");
                mSubView.submitCompleted();
            }
        };
        mStatusesAPI.repost(observer, id, status, is_comment, rip);
    }

    /**
     * 对一条微博进行评论。
     *
     * @param comment     评论内容，内容不超过140个汉字。
     * @param id          需要评论的微博ID。
     * @param comment_ori 当评论转发微博时，是否评论给原微博
     */
    @Override
    public void create(String comment, long id, int comment_ori) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        BaseObserver<Comment> observer = new BaseObserver<Comment>(mContext) {
            @Override
            public void onComplete() {
                ToastUtil.showShortToast(mContext, "评论成功");
                mSubView.submitCompleted();
            }
        };
        mCommentsAPI.create(observer, comment, id, comment_ori);
    }

    /**
     * 回复一条评论。
     *
     * @param cid             需要回复的评论ID
     * @param id              需要评论的微博ID
     * @param comment         回复评论内容，内容不超过140个汉字
     * @param without_mention 回复中是否自动加入“回复@用户名”，0：是、1：否，默认为0。
     * @param comment_ori     当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
     */
    @Override
    public void reply(long cid, long id, String comment, int without_mention, int comment_ori) {
        BaseObserver<Comment> observer = new BaseObserver<Comment>(mContext) {
            @Override
            public void onComplete() {
                ToastUtil.showShortToast(mContext, "回复成功");
                mSubView.submitCompleted();
            }
        };
        mCommentsAPI.reply(observer, cid, id, comment, without_mention, comment_ori);
    }

    /**
     * 发布一条新微博
     *
     * @param status  要发布的微博文本内容，必须做URLencode，内容不超过140个汉字
     * @param visible 微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0
     * @param list_id 微博的保护投递指定分组ID，只有当visible参数为3时生效且必选
     */
    @Override
    public void updateStatus(String status, int visible, String list_id) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        BaseObserver<Status> observer = new BaseObserver<Status>(mContext) {
            @Override
            public void onNext(Status value) {

            }

            @Override
            public void onComplete() {
                ToastUtil.showShortToast(mContext, "微博发布成功");
                mSubView.submitCompleted();
            }
        };
        mStatusesAPI.update(observer, status, visible, list_id);
    }

    /**
     * 获取用户信息
     *
     *
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
                mSubView.setSubTitle();
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
