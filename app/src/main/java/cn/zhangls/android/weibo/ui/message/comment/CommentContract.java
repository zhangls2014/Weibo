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

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.api.CommentsAPI;
import cn.zhangls.android.weibo.network.models.CommentList;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/25.
 */

interface CommentContract {
    interface Presenter extends BasePresenter {

        /**
         * 获取当前登录用户所发出的评论列表。
         */
        void requestCommentByMe();

        /**
         * 获取当前登录用户所接收到的评论列表。
         *
         * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
         *                   <li>{@link CommentsAPI#AUTHOR_FILTER_ALL}
         *                   <li>{@link CommentsAPI#AUTHOR_FILTER_ATTENTIONS}
         *                   <li>{@link CommentsAPI#AUTHOR_FILTER_STRANGER}
         */
        void requestCommentToMe(int authorType);
    }

    interface View extends BaseView<CommentContract.Presenter> {
        /**
         * 显示@我的评论
         *
         * @param commentList 评论列表
         */
        void showCommentMention(CommentList commentList);
    }
}
