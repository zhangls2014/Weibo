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

package cn.zhangls.android.weibo.ui.details.comment;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.models.Status;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/7.
 *
 *
 */

class CommentPresenter extends ParentPresenter<CommentContract.CommentView> implements CommentContract.Presenter {

    /**
     * StatusesAPI
     */
    private StatusesAPI mStatusesAPI;
    /**
     * CommentsAPI
     */
    private CommentsAPI mCommentsAPI;
    /**
     * Status
     */
    private Status mStatus;
    /**
     * 微博评论列表
     */
    private CommentList mCommentList;

    CommentPresenter(Context context, @NonNull CommentContract.CommentView subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
        mCommentsAPI = new CommentsAPI(mContext, mAccessToken);
    }

    /**
     * 获取微博正文
     *
     * @param id 微博ID
     */
    @Override
    public void getStatus(long id) {
        if (!mAccessToken.isSessionValid()) {
            return;
        }

        BaseObserver<Status> observer = new BaseObserver<Status>(mContext) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSubView.stopRefresh();
            }

            @Override
            public void onComplete() {
                mSubView.showContent(mStatus);
                mSubView.stopRefresh();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Status value) {
                mStatus = value;
            }
        };

        mStatusesAPI.show(observer, id);
    }

    /**
     * 根据微博ID返回某条微博的评论列表。
     *
     * @param id               需要查询的微博ID。
     * @param since_id         若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count            单页返回的记录条数，默认为50
     * @param page             返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
     *                         <li>{@link CommentsAPI#AUTHOR_FILTER_ALL}
     *                         <li>{@link CommentsAPI#AUTHOR_FILTER_ATTENTIONS}
     *                         <li>{@link CommentsAPI#AUTHOR_FILTER_STRANGER}
     */
    @Override
    public void getCommentById(long id, long since_id, long max_id, int count, int page, int filter_by_author) {
        if (!mAccessToken.isSessionValid()) {
            return;
        }

        BaseObserver<CommentList> observer = new BaseObserver<CommentList>(mContext) {
            @Override
            public void onComplete() {
                super.onComplete();
                mSubView.showComment(mCommentList);
                mSubView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(CommentList value) {
                mCommentList = value;
            }

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
            }
        };

        mCommentsAPI.show(observer, id, since_id, max_id, count, page, filter_by_author);
    }
}
