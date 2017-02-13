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
import com.sina.weibo.sdk.net.RequestListener;

import cn.zhangls.android.weibo.network.models.CommentList;
import cn.zhangls.android.weibo.network.service.CommentService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls on 2016/10/21.
 * <p>
 * 此类封装了评论的接口。
 */
public class CommentsAPI extends BaseAPI {

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
     * access_token
     */
    private String access_token;
    /**
     * CommentService
     */
    private CommentService mCommentService;

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public CommentsAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
        access_token = mAccessToken.getToken();
        mCommentService = mRetrofit.create(CommentService.class);
    }

    /**
     * 根据微博ID返回某条微博的评论列表。
     *
     * @param id               需要查询的微博ID。
     * @param since_id         若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id           若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count            单页返回的记录条数，默认为50
     * @param page             返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
     *                         <li>{@link #AUTHOR_FILTER_ALL}
     *                         <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                         <li>{@link #AUTHOR_FILTER_STRANGER}
     */
    public void show(Observer<CommentList> observer, long id, long since_id, long max_id,
                     int count, int page, int filter_by_author) {
        mCommentService.getCommentById(access_token, id, since_id, max_id, count, page, filter_by_author)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 获取当前登录用户所发出的评论列表。
     *
     * @param since_id   若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count      单页返回的记录条数，默认为50。
     * @param page       返回结果的页码，默认为1。
     * @param sourceType 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0。
     *                   <li>{@link #SRC_FILTER_ALL}
     *                   <li>{@link #SRC_FILTER_WEIBO}
     *                   <li>{@link #SRC_FILTER_WEIQUN}
     * @param listener   异步请求回调接口
     */
    public void byME(long since_id, long max_id, int count, int page, int sourceType, RequestListener listener) {
        // TODO "/by_me.json"
    }

    /**
     * 获取当前登录用户所接收到的评论列表。
     *
     * @param since_id   若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count      单页返回的记录条数，默认为50。
     * @param page       返回结果的页码，默认为1。
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
     *                   <li>{@link #AUTHOR_FILTER_ALL}
     *                   <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li>{@link #AUTHOR_FILTER_STRANGER}
     * @param sourceType 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0。
     *                   <li>{@link #SRC_FILTER_ALL}
     *                   <li>{@link #SRC_FILTER_WEIBO}
     *                   <li>{@link #SRC_FILTER_WEIQUN}
     * @param listener   异步请求回调接口
     */
    public void toME(long since_id, long max_id, int count, int page, int authorType, int sourceType,
                     RequestListener listener) {
        // TODO "/to_me.json"
    }

    /**
     * 获取当前登录用户的最新评论包括接收到的与发出的。
     *
     * @param since_id  若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count     单页返回的记录条数，默认为50。
     * @param page      返回结果的页码，默认为1。
     * @param trim_user 返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false。
     * @param listener  异步请求回调接口
     */
    public void timeline(long since_id, long max_id, int count, int page, boolean trim_user, RequestListener listener) {
        // TODO "/timeline.json"
    }

    /**
     * 获取最新的提到当前登录用户的评论，即@我的评论 若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0
     *
     * @param since_id   若指定此参数，则返回ID小于或等于max_id的评论，默认为0
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的评论，默认为0
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1
     * @param authorType 作者筛选类型，0：全部，1：我关注的人， 2：陌生人，默认为0
     *                   <li> {@link #AUTHOR_FILTER_ALL}
     *                   <li> {@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li> {@link #AUTHOR_FILTER_STRANGER}
     * @param sourceType 来源筛选类型，0：全部，1：来自微博的评论，2：来自微群的评论，默认为0
     *                   <li> {@link #SRC_FILTER_ALL}
     *                   <li> {@link #SRC_FILTER_WEIBO}
     *                   <li> {@link #SRC_FILTER_WEIQUN}
     * @param listener   异步请求回调接口
     */
    public void mentions(long since_id, long max_id, int count, int page, int authorType, int sourceType,
                         RequestListener listener) {
        // TODO "/mentions.json"
    }

    /**
     * 根据评论ID批量返回评论信息。
     *
     * @param cids     需要查询的批量评论ID数组，最大50
     * @param listener 异步请求回调接口
     */
    public void showBatch(long[] cids, RequestListener listener) {
        // TODO "/show_batch.json"
    }

    /**
     * 对一条微博进行评论。
     *
     * @param comment     评论内容，内容不超过140个汉字。
     * @param id          需要评论的微博ID。
     * @param comment_ori 当评论转发微博时，是否评论给原微博
     * @param listener    异步请求回调接口
     */
    public void create(String comment, long id, boolean comment_ori, RequestListener listener) {
        // TODO "/create.json"
    }

    /**
     * 删除一条评论。
     *
     * @param cid      要删除的评论ID，只能删除登录用户自己发布的评论。
     * @param listener 异步请求回调接口
     */
    public void destroy(long cid, RequestListener listener) {
        // TODO "/destroy.json"
    }

    /**
     * 根据评论ID批量删除评论。
     *
     * @param ids      需要删除的评论ID数组，最多20个。
     * @param listener 异步请求回调接口
     */
    public void destroyBatch(long[] ids, RequestListener listener) {
        // TODO "/sdestroy_batch.json"
    }

    /**
     * 回复一条评论。
     *
     * @param cid             需要回复的评论ID
     * @param id              需要评论的微博ID
     * @param comment         回复评论内容，内容不超过140个汉字
     * @param without_mention 回复中是否自动加入“回复@用户名”，true：是、false：否，默认为false
     * @param comment_ori     当评论转发微博时，是否评论给原微博，false：否、true：是，默认为false
     * @param listener        异步请求回调接口
     */
    public void reply(long cid, long id, String comment, boolean without_mention, boolean comment_ori,
                      RequestListener listener) {
        // TODO "/reply.json"
    }
}
