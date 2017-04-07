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

import java.util.ArrayList;

import cn.zhangls.android.weibo.network.models.FavoriteList;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.network.service.SuggestionsService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 该类封装了推荐接口
 */
public class SuggestionsAPI extends BaseAPI {
    /**
     * access_token
     */
    private String access_token;
    /**
     * StatusesService
     */
    private SuggestionsService mSuggestionsService;

    /**
     * 推荐分类
     */
    public enum USER_CATEGORY {
        DEFAULT, ent, hk_famous, model, cooking, sports, finance, tech, singer, writer, moderator, medium, stockplayer
    }

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public SuggestionsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
        access_token = mAccessToken.getToken();
        mSuggestionsService = mRetrofit.create(SuggestionsService.class);
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
    public void usersHot(Observer<ArrayList<User>> observer, USER_CATEGORY category) {
        mSuggestionsService.userHot(access_token, category.name())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
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
     * 返回系统推荐的热门收藏。
     * 
     * @param count     每页返回结果数，默认20
     * @param page      返回页码，默认1
     */
    public void favoritesHot(Observer<FavoriteList> observer, int count, int page) {
        mSuggestionsService.favoritesHot(access_token, count, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 把某人标识为不感兴趣的人。
     * 
     * @param uid       不感兴趣的用户的UID
     */
    public void notInterested(Observer<User> observer, long uid) {
        mSuggestionsService.notInterested(access_token, uid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }
}
