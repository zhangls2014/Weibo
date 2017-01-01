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

import cn.zhangls.android.weibo.network.models.FriendsList;
import cn.zhangls.android.weibo.network.models.GroupList;
import cn.zhangls.android.weibo.network.models.StatusList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangls on 2016/11/15.
 * <p>
 * 用户关系方法
 */
public interface FriendsService {
    /**
     * 获取用户的关注列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param uid          需要查询的用户UID
     * @param screen_name  需要查询的用户昵称
     * @param count        单页返回的记录条数，默认为50，最大不超过200
     * @param cursor       返回结果的游标，
     *                     下一页用返回值里的next_cursor，
     *                     上一页用previous_cursor，
     *                     默认为0
     * @param trim_status  返回值中user字段中的status字段开关，
     *                     0：返回完整status字段、
     *                     1：status字段仅返回status_id，
     *                     默认为1
     * @return 用户的关注列表
     */
    @GET("/2/friendships/friends.json")
    Observable<FriendsList> getFriendsList(
            @NonNull @Query("access_token") String access_token,
            @Query("uid") long uid,
            @Query("screen_name") String screen_name,
            @Query("count") int count,
            @Query("cursor") int cursor,
            @Query("trim_status") int trim_status
    );

    /**
     * 获取当前登陆用户好友分组列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @return 用户好友分组列表
     */
    @GET("/2/friendships/groups.json")
    Observable<GroupList> getGroupList(
            @Query("access_token") String access_token
    );

    /**
     * 获取当前登录用户某一好友分组的微博列表。
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param list_id      需要查询的好友分组ID，建议使用返回值里的idstr，当查询的为私有分组时，则当前登录用户必须为其所有者
     * @param since_id     若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id       若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count        单页返回的记录条数，默认为50
     * @param page         返回结果的页码，默认为1
     * @param base_app     是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     * @param feature      过滤类型ID，0：全部，1：原创， 2：图片，3：视频，4：音乐
     */
    @GET("/2/friendships/groups/timeline.json")
    Observable<StatusList> getGroupTimeline(
            @Query("access_token") @NonNull String access_token,
            @Query("list_id") long list_id,
            @Query("since_id") long since_id,
            @Query("max_id") long max_id,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") boolean base_app,
            @Query("feature") int feature
    );
}
