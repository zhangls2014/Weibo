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
import android.util.SparseArray;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * 该类封装了用户接口。
 * 详情请参考<a href="http://t.cn/8F1n1eF">用户接口</a>
 * 
 * @author SINA
 * @since 2014-03-03
 */
public class UsersAPI extends AbsOpenAPI {

    private static final int READ_USER           = 0;
    private static final int READ_USER_BY_DOMAIN = 1;
    private static final int READ_USER_COUNT     = 2;

    private static final String API_BASE_URL = API_SERVER + "/users";

    private static final SparseArray<String> sAPIList = new SparseArray<String>();
    static {
        sAPIList.put(READ_USER,           API_BASE_URL + "/show.json");
        sAPIList.put(READ_USER_BY_DOMAIN, API_BASE_URL + "/domain_show.json");
        sAPIList.put(READ_USER_COUNT,     API_BASE_URL + "/counts.json");
    }

    public UsersAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    /**
     * 根据用户ID获取用户信息。
     * 
     * @param uid      需要查询的用户ID
     * @param listener 异步请求回调接口
     */
    public void show(long uid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("uid", uid);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * 根据用户昵称获取用户信息。
     * 
     * @param screen_name 需要查询的用户昵称
     * @param listener    异步请求回调接口
     */
    public void show(String screen_name, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("screen_name", screen_name);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * 通过个性化域名获取用户资料以及用户最新的一条微博。
     * 
     * @param domain   需要查询的个性化域名（请注意：是http://weibo.com/xxx后面的xxx部分）
     * @param listener 异步请求回调接口
     */
    public void domainShow(String domain, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("domain", domain);
        requestAsync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * 批量获取用户的粉丝数、关注数、微博数。
     * 
     * @param uids     需要获取数据的用户UID，多个之间用逗号分隔，最多不超过100个
     * @param listener 异步请求回调接口
     */
    public void counts(long[] uids, RequestListener listener) {
        WeiboParameters params = buildCountsParams(uids);
        requestAsync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * -----------------------------------------------------------------------
     * 请注意：以下方法匀均同步方法。如果开发者有自己的异步请求机制，请使用该函数。
     * -----------------------------------------------------------------------
     */
    
    /**
     * @see #show(long, RequestListener)
     */
    public String showSync(long uid) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("uid", uid);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
     * @see #show(String, RequestListener)
     */
    public String showSync(String screen_name) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("screen_name", screen_name);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
     * @see #domainShow(String, RequestListener)
     */
    public String domainShowSync(String domain) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("domain", domain);
        return requestSync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET);
    }

    /**
     * @see #counts(long[], RequestListener)
     */
    public String countsSync(long[] uids) {
        WeiboParameters params = buildCountsParams(uids);
        return requestSync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET);
    }

    private WeiboParameters buildCountsParams(long[] uids) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        StringBuilder strb = new StringBuilder();
        for (long cid : uids) {
            strb.append(cid).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        params.put("uids", strb.toString());
        return params;
    }
}
