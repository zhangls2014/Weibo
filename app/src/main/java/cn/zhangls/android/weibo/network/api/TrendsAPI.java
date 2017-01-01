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

/**
 * Created by zhangls on 2016/10/21.
 *
 * 该类封装了话题接口
 */
public class TrendsAPI extends BaseAPI {


    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public TrendsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 获取某人的话题列表。
     * 
     * @param uid       需要获取话题的用户的UID
     * @param count     单页返回的记录条数，默认为10
     * @param page      返回结果的页码，默认为1
     */
    public void trends(long uid, int count, int page) {
        // TODO  ".json"
    }

    /**
     * 判断当前用户是否关注某话题。
     * 
     * @param trend_name    话题关键字
     */
    public void isFollow(String trend_name) {
        // TODO  "/is_follow.json"
    }

    /**
     * 返回最近一小时内的热门话题。
     *
     * @param base_app  是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    public void hourly(int base_app) {
        // TODO  "/hourly.json"
    }

    /**
     * 返回最近一天内的热门话题。
     *
     * @param base_app  是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    public void daily(int base_app) {
        // TODO  "/daily.json"
    }

    /**
     * 返回最近一周内的热门话题。
     *
     * @param base_app  是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    public void weekly(int base_app) {
        // TODO  "/weekly.json"
    }

    /**
     * 关注某话题。
     * 
     * @param trend_name    要关注的话题关键词
     */
    public void follow(String trend_name) {
        // TODO "/follow.json"
    }

    /**
     * 取消对某话题的关注。
     * 
     * @param trend_id  要取消关注的话题ID
     */
    public void destroy(long trend_id) {
        // TODO "/destroy.json"
    }
}
