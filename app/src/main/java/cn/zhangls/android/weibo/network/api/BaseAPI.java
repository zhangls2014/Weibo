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

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangls on 2016/10/21.
 * <p>
 * 对网络请求进行封装
 */
public abstract class BaseAPI {

    /**
     * 访问微博服务接口的地址
     */
    private static final String API_SERVER = "https://api.weibo.com";
    /**
     * 默认请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 5;
    /**
     * 封装授权信息
     */
    protected Oauth2AccessToken mAccessToken;
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * Retrofit
     */
    protected Retrofit mRetrofit;

    protected long uid;
    protected String access_token;
    protected String phone_num;
    protected String refresh_token;
    protected long expires_time;


    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public BaseAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        mContext = context;
        mAccessToken = accessToken;
        getTokenInfo();
        setRetrofit();
    }

    private void getTokenInfo() {
        access_token = mAccessToken.getToken();
        uid = Long.parseLong(mAccessToken.getUid());
        phone_num = mAccessToken.getPhoneNum();
        expires_time = mAccessToken.getExpiresTime();
        refresh_token = mAccessToken.getRefreshToken();
    }

    private void setRetrofit() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_SERVER)
                .build();
    }

}
