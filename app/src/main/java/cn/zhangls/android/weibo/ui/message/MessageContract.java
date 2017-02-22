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

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.models.StatusList;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/20.
 * <p>
 * MessageActivity Contract
 */

interface MessageContract {
    interface Presenter extends BasePresenter {
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
        void requestWeiboTimeline(int filter_by_author, int filter_by_source, int filter_by_type);

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
        void requestCommentTimeline(int filter_by_author, int filter_by_source);
    }

    interface View extends BaseView<MessageContract.Presenter> {
        /**
         * 显示@我微博信息
         *
         * @param statusList 微博列表
         */
        void showWeiboMention(StatusList statusList);

        /**
         * 显示@我的评论
         *
         * @param commentList 评论列表
         */
        void showCommentMention(CommentList commentList);
    }
}
