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

import java.util.ArrayList;

import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/4.
 * <p>
 * 搜索接口
 */

public interface SearchService {

    /**
     * 搜索用户时的联想搜索建议。
     *
     * @param q     搜索的关键字，必须做URLencoding
     * @param count 返回的记录条数，默认为10
     */
    @FormUrlEncoded
    @POST("/2/search/suggestions/users.json")
    Observable<ArrayList<User>> users(
            @Field("access_token") String access_token,
            @Field("q") String q,
            @Field("count") int count
    );

    /**
     * 搜索微博时的联想搜索建议。
     *
     * @param q     搜索的关键字，必须做URLencoding
     * @param count 返回的记录条数，默认为10
     */
    @FormUrlEncoded
    @POST("/2/search/suggestions/statuses.json")
    Observable<ArrayList<Status>> statuses(
            @Field("access_token") String access_token,
            @Field("q") String q,
            @Field("count") int count
    );

    /**
     * 综合联想，包含用户、微群、应用等的联想建议
     *
     * @param query      搜索的关键字，必须进行URLencode
     * @param sort_user  用户排序，0：按专注人最多，默认为0
     * @param sort_app   应用排序，0：按用户数最多，默认为0
     * @param sort_grp   微群排序，0：按成员数最多，默认为0
     * @param user_count 返回的用户记录条数，默认为4
     * @param app_count  返回的应用记录条数，默认为1
     * @param grp_count  返回的微群记录条数，默认为1
     */
    @FormUrlEncoded
    @POST("/2/search/suggestions/integrate.json")
    Observable<ArrayList<Status>> integrate(
            @Field("access_token") String access_token,
            @Field("query") String query,
            @Field("sort_user") int sort_user,
            @Field("sort_app") int sort_app,
            @Field("sort_grp") int sort_grp,
            @Field("user_count") int user_count,
            @Field("app_count") int app_count,
            @Field("grp_count") int grp_count
    );
}
