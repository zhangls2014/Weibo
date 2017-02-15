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

import cn.zhangls.android.weibo.network.models.Comment;
import cn.zhangls.android.weibo.network.models.CommentList;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/2/11.
 * <p>
 * 微博评论请求接口
 */

public interface CommentService {

    /**
     * 根据微博ID返回某条微博的评论列表。
     *
     * @param access_token     采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param id               需要查询的微博ID。
     * @param since_id         若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count            单页返回的记录条数，默认为50
     * @param page             返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0
     */
    @GET("/2/comments/show.json")
    Observable<CommentList> getCommentById(
            @Query("access_token") @NonNull String access_token,
            @Query("id") long id,
            @Query("since_id") long since_id,
            @Query("max_id") long max_id,
            @Query("count") int count,
            @Query("page") int page,
            @Query("filter_by_author") int filter_by_author
    );

    /**
     * 对一条微博进行评论。
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param comment      评论内容，内容不超过140个汉字。
     * @param id           需要评论的微博ID。
     * @param comment_ori  当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
     */
    @FormUrlEncoded
    @POST("/2/comments/create.json")
    Observable<Comment> create(
            @Field("access_token") @NonNull String access_token,
            @Field("comment") String comment,
            @Field("id") long id,
            @Field("comment_ori") int comment_ori
    );

    /**
     * 回复一条评论。
     *
     * @param access_token    采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param cid             需要回复的评论ID
     * @param id              需要评论的微博ID
     * @param comment         回复评论内容，内容不超过140个汉字
     * @param without_mention 回复中是否自动加入“回复@用户名”，0：是、1：否，默认为0。
     * @param comment_ori     当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
     */
    @FormUrlEncoded
    @POST("/2/comments/reply.json")
    Observable<Comment> reply(
            @Field("access_token") @NonNull String access_token,
            @Field("cid") long cid,
            @Field("id") long id,
            @Field("comment") String comment,
            @Field("without_mention") int without_mention,
            @Field("comment_ori") int comment_ori
    );

    /**
     * 删除一条评论
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param cid          需要回复的评论ID
     */
    @POST("/2/comments/destroy.json")
    Observable<Comment> destroy(
            @Field("access_token") @NonNull String access_token,
            @Field("cid") long cid
    );
}
