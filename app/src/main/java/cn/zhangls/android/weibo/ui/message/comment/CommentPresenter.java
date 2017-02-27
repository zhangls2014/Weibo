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

package cn.zhangls.android.weibo.ui.message.comment;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.utils.ToastUtil;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/25.
 */

class CommentPresenter extends ParentPresenter<CommentContract.View> implements CommentContract.Presenter {
    /**
     * 每次获取的评论数
     */
    private static int COMMENT_COUNT = 50;
    /**
     * 获取的评论页数
     * eg:page = 1,获取的是第一页的评论
     */
    private static int COMMENT_PAGE = 1;
    /**
     * 评论接口方法对象
     */
    private CommentsAPI mCommentsAPI;
    /**
     * 评论列表
     */
    private CommentList mCommentList;

    CommentPresenter(Context context, @NonNull CommentContract.View subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mCommentsAPI = new CommentsAPI(mContext, mAccessToken);
    }

    /**
     * 获取 observer
     */
    private BaseObserver<CommentList> getObserver() {
        return new BaseObserver<CommentList>(mContext) {
            @Override
            public void onNext(CommentList value) {
                mCommentList = value;
            }

            @Override
            public void onComplete() {
                mSubView.showCommentMention(mCommentList);
            }
        };
    }

    /**
     * 获取当前登录用户所发出的评论列表。
     */
    @Override
    public void requestCommentByMe() {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        mCommentsAPI.byME(getObserver(), 0, 0, COMMENT_COUNT, COMMENT_PAGE, 0);
    }

    /**
     * 获取当前登录用户所接收到的评论列表。
     *
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
     *                   <li>{@link CommentsAPI#AUTHOR_FILTER_ALL}
     *                   <li>{@link CommentsAPI#AUTHOR_FILTER_ATTENTIONS}
     *                   <li>{@link CommentsAPI#AUTHOR_FILTER_STRANGER}
     */
    @Override
    public void requestCommentToMe(int authorType) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        mCommentsAPI.toME(getObserver(), 0, 0, COMMENT_COUNT, COMMENT_PAGE, authorType, 0);
    }
}
