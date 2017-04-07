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

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.FavoritesAPI;
import cn.zhangls.android.weibo.network.api.StatusesAPI;
import cn.zhangls.android.weibo.network.api.SuggestionsAPI;
import cn.zhangls.android.weibo.network.models.FavoriteList;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.utils.ToastUtil;

/**
 * Created by zhangls on 2016/10/31.
 *
 */
class WeiboPresenter extends ParentPresenter<WeiboContract.WeiboView> implements WeiboContract.Presenter {

    /**
     * 微博接口方法对象
     */
    private StatusesAPI mStatusesAPI;
    /**
     * FavoritesAPI
     */
    private FavoritesAPI mFavoritesAPI;
    /**
     * SuggestionsAPI
     */
    private SuggestionsAPI mSuggestionsAPI;
    /**
     * 数据结构体
     */
    private StatusList mStatusList;
    /**
     * 收藏数据结构体
     */
    private FavoriteList mFavoriteList;
    /**
     * 热门收藏数据列表
     */
    private ArrayList<Status> mHotFavorites;

    WeiboPresenter(Context context, @NonNull WeiboContract.WeiboView subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mStatusesAPI = new StatusesAPI(mContext, mAccessToken);
        mFavoritesAPI = new FavoritesAPI(mContext, mAccessToken);
        mSuggestionsAPI = new SuggestionsAPI(mContext, mAccessToken);
    }

    /**
     * 刷新微博
     *
     * @param weiboListType 微博列表类型
     * @param weiboCount 每次获取的微博数
     * @param weiboPage 获取的微博页数
     */
    @Override
    public void requestTimeline(WeiboFragment.WeiboListType weiboListType, int weiboCount, int weiboPage) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        mSubView.onWeiboRefresh();
        switch (weiboListType) {
            case FRIEND:
                mStatusesAPI.friendsTimeline(getStatusObserver(), 0, 0, weiboCount, weiboPage,
                        StatusesAPI.BASE_APP_ALL, StatusesAPI.FEATURE_ALL, StatusesAPI.TRIM_USER_ALL);
                break;
            case PUBLIC:
                mStatusesAPI.publicTimeline(getStatusObserver(), weiboCount, weiboPage, StatusesAPI.BASE_APP_ALL);
                break;
            case MENTION:
                mStatusesAPI.mentions(getStatusObserver(), 0, 0, weiboCount, weiboPage,
                        StatusesAPI.AUTHOR_FILTER_ALL, StatusesAPI.SRC_FILTER_ALL, StatusesAPI.TYPE_FILTER_ALL);
                break;
            case USER:
                mStatusesAPI.userTimeline(getStatusObserver(), Long.parseLong(mAccessToken.getUid()), 0, 0, weiboCount, weiboPage,
                        0, StatusesAPI.FEATURE_ALL, StatusesAPI.TRIM_USER_ALL);
                break;
            case FAVORITE:
                mFavoritesAPI.favorites(getFavoriteObserver(), weiboCount, weiboPage);
                break;
            case HOT_FAVORITE:
                mSuggestionsAPI.favoritesHot(getFavoriteObserver(), weiboCount, weiboPage);
                break;
        }
    }

    /**
     * 刷新用户微博
     *
     * @param userId     用户 ID
     * @param weiboCount 每次获取的微博数
     * @param weiboPage  获取的微博页数
     */
    @Override
    public void requestUserTimeline(long userId, int weiboCount, int weiboPage) {
        if (!mAccessToken.isSessionValid()) {
            ToastUtil.showLongToast(mContext, "授权信息拉取失败，请重新登录");
            return;
        }
        mStatusesAPI.userTimeline(getStatusObserver(), userId, 0, 0, weiboCount, weiboPage,
                0, StatusesAPI.FEATURE_ALL, StatusesAPI.TRIM_USER_ALL);
    }

    /**
     * 获取 observer
     */
    private BaseObserver<StatusList> getStatusObserver() {
        return new BaseObserver<StatusList>(mContext) {
            @Override
            public void onNext(StatusList value) {
                mStatusList = value;
            }

            @Override
            public void onComplete() {
                mSubView.refreshCompleted(mStatusList);
                mSubView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSubView.loadError();
            }
        };
    }

    /**
     * 获取 observer
     */
    private BaseObserver<FavoriteList> getFavoriteObserver() {
        return new BaseObserver<FavoriteList>(mContext) {
            @Override
            public void onNext(FavoriteList value) {
                mFavoriteList = value;
            }

            @Override
            public void onComplete() {
                mSubView.loadFavorites(mFavoriteList);
                mSubView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSubView.loadError();
            }
        };
    }

    /**
     * 获取 observer
     */
    private BaseObserver<ArrayList<Status>> getHotFavoritesObserver() {
        return new BaseObserver<ArrayList<Status>>(mContext) {
            @Override
            public void onNext(ArrayList<Status> value) {
                mHotFavorites = value;
            }

            @Override
            public void onComplete() {
                mSubView.loadHotFavorites(mHotFavorites);
                mSubView.stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSubView.loadError();
            }
        };
    }
}