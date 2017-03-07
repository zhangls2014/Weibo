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
 * 此类封装了关系的接口
 */
public class FriendshipsAPI extends BaseAPI {

    public FriendshipsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 获取用户的关注列表。
     * 
     * @param uid           需要查询的用户UID
     * @param count         单页返回的记录条数，默认为50，最大不超过200
     * @param cursor        返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @param trim_status   返回值中user字段中的status字段开关，false：返回完整status字段、true：status字段仅返回status_id，默认为true
     */
    public void friends(long uid, int count, int cursor, boolean trim_status) {
        // TODO "/friends.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 获取用户的关注列表。
     * 
     * @param screen_name   需要查询的用户昵称
     * @param count         单页返回的记录条数，默认为50，最大不超过200
     * @param cursor        返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @param trim_status   返回值中user字段中的status字段开关，false：返回完整status字段、true：status字段仅返回status_id，默认为true
     */
    public void friends(String screen_name, int count, int cursor, boolean trim_status) {
        // TODO "/friends.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 获取两个用户之间的共同关注人列表。
     * 
     * @param uid       需要获取共同关注关系的用户UID
     * @param suid      需要获取共同关注关系的用户UID，默认为当前登录用户
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     * @param trim_status 返回值中user字段中的status字段开关，false：返回完整status字段、true：status字段仅返回status_id，默认为true
     */
    public void inCommon(long uid, long suid, int count, int page, boolean trim_status) {
        // TODO "/friends/in_common.json", params, HTTPMETHOD_GET, listener);
    }

    /**
     * 获取用户的双向关注列表，即互粉列表。
     * 
     * @param uid       需要获取双向关注列表的用户UID
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void bilateral(long uid, int count, int page) {
        // TODO "/friends/bilateral.json"
    }

    /**
     * 获取用户关注的用户UID列表。
     * 
     * @param uid       需要查询的用户UID
     * @param count     单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor    返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     */
    public void friendsIds(long uid, int count, int cursor) {
        // TODO "/friends/ids.json"
    }

    /**
     * 获取用户的粉丝列表(最多返回5000条数据)。
     * 
     * @param uid       需要查询的用户UID
     * @param count     单页返回的记录条数，默认为50，最大不超过200
     * @param cursor    返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @param trim_status 返回值中user字段中的status字段开关，false：返回完整status字段、true：status字段仅返回status_id，默认为false
     */
    public void followers(long uid, int count, int cursor, boolean trim_status) {
        // TODO "/followers.json"
    }

    /**
     * 获取用户的粉丝列表(最多返回5000条数据)。
     * 
     * @param screen_name   需要查询的用户昵称
     * @param count         单页返回的记录条数，默认为50，最大不超过200
     * @param cursor        返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @param trim_status   返回值中user字段中的status字段开关，false：返回完整status字段、true：status字段仅返回status_id，默认为false
     */
    public void followers(String screen_name, int count, int cursor, boolean trim_status) {
        // TODO "/followers.json"
    }

    /**
     * 获取用户粉丝的用户UID列表。
     * 
     * @param uid       需要查询的用户UID
     * @param count     单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor    返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     */
    public void followersIds(long uid, int count, int cursor) {
        // TODO "/followers/ids.json"
    }

    /**
     * 获取用户粉丝的用户UID列表。
     * 
     * @param screen_name   需要查询的用户昵称
     * @param count         单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor        返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     */
    public void followersIds(String screen_name, int count, int cursor) {
        // TODO "/followers/ids.json"
    }

    /**
     * 获取用户的活跃粉丝列表。
     * 
     * @param uid       需要查询的用户UID
     * @param count     返回的记录条数，默认为20，最大不超过200
     */
    public void followersActive(long uid, int count) {
        // TODO "/followers/active.json"
    }

    /**
     * 获取当前登录用户的关注人中又关注了指定用户的用户列表。
     * 
     * @param uid       指定的关注目标用户UID
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     */
    public void chainFollowers(long uid, int count, int page) {
        // TODO "/friends_chain/followers.json"
    }

    /**
     * 获取两个用户之间的详细关注关系情况。
     * 
     * @param source_id     源用户的UID
     * @param target_id     目标用户的UID
     */
    public void show(long source_id, long target_id) {
        // TODO "/show.json"
    }

    /**
     * 获取两个用户之间的详细关注关系情况。
     * 
     * @param source_id             源用户的UID
     * @param target_screen_name    目标用户的微博昵称
     */
    public void show(long source_id, String target_screen_name) {
        // TODO "/show.json"
    }

    /**
     * 获取两个用户之间的详细关注关系情况。
     * 
     * @param source_screen_name    源用户的微博昵称
     * @param target_id             目标用户的UID
     */
    public void show(String source_screen_name, long target_id) {
        // TODO "/show.json"
    }

    /**
     * 获取两个用户之间的详细关注关系情况。
     * 
     * @param source_screen_name 源用户的微博昵称
     * @param target_screen_name 目标用户的微博昵称
     */
    public void show(String source_screen_name, String target_screen_name) {
        // TODO "/show.json"
    }

    /**
     * 关注一个用户。
     * 
     * @param uid           需要关注的用户ID
     * @param screen_name   需要关注的用户昵称
     */
    public void create(long uid, String screen_name) {
        // TODO "/create.json"
    }

    /**
     * 关注一个用户
     * 
     * @param screen_name   需要关注的用户昵称
     */
    @Deprecated
    public void create(String screen_name) {
        // TODO "/create.json"
    }

    /**
     * 取消关注一个用户。
     * 
     * @param uid           需要取消关注的用户ID
     * @param screen_name   需要取消关注的用户昵称
     */
    public void destroy(long uid, String screen_name) {
        // TODO "/destroy.json"
    }

    /**
     * 取消关注一个用户。
     * 
     * @param screen_name 需要取消关注的用户昵称
     */
    @Deprecated
    public void destroy(String screen_name) {
        // TODO "/destroy.json"
    }
}
