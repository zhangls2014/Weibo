/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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

package com.sina.weibo.sdk.openapi;

import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.LogUtil;

/**
 * 微博 OpenAPI 的基类，每个接口类都继承了此抽象类。
 * 
 * @author SINA
 * @since 2013-11-05
 */
public abstract class AbsOpenAPI {
    /** 访问微博服务接口的地址 */
    protected static final String API_SERVER       = "https://api.weibo.com/2";
    /** POST 请求方式 */
    protected static final String HTTPMETHOD_POST  = "POST";
    /** GET 请求方式 */
    protected static final String HTTPMETHOD_GET   = "GET";
    private static final String TAG = AbsOpenAPI.class.getName();
    /** HTTP 参数 */
    private static final String KEY_ACCESS_TOKEN = "access_token";
    protected String mAppKey;
    /**
     * 当前的 Token
     */
    private Oauth2AccessToken mAccessToken;
    private Context mContext;
    
    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param accessToken 访问令牌
     */
    public AbsOpenAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        mContext = context;
        mAppKey = appKey;
        mAccessToken = accessToken;
    }

    /**
     * HTTP 异步请求。
     * 
     * @param url        请求的地址
     * @param params     请求的参数
     * @param httpMethod 请求方法
     * @param listener   请求后的回调接口
     */
    protected void requestAsync(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        if (null == mAccessToken
                || TextUtils.isEmpty(url)
                || null == params
                || TextUtils.isEmpty(httpMethod)
                || null == listener) {
            LogUtil.e(TAG, "Argument error!");
            return;
        }
        
        params.put(KEY_ACCESS_TOKEN, mAccessToken.getToken());
        new AsyncWeiboRunner(mContext).requestAsync(url, params, httpMethod, listener);
    }
    
    /**
     * HTTP 同步请求。
     * 
     * @param url        请求的地址
     * @param params     请求的参数
     * @param httpMethod 请求方法
     * 
     * @return 同步请求后，服务器返回的字符串。
     */
    protected String requestSync(String url, WeiboParameters params, String httpMethod) {
        if (null == mAccessToken
                || TextUtils.isEmpty(url)
                || null == params
                || TextUtils.isEmpty(httpMethod)) {
            LogUtil.e(TAG, "Argument error!");
            return "";
        }
        
        params.put(KEY_ACCESS_TOKEN, mAccessToken.getToken());
        return new AsyncWeiboRunner(mContext).request(url, params, httpMethod);
    }
}
