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

package cn.zhangls.android.weibo.ui.weibo;

import java.util.ArrayList;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.models.FavoriteList;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;

/**
 * Created by zhangls on 2016/10/31.
 *
 */

interface WeiboContract {
    interface Presenter extends BasePresenter {
        /**
         * 刷新微博
         *
         * @param weiboListType 微博列表类型
         * @param weiboCount 每次获取的微博数
         * @param weiboPage 获取的微博页数
         */
        void requestTimeline(WeiboFragment.WeiboListType weiboListType, int weiboCount, int weiboPage);

        /**
         * 刷新用户微博
         *
         * @param userId     用户 ID
         * @param weiboCount 每次获取的微博数
         * @param weiboPage  获取的微博页数
         */
        void requestUserTimeline(long userId, int weiboCount, int weiboPage);
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
         * 加载收藏数据
         *
         * @param favoriteList 收藏数据
         */
        void loadFavorites(FavoriteList favoriteList);

        /**
         * 加载热门收藏数据
         *
         * @param hotFavorites 热门收藏数据
         */
        void loadHotFavorites(ArrayList<Status> hotFavorites);

        /**
         * 加载出错，显示 Error 页面
         */
        void loadError();

        /**
         * 停止刷新动画
         */
        void stopRefresh();
    }
}
