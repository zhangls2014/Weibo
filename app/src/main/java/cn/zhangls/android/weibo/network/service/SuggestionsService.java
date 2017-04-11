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

import android.support.annotation.NonNull;

import java.util.ArrayList;

import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/4.
 * <p>
 * 推荐接口
 */

public interface SuggestionsService {
    /**
     * 返回系统推荐的热门用户列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param category     @param category  推荐分类，返回某一类别的推荐用户
     */
    @GET("/2/suggestions/users/hot.json")
    Observable<ArrayList<User>> userHot(
            @Query("access_token") @NonNull String access_token,
            @Query("category") String category
    );

    /**
     * 返回系统推荐的热门收藏。
     *
     * @param count 每页返回结果数，默认20
     * @param page  返回页码，默认1
     */
    @GET("/2/suggestions/favorites/hot.json")
    Observable<ArrayList<Status>> favoritesHot(
            @Query("access_token") @NonNull String access_token,
            @Query("count") int count,
            @Query("page") int page
    );

    /**
     * 获取用户可能感兴趣的人。
     *
     * @param count 单页返回的记录条数，默认为10
     * @param page  返回结果的页码，默认为1
     */
    @GET("/2/suggestions/users/may_interested.json")
    Observable<ArrayList<Status>> mayInterested(
            @Query("access_token") @NonNull String access_token,
            @Query("count") int count,
            @Query("page") int page
    );

    /**
     * 把某人标识为不感兴趣的人。
     *
     * @param uid 不感兴趣的用户的UID
     */
    @POST("/2/suggestions/users/not_interested.json")
    Observable<User> notInterested(
            @Field("access_token") @NonNull String access_token,
            @Field("uid") long uid
    );
}
