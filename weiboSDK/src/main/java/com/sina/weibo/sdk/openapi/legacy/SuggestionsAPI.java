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
 * 该类封装了推荐接口。
 * 详情请参考<a href="http://t.cn/8F1nOVu">推荐接口</a>
 * 
 * @author SINA
 * @date 2014-03-03
 */
public class SuggestionsAPI extends AbsOpenAPI {

    /** 推荐分类 */
    public enum USER_CATEGORY {
        DEFAULT, ent, hk_famous, model, cooking, sports, finance, tech, singer, writer, moderator, medium, stockplayer
    }

    /** 微博精选分类 */
    public enum STATUSES_TYPE {
        ENTERTAINMENT, FUNNY, BEAUTY, VIDEO, CONSTELLATION, LOVELY, FASHION, CARS, CATE, MUSIC
    }

    public SuggestionsAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    private static final String SERVER_URL_PRIX = API_SERVER + "/suggestions";

    /**
     * 返回系统推荐的热门用户列表。
     * 
     * @param category  推荐分类，返回某一类别的推荐用户，默认为default，如果不在以下分类中，返回空列表。
     *                  default：人气关注     ent：影视名星        hk_famous：港台名人   model：模特
     *                  cooking：美食&健康   sports：体育名人  finance：商界名人       tech：IT互联网
     *                  singer：歌手               writer：作家         moderator：主持人       medium：媒体总编 
     *                  stockplayer：炒股高手
     * @param listener  异步请求回调接口
     */
    public void usersHot(USER_CATEGORY category, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("category", category.name());
        requestAsync(SERVER_URL_PRIX + "/users/hot.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 获取用户可能感兴趣的人。
     * 
     * @param count     单页返回的记录条数，默认为10
     * @param page      返回结果的页码，默认为1
     * @param listener  异步请求回调接口
     */
    public void mayInterested(int count, int page, RequestListener listener) {
        WeiboParameters params = builderCountPage(count, page);
        requestAsync(SERVER_URL_PRIX + "/users/may_interested.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 根据一段微博正文推荐相关微博用户。
     * 
     * @param content   微博正文内容
     * @param num       返回结果数目，默认为10
     * @param listener  异步请求回调接口
     */
    public void byStatus(String content, int num, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("content", content);
        params.put("num", num);
        requestAsync(SERVER_URL_PRIX + "/users/may_interested.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 获取微博精选推荐。
     * 
     * @param type      微博精选分类，1：娱乐、2：搞笑、3：美女、4：视频、5：星座、6：各种萌、7：时尚、8：名车、9：美食、10：音乐
     * @param is_pic    是否返回图片精选微博，false：全部、true：图片微博
     * @param count     单页返回的记录条数，默认为20
     * @param page      返回结果的页码，默认为1
     * @param listener  异步请求回调接口
     */
    public void statusesHot(STATUSES_TYPE type, boolean is_pic, int count, int page, RequestListener listener) {
        WeiboParameters params = builderCountPage(count, page);
        params.put("type", type.ordinal() + 1);
        params.put("is_pic", is_pic ? 1 : 0);
        requestAsync(SERVER_URL_PRIX + "/statuses/hot.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 返回系统推荐的热门收藏。
     * 
     * @param count     每页返回结果数，默认20
     * @param page      返回页码，默认1
     * @param listener  异步请求回调接口
     */
    public void favoritesHot(int count, int page, RequestListener listener) {
        WeiboParameters params = builderCountPage(count, page);
        requestAsync(SERVER_URL_PRIX + "/favorites/hot.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 把某人标识为不感兴趣的人。
     * 
     * @param uid       不感兴趣的用户的UID
     * @param listener  异步请求回调接口
     */
    public void notInterested(long uid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("uid", uid);
        requestAsync(SERVER_URL_PRIX + "/users/not_interested.json", params, HTTPMETHOD_POST, listener);
    }
    
    private WeiboParameters builderCountPage(int count, int page) {
        WeiboParameters params = new WeiboParameters(mAppKey);
        params.put("count", count);
        params.put("page", page);
        return params;
    }
}
