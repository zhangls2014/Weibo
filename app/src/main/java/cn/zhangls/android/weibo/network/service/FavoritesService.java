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

package cn.zhangls.android.weibo.network.service;

import cn.zhangls.android.weibo.network.models.Favorite;
import cn.zhangls.android.weibo.network.models.FavoriteList;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/2.
 * <p>
 * 收藏接口方法
 */

public interface FavoritesService {
    /**
     * 获取当前登录用户的收藏列表。
     *
     * @param count 单页返回的记录条数，默认为50
     * @param page  返回结果的页码，默认为1
     */
    @GET("/2/favorites.json")
    Observable<FavoriteList> favorites(
            @Query("access_token") String access_token,
            @Query("count") int count,
            @Query("page") int page
    );

    /**
     * 根据标签获取当前登录用户该标签下的收藏列表。
     *
     * @param id 需要查询的标签ID
     */
    @GET("/2/favorites/show.json")
    Observable<Favorite> show(
            @Query("access_token") String access_token,
            @Query("id") long id
    );

    @FormUrlEncoded
    @POST("/2/favorites/create.json")
    Observable<Favorite> create(
            @Field("access_token") String access_token,
            @Field("id") long id
    );

    @FormUrlEncoded
    @POST("/2/favorites/destroy.json")
    Observable<Favorite> destroy(
            @Field("access_token") String access_token,
            @Field("id") long id
    );
}
