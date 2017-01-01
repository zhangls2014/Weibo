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

import cn.zhangls.android.weibo.network.models.Privacy;
import cn.zhangls.android.weibo.network.models.School;
import cn.zhangls.android.weibo.network.models.Uid;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 * <p>
 * 帐号接口
 */

public interface AccountService {

    /**
     * 获取所有的学校列表。
     * NOTE：参数keyword与capital二者必选其一，且只能选其一 按首字母capital查询时，必须提供province参数
     *
     * @param province 省份范围，省份ID
     * @param city     城市范围，城市ID
     * @param area     区域范围，区ID
     * @param type     学校类型，可为以下几种： 1：大学，2：高中，3：中专技校，4：初中，5：小学
     * @param keyword  学校名称关键字
     * @param count    返回的记录条数，默认为10
     */
    @GET("/2/friendships/friends.json")
    Observable<ArrayList<School>> getSchoolListbyKey(
            @NonNull @Query("access_token") String access_token,
            @Query("province") int province,
            @Query("city") int city,
            @Query("area") int area,
            @Query("type") int type,
            @NonNull @Query("keyword") String keyword,
            @Query("count") int count
    );

    /**
     * 获取所有的学校列表。
     * NOTE：参数keyword与capital二者必选其一，且只能选其一 按首字母capital查询时，必须提供province参数
     *
     * @param province 省份范围，省份ID
     * @param city     城市范围，城市ID
     * @param area     区域范围，区ID
     * @param type     学校类型，可为以下几种： 1：大学，2：高中，3：中专技校，4：初中，5：小学
     * @param capital  学校首字母，默认为A
     * @param count    返回的记录条数，默认为10
     */
    @GET("/2/friendships/friends.json")
    Observable<ArrayList<School>> getSchoolListByCapital(
            @NonNull @Query("access_token") String access_token,
            @Query("province") int province,
            @Query("city") int city,
            @Query("area") int area,
            @Query("type") int type,
            @NonNull @Query("capital") String capital,
            @Query("count") int count
    );

    /**
     * OAuth授权之后，获取授权用户的UID
     */
    @GET("/2/account/get_uid.json")
    Observable<Uid> getUid(@NonNull @Query("access_token") String access_token);

    /**
     * 设置隐私信息
     *
     * @param comment  谁可以评论此账号的微薄。0：所有人，1：我关注的人。默认为0。
     * @param geo      发布微博，是否允许微博保存并显示所处的地理位置信息。 0允许，1不允许，默认值0。
     * @param message  谁可以给此账号发私信。0：所有人，1：我关注的人。默认为1。
     * @param realname 是否允许别人通过真实姓名搜索到我， 0允许，1不允许，默认值1。
     * @param badge    勋章展现状态，值—1私密状态，0公开状态，默认值0。
     * @param mobile   是否可以通过手机号码搜索到我，0：不可以、1：可以，默认为不更新
     * @param mention  是否可以提及到我，0：所有人、1：关注的人、2：可信用户，默认为不更新。
     */
    @POST("/2/account/update_privacy.json")
    Observable<Privacy> updatePrivacy(
            @NonNull @Query("access_token") String access_token,
            @Field("comment") int comment,
            @Field("geo") int geo,
            @Field("message") int message,
            @Field("realname") int realname,
            @Field("badge") int badge,
            @Field("mobile") int mobile,
            @Field("mention") int mention
    );

    /**
     * 获取隐私信息设置情况
     */
    @GET("/2/account/get_privacy.json")
    Observable<Privacy> getPrivacy(@NonNull @Query("access_token") String access_token);
}
