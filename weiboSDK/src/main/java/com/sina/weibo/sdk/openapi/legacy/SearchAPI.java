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
 
package com.sina.weibo.sdk.openapi.legacy;

import android.content.Context;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.AbsOpenAPI;

/**
 * 该类封装了微博的搜索接口。
 * 详情请参考<a href="http://t.cn/8F1nKH7">搜索接口</a>
 * 
 * @author SINA
 * @date 2014-03-03
 */
public class SearchAPI extends AbsOpenAPI {

    /** 学校类型，1：大学、2：高中、3：中专技校、4：初中、5：小学，默认为1。 */
    public static final int SCHOOL_TYPE_COLLEGE     = 1;
    public static final int SCHOOL_TYPE_SENIOR      = 2;
    public static final int SCHOOL_TYPE_TECHNICAL   = 3;
    public static final int SCHOOL_TYPE_JUNIOR      = 4;
    public static final int SCHOOL_TYPE_PRIMARY     = 5;

    /** 联想类型，0：关注、1：粉丝。 */
    public static final int FRIEND_TYPE_ATTENTIONS  = 0;
    public static final int FRIEND_TYPE_FELLOWS     = 1;

    /** 联想范围，0：只联想关注人、1：只联想关注人的备注、2：全部，默认为2。 */
    public static final int RANGE_ATTENTIONS     = 0;
    public static final int RANGE_ATTENTION_TAGS = 1;
    public static final int RANGE_ALL            = 2;

    public SearchAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    private static final String SERVER_URL_PRIX = API_SERVER + "/search";

    /**
     * 搜索用户时的联想搜索建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding
     * @param count     返回的记录条数，默认为10
     * @param listener  异步请求回调接口
     */
    public void users(String q, int count, RequestListener listener) {
        WeiboParameters params = buildBaseParams(q, count);
        requestAsync(SERVER_URL_PRIX + "/suggestions/users.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 搜索微博时的联想搜索建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding。
     * @param count     返回的记录条数，默认为10。
     * @param listener  异步请求回调接口
     */
    public void statuses(String q, int count, RequestListener listener) {
        WeiboParameters params = buildBaseParams(q, count);
        requestAsync(SERVER_URL_PRIX + "/suggestions/statuses.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 搜索学校时的联想搜索建议。
     * 
     * @param q             搜索的关键字，必须做URLencoding。
     * @param count         返回的记录条数，默认为10。
     * @param schoolType    学校类型，1：大学、2：高中、3：中专技校、4：初中、5：小学，默认为1。可为以下几种： 
     *                      <li> {@link #SCHOOL_TYPE_COLLEGE}
     *                      <li> {@link #SCHOOL_TYPE_SENIOR}
     *                      <li> {@link #SCHOOL_TYPE_TECHNICAL}
     *                      <li> {@link #SCHOOL_TYPE_JUNIOR}
     *                      <li> {@link #SCHOOL_TYPE_PRIMARY}
     * @param listener      异步请求回调接口
     */
    public void schools(String q, int count, int schoolType, RequestListener listener) {
        WeiboParameters params = buildBaseParams(q, count);
        params.put("type", schoolType);
        requestAsync(SERVER_URL_PRIX + "/suggestions/schools.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 搜索公司时的联想搜索建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding
     * @param count     返回的记录条数，默认为10
     * @param listener  异步请求回调接口
     */
    public void companies(String q, int count, RequestListener listener) {
        WeiboParameters params = buildBaseParams(q, count);
        requestAsync(SERVER_URL_PRIX + "/suggestions/companies.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 搜索应用时的联想搜索建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding
     * @param count     返回的记录条数，默认为10
     * @param listener  异步请求回调接口
     */
    public void apps(String q, int count, RequestListener listener) {
        WeiboParameters params = buildBaseParams(q, count);
        requestAsync(SERVER_URL_PRIX + "/suggestions/apps.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * “@”用户时的联想建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding
     * @param count     返回的记录条数，默认为10，粉丝最多1000，关注最多2000
     * @param type      联想类型，0：关注、1：粉丝。可为以下几种：
     *                  <li> {@link #FRIEND_TYPE_ATTENTIONS}
     *                  <li> {@link #FRIEND_TYPE_FELLOWS}
     * @param range     联想范围，0：只联想关注人、1：只联想关注人的备注、2：全部，默认为2。 
     *                  <li> {@link #RANGE_ATTENTIONS}
     *                  <li> {@link #RANGE_ATTENTION_TAGS}
     *                  <li> {@link #RANGE_ALL}
     * @param listener  异步请求回调接口
     */
    public void atUsers(String q, int count, int type, int range, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("q", q);
        params.put("count", count);
        params.put("type", type);
        params.put("range", range);
        requestAsync(SERVER_URL_PRIX + "/suggestions/at_users.json", params, HTTPMETHOD_GET, listener);
    }

    private WeiboParameters buildBaseParams(String q, int count) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("q", q);
        params.put("count", count);
        return params;
    }
}
