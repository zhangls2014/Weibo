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

import cn.zhangls.android.weibo.network.models.Favorite;
import cn.zhangls.android.weibo.network.models.FavoriteList;
import cn.zhangls.android.weibo.network.service.FavoritesService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 此类封装了收藏的接口
 */
public class FavoritesAPI extends BaseAPI {

    /**
     * access_token
     */
    private String access_token;
    /**
     * FavoritesService
     */
    private FavoritesService mFavoritesService;

    public FavoritesAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
        access_token = mAccessToken.getToken();
        mFavoritesService = mRetrofit.create(FavoritesService.class);
    }

    /**
     * 获取当前登录用户的收藏列表。
     * 
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void favorites(Observer<FavoriteList> observer, int count, int page) {
        mFavoritesService.favorites(access_token, count, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取当前用户的收藏列表的ID。
     * 
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void ids(int count, int page) {
        // TODO "/ids.json"
    }

    /**
     * 根据收藏ID获取指定的收藏信息。
     * 
     * @param id        需要查询的收藏ID
     */
    public void show(Observer<Favorite> observer, long id) {
        mFavoritesService.show(access_token, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据标签获取当前登录用户该标签下的收藏列表。
     * 
     * @param tid       需要查询的标签ID
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void byTags(long tid, int count, int page) {
        // TODO "/by_tags.json"
    }

    /**
     * 获取当前登录用户的收藏标签列表。
     * 
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void tags(int count, int page) {
        // TODO "/tags.json"
    }

    /**
     * 获取当前用户某个标签下的收藏列表的ID。
     * 
     * @param tid       需要查询的标签ID。
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void byTagsIds(long tid, int count, int page) {
        // TODO "/by_tags/ids.json"
    }

    /**
     * 添加一条微博到收藏里。
     * 
     * @param id        要收藏的微博ID
     */
    public void create(Observer<Favorite> observer, long id) {
        mFavoritesService.create(access_token, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 取消收藏一条微博。
     * 
     * @param id        要取消收藏的微博ID。
     */
    public void destroy(Observer<Favorite> observer, long id) {
        mFavoritesService.destroy(access_token, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 更新一条收藏的收藏标签。
     * 
     * @param id        需要更新的收藏ID
     * @param tags      需要更新的标签内容，最多不超过2条
     */
    public void tagsUpdate(long id, String[] tags) {
        // TODO "/tags/update.json"
    }

    /**
     * 更新当前登录用户所有收藏下的指定标签。
     * 
     * @param id        需要更新的标签ID
     * @param tag       需要更新的标签内容
     */
    public void tagsUpdateBatch(long id, String tag) {
        // TODO "/tags/update_batch.json"
    }

    /**
     * 删除当前登录用户所有收藏下的指定标签。
     * 
     * @param tid       需要删除的标签ID
     */
    public void tagsDestroyBatch(long tid) {
        // TODO "/tags/destroy_batch.json"
    }
}
