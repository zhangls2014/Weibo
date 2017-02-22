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

package cn.zhangls.android.weibo.ui.message;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.utils.ToastUtil;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/20.
 * <p>
 * MessageActivity Presenter
 */

class MessagePresenter extends ParentPresenter<MessageContract.View> implements MessageContract.Presenter {

    /**
     * 每次获取的微博数
     */
    private static int WEIBO_COUNT = 50;
    /**
     * 获取的微博页数
     * eg:page = 1,获取的是第一页的微博
     */
    private static int WEIBO_PAGE = 1;
    /**
     * 微博接口方法对象
     */
    private StatusesAPI mStatusesAPI;
    /**
     * 评论接口方法对象
     */
    private CommentsAPI mCommentsAPI;
    /**
     * 微博列表
     */
    private StatusList mStatusList;
    /**
     * 评论列表
     */
    private CommentList mCommentList;

    MessagePresenter(Context context, @NonNull MessageContract.View subView) {
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
     * 获取最新的提到登录用户的微博列表，即@我的微博。
     *
     * @param since_id         若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count            单页返回的记录条数，默认为50
     * @param page             返回结果的页码，默认为1
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
     *                         <li> {@link StatusesAPI#AUTHOR_FILTER_ALL}
     *                         <li> {@link StatusesAPI#AUTHOR_FILTER_ATTENTIONS}
     *                         <li> {@link StatusesAPI#AUTHOR_FILTER_STRANGER}
     * @param filter_by_source 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论。可分为以下几种：
     *                         <li> {@link StatusesAPI#SRC_FILTER_ALL}
     *                         <li> {@link StatusesAPI#SRC_FILTER_WEIBO}
     *                         <li> {@link StatusesAPI#SRC_FILTER_WEIQUN}
     * @param filter_by_type   原创筛选类型，0：全部微博、1：原创的微博，默认为0。可分为以下几种：
     *                         <li> {@link StatusesAPI#TYPE_FILTER_ALL}
     *                         <li> {@link StatusesAPI#TYPE_FILTER_ORIGAL}
     */
    private void getWeiboMentions(long since_id, long max_id, int count, int page,
                                  int filter_by_author, int filter_by_source, int filter_by_type) {
        BaseObserver<StatusList> observer = new BaseObserver<StatusList>(mContext) {
            @Override
            public void onNext(StatusList value) {
                mStatusList = value;
            }

            @Override
            public void onComplete() {
                mSubView.showWeiboMention(mStatusList);
            }
        };

        mStatusesAPI.mentions(observer, since_id, max_id, count, page, filter_by_author,
                filter_by_source, filter_by_type);
    }

    /**
     * 获取最新的提到登录用户的微博列表
     *
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
     *                         <li> {@link StatusesAPI#AUTHOR_FILTER_ALL}
     *                         <li> {@link StatusesAPI#AUTHOR_FILTER_ATTENTIONS}
     *                         <li> {@link StatusesAPI#AUTHOR_FILTER_STRANGER}
     * @param filter_by_source 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论。可分为以下几种：
     *                         <li> {@link StatusesAPI#SRC_FILTER_ALL}
     *                         <li> {@link StatusesAPI#SRC_FILTER_WEIBO}
     *                         <li> {@link StatusesAPI#SRC_FILTER_WEIQUN}
     * @param filter_by_type   原创筛选类型，0：全部微博、1：原创的微博，默认为0。可分为以下几种：
     *                         <li> {@link StatusesAPI#TYPE_FILTER_ALL}
     *                         <li> {@link StatusesAPI#TYPE_FILTER_ORIGAL}
     */
    @Override
    public void requestWeiboTimeline(int filter_by_author, int filter_by_source, int filter_by_type) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        getWeiboMentions(0, 0, WEIBO_COUNT, WEIBO_PAGE, filter_by_author,
                filter_by_source, filter_by_type);
    }

    /**
     * 获取最新的提到当前登录用户的评论，即@我的评论 若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0
     *
     * @param since_id         若指定此参数，则返回ID小于或等于max_id的评论，默认为0
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的评论，默认为0
     * @param count            单页返回的记录条数，默认为50
     * @param page             返回结果的页码，默认为1
     * @param filter_by_author 作者筛选类型，0：全部，1：我关注的人， 2：陌生人，默认为0
     *                         <li> {@link CommentsAPI#AUTHOR_FILTER_ALL}
     *                         <li> {@link CommentsAPI#AUTHOR_FILTER_ATTENTIONS}
     *                         <li> {@link CommentsAPI#AUTHOR_FILTER_STRANGER}
     * @param filter_by_source 来源筛选类型，0：全部，1：来自微博的评论，2：来自微群的评论，默认为0
     *                         <li> {@link CommentsAPI#SRC_FILTER_ALL}
     *                         <li> {@link CommentsAPI#SRC_FILTER_WEIBO}
     *                         <li> {@link CommentsAPI#SRC_FILTER_WEIQUN}
     */
    private void getCommentMentions(long since_id, long max_id, int count, int page,
                                    int filter_by_author, int filter_by_source) {
        BaseObserver<CommentList> observer = new BaseObserver<CommentList>(mContext) {
            @Override
            public void onNext(CommentList value) {
                mCommentList = value;
            }

            @Override
            public void onComplete() {
                mSubView.showCommentMention(mCommentList);
            }
        };

        mCommentsAPI.mentions(observer, since_id, max_id, count, page, filter_by_author,
                filter_by_source);
    }

    /**
     * 获取最新的提到当前登录用户的评论，即@我的评论 若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0
     *
     * @param filter_by_author 作者筛选类型，0：全部，1：我关注的人， 2：陌生人，默认为0
     *                         <li> {@link CommentsAPI#AUTHOR_FILTER_ALL}
     *                         <li> {@link CommentsAPI#AUTHOR_FILTER_ATTENTIONS}
     *                         <li> {@link CommentsAPI#AUTHOR_FILTER_STRANGER}
     * @param filter_by_source 来源筛选类型，0：全部，1：来自微博的评论，2：来自微群的评论，默认为0
     *                         <li> {@link CommentsAPI#SRC_FILTER_ALL}
     *                         <li> {@link CommentsAPI#SRC_FILTER_WEIBO}
     *                         <li> {@link CommentsAPI#SRC_FILTER_WEIQUN}
     */
    @Override
    public void requestCommentTimeline(int filter_by_author, int filter_by_source) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        getCommentMentions(0, 0, WEIBO_COUNT, WEIBO_PAGE, filter_by_author, filter_by_source);
    }
}
