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

package cn.zhangls.android.weibo.network.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.network.models.TrendHourly;
import cn.zhangls.android.weibo.network.service.TrendsService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 该类封装了话题接口
 */
public class TrendsAPI extends BaseAPI {

    /**
     * access_token
     */
    private String access_token;
    /**
     * TrendsService
     */
    private TrendsService mTrendsService;


    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public TrendsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
        access_token = mAccessToken.getToken();
        mTrendsService = mRetrofit.create(TrendsService.class);
    }

    /**
     * 返回最近一小时内的热门话题。
     *
     * @param base_app  是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    public void hourly(Observer<TrendHourly> observer, int base_app) {
        mTrendsService.hourly(access_token, base_app)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }
}
