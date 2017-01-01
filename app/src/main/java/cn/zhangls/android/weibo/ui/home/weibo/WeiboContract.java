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

package cn.zhangls.android.weibo.ui.home.weibo;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.models.StatusList;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

interface WeiboContract {
    interface Presenter extends BasePresenter {
        /**
         * 刷新微博
         */
        void requestFriendsTimeline();

        /**
         * 获取分组列表
         */
        void requestGroupList();

        /**
         * 获取分组微博
         */
        void requestGroupTimeline();

    }

    interface WeiboView extends BaseView<Presenter> {
        /**
         * 刷新微博
         */
        void onWeiboRefresh();

        /**
         * 完成数据加载
         *
         * @param statusList 返回数据
         */
        void refreshCompleted(StatusList statusList);

        /**
         * 停止刷新动画
         */
        void stopRefresh();
    }
}
