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

package cn.zhangls.android.weibo.ui.home.discovery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.SuggestionsAPI;
import cn.zhangls.android.weibo.network.api.TrendsAPI;
import cn.zhangls.android.weibo.network.models.TrendHourly;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/14.
 *
 */

class DiscoveryPresenter extends ParentPresenter<DiscoveryContract.FindView> implements DiscoveryContract.Presenter {

    private static final String TAG = "DiscoveryPresenter";

    /**
     * TrendsAPI
     */
    private TrendsAPI mTrendsAPI;
    /**
     * SuggestionsAPI
     */
    private SuggestionsAPI mSuggestionsAPI;
    /**
     * 热门话题数据结构体
     */
    private TrendHourly mTrendHourly;

    DiscoveryPresenter(Context context, @NonNull DiscoveryContract.FindView subView) {
        super(context, subView);
    }


    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mTrendsAPI = new TrendsAPI(mContext, mAccessToken);
        mSuggestionsAPI = new SuggestionsAPI(mContext, mAccessToken);
    }


    /**
     * 返回最近一小时内的热门话题。
     *
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    @Override
    public void getTrendHourly(int base_app) {
        BaseObserver<TrendHourly> observer = new BaseObserver<TrendHourly>(mContext) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }

            @Override
            public void onNext(TrendHourly value) {
                mTrendHourly = value;
                Log.d(TAG, "onNext: ===========" + value.getAsIf());
            }

            @Override
            public void onComplete() {
                mSubView.showHotTrend(mTrendHourly);
            }
        };

        mTrendsAPI.hourly(observer, base_app);
    }
}
