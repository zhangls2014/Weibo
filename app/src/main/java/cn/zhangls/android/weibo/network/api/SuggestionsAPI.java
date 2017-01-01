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
 * 该类封装了推荐接口
 */
public class SuggestionsAPI extends BaseAPI {

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public SuggestionsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /** 推荐分类 */
    public enum USER_CATEGORY {
        DEFAULT, ent, hk_famous, model, cooking, sports, finance, tech, singer, writer, moderator, medium, stockplayer
    }

    /** 微博精选分类 */
    public enum STATUSES_TYPE {
        ENTERTAINMENT, FUNNY, BEAUTY, VIDEO, CONSTELLATION, LOVELY, FASHION, CARS, CATE, MUSIC
    }

    /**
     * 返回系统推荐的热门用户列表。
     * 
     * @param category  推荐分类，返回某一类别的推荐用户，默认为default，如果不在以下分类中，返回空列表。
     *                  default：人气关注     ent：影视名星        hk_famous：港台名人   model：模特
     *                  cooking：美食&健康   sports：体育名人  finance：商界名人       tech：IT互联网
     *                  singer：歌手               writer：作家         moderator：主持人       medium：媒体总编 
     *                  stockplayer：炒股高手
     */
    public void usersHot(USER_CATEGORY category) {
        // TODO "/users/hot.json"
    }

    /**
     * 获取用户可能感兴趣的人。
     * 
     * @param count     单页返回的记录条数，默认为10
     * @param page      返回结果的页码，默认为1
     */
    public void mayInterested(int count, int page) {
        // TODO "/users/may_interested.json"
    }

    /**
     * 根据一段微博正文推荐相关微博用户。
     * 
     * @param content   微博正文内容
     * @param num       返回结果数目，默认为10
     */
    public void byStatus(String content, int num) {
        // TODO "/users/may_interested.json"
    }

    /**
     * 获取微博精选推荐。
     * 
     * @param type      微博精选分类，1：娱乐、2：搞笑、3：美女、4：视频、5：星座、6：各种萌、7：时尚、8：名车、9：美食、10：音乐
     * @param is_pic    是否返回图片精选微博，false：全部、true：图片微博
     * @param count     单页返回的记录条数，默认为20
     * @param page      返回结果的页码，默认为1
     */
    public void statusesHot(STATUSES_TYPE type, boolean is_pic, int count, int page) {
        // TODO  "/statuses/hot.json"
    }

    /**
     * 返回系统推荐的热门收藏。
     * 
     * @param count     每页返回结果数，默认20
     * @param page      返回页码，默认1
     */
    public void favoritesHot(int count, int page) {
        // TODO "/favorites/hot.json"
    }

    /**
     * 把某人标识为不感兴趣的人。
     * 
     * @param uid       不感兴趣的用户的UID
     */
    public void notInterested(long uid) {
        // TODO "/users/not_interested.json"
    }
}
