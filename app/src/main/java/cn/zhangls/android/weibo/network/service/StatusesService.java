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

import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 微博读取请求类
 */

public interface StatusesService {

    /**
     * 获取最新的公共微博
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     */
    @GET("/2/statuses/public_timeline.json")
    Observable<StatusList> getPublicTimeline(
            @Query("access_token") @NonNull String access_token,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app);

    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     *
     * @param access_token 用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count 单页返回的记录条数，最大不超过100，默认为20。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trim_user 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     */
    @GET("/2/statuses/friends_timeline.json")
    Observable<StatusList> getFriendsTimeline(
            @Query("access_token") @NonNull String access_token,
            @Query("since_id") long since_id,
            @Query("max_id") long max_id,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app,
            @Query("feature") int feature,
            @Query("trim_user") int trim_user);

    /**
     * 获取某个用户最新发表的微博列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param uid          需要查询的用户ID
     * @param since_id     若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id       若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count        单页返回的记录条数，最大不超过100，超过100以100处理，默认为20
     * @param page         返回结果的页码，默认为1
     * @param base_app     是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @param feature      过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     * @param trim_user    返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    @GET("/2/statuses/user_timeline.json")
    Observable<StatusList> getUserTimeline(
            @Query("access_token") String access_token,
            @Query("uid") long uid,
            @Query("since_id") long since_id,
            @Query("max_id") long max_id,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app,
            @Query("feature") int feature,
            @Query("trim_user") int trim_user
    );

    /**
     * 获取某个用户最新发表的微博列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param screen_name  需要查询的用户昵称
     * @param since_id     若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id       若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count        单页返回的记录条数，最大不超过100，超过100以100处理，默认为20
     * @param page         返回结果的页码，默认为1
     * @param base_app     是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @param feature      过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     * @param trim_user    返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    @GET("/2/statuses/user_timeline.json")
    Observable<StatusList> getUserTimeline(
            @Query("access_token") String access_token,
            @Query("screen_name") String screen_name,
            @Query("since_id") long since_id,
            @Query("max_id") long max_id,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app,
            @Query("feature") int feature,
            @Query("trim_user") int trim_user
    );

    /**
     * 转发一条微博
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param id           要转发的微博ID
     * @param status       添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”
     * @param is_comment   是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
     * @param rip          开发者上报的操作用户真实IP，形如：211.156.0.1
     */
    @POST("/2/statuses/repost.json")
    Observable<Status> repostStatus(
            @Field("access_token") String access_token,
            @Field("id") long id,
            @Field("status") String status,
            @Field("is_comment") int is_comment,
            @Field("rip") String rip
    );
}
