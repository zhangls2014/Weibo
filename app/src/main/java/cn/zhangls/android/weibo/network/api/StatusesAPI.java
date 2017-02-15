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
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.StatusList;
import cn.zhangls.android.weibo.network.service.StatusesService;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 *
 * 该类封装了微博接口
 */

public class StatusesAPI extends BaseAPI {
    /**
     * access_token
     */
    private String access_token;

    /**
     * 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐
     */
    public static final int FEATURE_ALL = 0;
    public static final int FEATURE_ORIGINAL = 1;
    public static final int FEATURE_PICTURE = 2;
    public static final int FEATURE_VIDEO = 3;
    public static final int FEATURE_MUSICE = 4;

    /**
     * 作者筛选类型，0：全部、1：我关注的人、2：陌生人
     */
    public static final int AUTHOR_FILTER_ALL = 0;
    public static final int AUTHOR_FILTER_ATTENTIONS = 1;
    public static final int AUTHOR_FILTER_STRANGER = 2;

    /**
     * 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论
     */
    public static final int SRC_FILTER_ALL = 0;
    public static final int SRC_FILTER_WEIBO = 1;
    public static final int SRC_FILTER_WEIQUN = 2;

    /**
     * 原创筛选类型，0：全部微博、1：原创的微博。
     */
    public static final int TYPE_FILTER_ALL = 0;
    public static final int TYPE_FILTER_ORIGAL = 1;

    /**
     * 获取类型，1：微博、2：评论、3：私信，默认为1。
     */
    public static final int TYPE_STATUSES = 1;
    public static final int TYPE_COMMENTS = 2;
    public static final int TYPE_MESSAGE = 3;

    /**
     * 标识是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
     */
    public static final int COMMENTS_NONE = 0;
    public static final int COMMENTS_CUR_STATUSES = 1;
    public static final int COMMENTS_RIGAL_STATUSES = 2;
    public static final int COMMENTS_BOTH = 3;

    /**
     * 是否只获取当前应用的数据：0：所有数据、1：当前应用数据，默认为0
     */
    public static final int BASE_APP_ALL = 0;
    public static final int BASE_APP_SELF = 1;

    /**
     * 表情类别，face：普通表情、ani：魔法表情、cartoon：动漫表情，默认为face。
     */
    public static final String EMOTION_TYPE_FACE = "face";
    public static final String EMOTION_TYPE_ANI = "ani";
    public static final String EMOTION_TYPE_CARTOON = "cartoon";

    /**
     * 语言类别，cnname：简体、twname：繁体，默认为cnname。
     */
    public static final String LANGUAGE_CNNAME = "cnname";
    public static final String LANGUAGE_TWNAME = "twname";

    /**
     * 返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
     */
    public static final int TRIM_USER_ALL = 0;
    public static final int TRIM_USER_ID = 1;

    /**
     * StatusesService
     */
    private StatusesService mStatusesService;

    public StatusesAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
        access_token = mAccessToken.getToken();
        mStatusesService = mRetrofit.create(StatusesService.class);
    }

    /**
     * 获取最新的公共微博
     *
     * @param count    单页返回的记录条数，默认为50。
     * @param page     返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     */
    public void publicTimeline(Observer<StatusList> observer,
                               int count, int page, int base_app) {
        mStatusesService.getPublicTimeline(access_token, count, page, base_app)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博。
     *
     * @param since_id  若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count     单页返回的记录条数，默认为50。
     * @param page      返回结果的页码，默认为1。
     * @param base_app  是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false。
     * @param feature   过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     *                  <li>{@link #FEATURE_ALL}
     *                  <li>{@link #FEATURE_ORIGINAL}
     *                  <li>{@link #FEATURE_PICTURE}
     *                  <li>{@link #FEATURE_VIDEO}
     *                  <li>{@link #FEATURE_MUSICE}
     * @param trim_user 返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false。
     */
    public void friendsTimeline(Observer<StatusList> observer, long since_id, long max_id, int count,
                                int page, int base_app, int feature, int trim_user) {
        mStatusesService.getFriendsTimeline(access_token, since_id, max_id, count, page, base_app,
                feature, trim_user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博的ID。
     *
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id   若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count    单页返回的记录条数，默认为50
     * @param page     返回结果的页码，默认为1
     * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     * @param feature  过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     *                 <li> {@link #FEATURE_ALL}
     *                 <li> {@link #FEATURE_ORIGINAL}
     *                 <li> {@link #FEATURE_PICTURE}
     *                 <li> {@link #FEATURE_VIDEO}
     *                 <li> {@link #FEATURE_MUSICE}
     */
    public void friendsTimelineIds(long since_id, long max_id, int count, int page, boolean base_app,
                                   int feature) {
        // TODO
    }

    /**
     * 获取某个用户最新发表的微博列表。
     *
     * @param uid       需要查询的用户ID
     * @param since_id  若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count     单页返回的记录条数，默认为50
     * @param page      返回结果的页码，默认为1
     * @param base_app  是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @param feature   过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     *                  <li> {@link #FEATURE_ALL}
     *                  <li> {@link #FEATURE_ORIGINAL}
     *                  <li> {@link #FEATURE_PICTURE}
     *                  <li> {@link #FEATURE_VIDEO}
     *                  <li> {@link #FEATURE_MUSICE}
     * @param trim_user 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    public void userTimeline(Observer<StatusList> observer, long uid, long since_id, long max_id,
                             int count, int page, int base_app, int feature, int trim_user) {
        mStatusesService.getUserTimeline(access_token, uid, since_id, max_id, count,
                page, base_app, feature, trim_user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取某个用户最新发表的微博列表。
     *
     * @param screen_name 需要查询的用户昵称
     * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id      若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count       单页返回的记录条数，默认为50
     * @param page        返回结果的页码，默认为1
     * @param base_app    是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @param feature     过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     *                    <li> {@link #FEATURE_ALL}
     *                    <li> {@link #FEATURE_ORIGINAL}
     *                    <li> {@link #FEATURE_PICTURE}
     *                    <li> {@link #FEATURE_VIDEO}
     *                    <li> {@link #FEATURE_MUSICE}
     * @param trim_user   返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    public void userTimeline(Observer<StatusList> observer, String screen_name, long since_id,
                             long max_id, int count, int page, int base_app,
                             int feature, int trim_user) {
        mStatusesService.getUserTimeline(access_token, screen_name, since_id, max_id, count,
                page, base_app, feature, trim_user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博。
     *
     * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id      若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count       单页返回的记录条数，默认为50
     * @param page        返回结果的页码，默认为1
     * @param base_app    是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     * @param featureType 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     *                    <li> {@link #FEATURE_ALL}
     *                    <li> {@link #FEATURE_ORIGINAL}
     *                    <li> {@link #FEATURE_PICTURE}
     *                    <li> {@link #FEATURE_VIDEO}
     *                    <li> {@link #FEATURE_MUSICE}
     * @param trim_user   返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
     */
    public void homeTimeline(long since_id, long max_id, int count, int page, int base_app,
                             int featureType, int trim_user) {
        // TODO "/home_timeline.json"
    }

    /**
     * 获取用户发布的微博的ID
     *
     * @param screen_name 需要查询的用户昵称
     * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id      若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count       单页返回的记录条数，默认为50
     * @param page        返回结果的页码，默认为1
     * @param base_app    是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     * @param featureType 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     *                    <li> {@link #FEATURE_ALL}
     *                    <li> {@link #FEATURE_ORIGINAL}
     *                    <li> {@link #FEATURE_PICTURE}
     *                    <li> {@link #FEATURE_VIDEO}
     *                    <li> {@link #FEATURE_MUSICE}
     */
    public void userTimelineIds(String screen_name, long since_id, long max_id, int count, int page, int base_app,
                                int featureType) {
        // TODO  "/user_timeline/ids.json"
    }

    /**
     * 获取指定微博的转发微博列表
     *
     * @param id         需要查询的微博ID。
     * @param since_id   若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
     *                   <li> {@link #AUTHOR_FILTER_ALL}
     *                   <li> {@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li> {@link #AUTHOR_FILTER_STRANGER}
     */
    public void repostTimeline(long id, long since_id, long max_id, int count, int page, int authorType) {
        // TODO "/repost_timeline.json"
    }

    /**
     * 获取一条原创微博的最新转发微博的ID。
     *
     * @param id         需要查询的微博ID
     * @param since_id   若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
     *                   <li> {@link #AUTHOR_FILTER_ALL}
     *                   <li> {@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li> {@link #AUTHOR_FILTER_STRANGER}
     */
    public void repostTimelineIds(long id, long since_id, long max_id, int count, int page, int authorType) {
        // TODO  "/repost_timeline/ids.json"
    }

    /**
     * 获取当前用户最新转发的微博列表。
     *
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id   若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count    单页返回的记录条数，默认为50
     * @param page     返回结果的页码，默认为1
     */
    public void repostByMe(long since_id, long max_id, int count, int page) {
        // TODO "/repost_by_me.json"
    }

    /**
     * 获取最新的提到登录用户的微博列表，即@我的微博。
     *
     * @param since_id   若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
     *                   <li> {@link #AUTHOR_FILTER_ALL}
     *                   <li> {@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li> {@link #AUTHOR_FILTER_STRANGER}
     * @param sourceType 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论。可分为以下几种：
     *                   <li> {@link #SRC_FILTER_ALL}
     *                   <li> {@link #SRC_FILTER_WEIBO}
     *                   <li> {@link #SRC_FILTER_WEIQUN}
     * @param filterType 原创筛选类型，0：全部微博、1：原创的微博，默认为0。可分为以下几种：
     *                   <li> {@link #TYPE_FILTER_ALL}
     *                   <li> {@link #TYPE_FILTER_ORIGAL}
     * @param trim_user  返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
     */
    public void mentions(long since_id, long max_id, int count, int page, int authorType, int sourceType,
                         int filterType, boolean trim_user) {
        // TODO "/mentions.json"
    }

    /**
     * 获取@当前用户的最新微博的ID。
     *
     * @param since_id   若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。可为以下几种：
     *                   <li> {@link #AUTHOR_FILTER_ALL}
     *                   <li> {@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li> {@link #AUTHOR_FILTER_STRANGER}
     * @param sourceType 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论。可分为以下几种：
     *                   <li> {@link #SRC_FILTER_ALL}
     *                   <li> {@link #SRC_FILTER_WEIBO}
     *                   <li> {@link #SRC_FILTER_WEIQUN}
     * @param filterType 原创筛选类型，0：全部微博、1：原创的微博，默认为0。可分为以下几种：
     *                   <li> {@link #TYPE_FILTER_ALL}
     *                   <li> {@link #TYPE_FILTER_ORIGAL}
     */
    public void mentionsIds(long since_id, long max_id, int count, int page, int authorType, int sourceType,
                            int filterType) {
        // TODO "/mentions/ids.json"
    }

    /**
     * 获取双向关注用户的最新微博。
     *
     * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id      若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count       单页返回的记录条数，默认为50
     * @param page        返回结果的页码，默认为1
     * @param base_app    是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     * @param featureType 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     *                    <li> {@link #FEATURE_ALL}
     *                    <li> {@link #FEATURE_ORIGINAL}
     *                    <li> {@link #FEATURE_PICTURE}
     *                    <li> {@link #FEATURE_VIDEO}
     *                    <li> {@link #FEATURE_MUSICE}
     * @param trim_user   返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
     */
    public void bilateralTimeline(long since_id, long max_id, int count, int page, int base_app, int featureType,
                                  int trim_user) {
        // TODO "/bilateral_timeline.json"
    }

    /**
     * 根据微博ID获取单条微博内容。
     *
     * @param id 需要获取的微博ID
     */
    public void show(Observer<Status> observer, long id) {
        mStatusesService.show(access_token, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通过微博（评论、私信）ID获取其MID。
     *
     * @param ids  需要查询的微博（评论、私信）ID，最多不超过20个。
     * @param type 获取类型，1：微博、2：评论、3：私信，默认为1。可为几下几种：
     *             <li> {@link #TYPE_STATUSES}
     *             <li> {@link #TYPE_COMMENTS}
     *             <li> {@link #TYPE_MESSAGE}
     */
    public void queryMID(long[] ids, int type) {
        // TODO "/querymid.json"
    }

    /**
     * 通过微博（评论、私信）MID获取其ID,形如“3z4efAo4lk”的MID即为经过base62转换的MID。
     *
     * @param mids     需要查询的微博（评论、私信）MID，最多不超过20个
     * @param type     获取类型，1：微博、2：评论、3：私信，默认为1。可为几下几种：
     *                 <li> {@link #TYPE_STATUSES}
     *                 <li> {@link #TYPE_COMMENTS}
     *                 <li> {@link #TYPE_MESSAGE}
     * @param inbox    仅对私信有效，当MID类型为私信时用此参数，0：发件箱、1：收件箱，默认为0
     * @param isBase62 MID是否是base62编码，0：否、1：是，默认为0
     */
    public void queryID(String[] mids, int type, boolean inbox, boolean isBase62) {
        // TODO "/queryid.json"
    }

    /**
     * 按天返回热门微博转发榜的微博列表。
     *
     * @param count    返回的记录条数，最大不超过50，默认为20
     * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     */
    public void hotRepostDaily(int count, int base_app) {
        // TODO "/hot/repost_daily.json"
    }

    /**
     * 按周返回热门微博转发榜的微博列表。
     *
     * @param count    返回的记录条数，最大不超过50，默认为20
     * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     */
    public void hotRepostWeekly(int count, int base_app) {
        // TODO "/hot/repost_weekly.json"
    }

    /**
     * 按天返回热门微博评论榜的微博列表。
     *
     * @param count    返回的记录条数，最大不超过50，默认为20
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    public void hotCommentsDaily(int count, int base_app) {
        // TODO "/hot/comments_daily.json"
    }

    /**
     * 按周返回热门微博评论榜的微博列表。
     *
     * @param count    返回的记录条数，最大不超过50，默认为20
     * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
     */
    public void hotCommentsWeekly(int count, boolean base_app) {
        // TODO  "/hot/comments_weekly.json"
    }

    /**
     * 批量获取指定微博的转发数评论数。
     *
     * @param ids 需要获取数据的微博ID，最多不超过100个
     */
    public void count(String[] ids) {
        // TODO "/count.json"
    }

    /**
     * 转发一条微博
     *
     * @param id 要转发的微博ID
     * @param status 添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”
     * @param is_comment 是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
     *                   <li> {@link #COMMENTS_NONE}
     *                   <li> {@link #COMMENTS_CUR_STATUSES}
     *                   <li> {@link #COMMENTS_RIGAL_STATUSES}
     *                   <li> {@link #COMMENTS_BOTH}
     * @param rip 开发者上报的操作用户真实IP，形如：211.156.0.1
     */
    public void repost(Observer<Status> observer, long id, String status, int is_comment, String rip) {
        mStatusesService.repostStatus(access_token, id, status, is_comment, rip)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据微博ID删除指定微博。
     *
     * @param id 需要删除的微博ID
     */
    public void destroy(long id) {
        // TODO "/destroy.json"
    }

    /**
     * 发布一条新微博(连续两次发布的微博不可以重复)。
     *
     * @param content 要发布的微博文本内容，内容不超过140个汉字
     * @param lat     纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0
     * @param lon     经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0
     */
    public void update(String content, String lat, String lon) {
        // TODO "/update.json"
    }

    /**
     * 上传图片并发布一条新微博，此方法会处理urlencode。
     *
     * @param content 要发布的微博文本内容，内容不超过140个汉字
     * @param bitmap  要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M
     * @param lat     纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0
     * @param lon     经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0
     */
    public void upload(String content, Bitmap bitmap, String lat, String lon) {
        // TODO "/upload.json"
    }

    /**
     * 指定一个图片URL地址抓取后上传并同时发布一条新微博，此方法会处理URLencode。
     *
     * @param status   要发布的微博文本内容，内容不超过140个汉字
     * @param imageUrl 图片的URL地址，必须以http开头
     * @param pic_id   已经上传的图片pid，多个时使用英文半角逗号符分隔，最多不超过九张。 imageUrl 和 pic_id必选一个，两个参数都存在时，取picid参数的值为准
     * @param lat      纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0
     * @param lon      经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0
     */
    public void uploadUrlText(String status, String imageUrl, String pic_id, String lat, String lon) {
        // TODO "/upload_url_text.json"
    }

    /**
     * 获取微博官方表情的详细信息。
     *
     * @param type     表情类别，表情类别，face：普通表情、ani：魔法表情、cartoon：动漫表情，默认为face。可为以下几种：
     *                 <li> {@link #EMOTION_TYPE_FACE}
     *                 <li> {@link #EMOTION_TYPE_ANI}
     *                 <li> {@link #EMOTION_TYPE_CARTOON}
     * @param language 语言类别，cnname：、twname：，默认为cnname。
     *                 <li> {@link #LANGUAGE_CNNAME}
     *                 <li> {@link #LANGUAGE_TWNAME}
     */
    public void emotions(String type, String language) {
        // TODO "/emotions.json"
    }
}
