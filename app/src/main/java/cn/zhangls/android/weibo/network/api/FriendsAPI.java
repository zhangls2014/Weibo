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

import cn.zhangls.android.weibo.network.models.FriendsList;
import cn.zhangls.android.weibo.network.models.GroupList;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.network.service.FriendsService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 *
 * 该类封装了好友接口
 */

public class FriendsAPI extends BaseAPI {

    /**
     * FriendsService
     */
    private FriendsService mFriendsService;

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public FriendsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

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
     */
    public void getFriendsList(Observer<FriendsList> observer,
                               @NonNull String access_token, long uid, String screen_name,
                               int count, int cursor, int trim_status) {
        mFriendsService = mRetrofit.create(FriendsService.class);
        mFriendsService.getFriendsList(access_token, uid, screen_name, count, cursor, trim_status)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 获取当前登陆用户好友分组列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     */
    public void getGroupList(Observer<GroupList> observer, @NonNull String access_token) {
        mFriendsService = mRetrofit.create(FriendsService.class);
        mFriendsService.getGroupList(access_token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

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
    public void getGroupTimeline(Observer<StatusList> observer, @NonNull String access_token,
                                 long list_id, long since_id, long max_id, int count, int page,
                                 boolean base_app, int feature) {
        mFriendsService = mRetrofit.create(FriendsService.class);
        mFriendsService.getGroupTimeline(access_token, list_id, since_id, max_id, count, page, base_app, feature)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
