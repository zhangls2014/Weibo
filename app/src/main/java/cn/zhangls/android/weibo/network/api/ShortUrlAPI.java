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

import cn.zhangls.android.weibo.network.models.UrlList;
import cn.zhangls.android.weibo.network.service.ShortUrlService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 * <p>
 * 该类封装了短链接口
 */

public class ShortUrlAPI extends BaseAPI {

    /**
     * ShortUrlService
     */
    private ShortUrlService mShortUrlService;

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context     上下文对象
     * @param accessToken 访问令牌
     */
    public ShortUrlAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 将一个或多个长链接转换成短链接
     *
     * @param url_long 需要转换的长链接，需要URLencoded，最多不超过20个
     */
    public void getShorten(Observer<UrlList> observer, String access_token, String url_long) {

    }

    /**
     * 将一个或多个长链接转换成短链接
     *
     * @param url_long 需要转换的长链接，最多不超过20个
     */
    public void shorten(Observer<UrlList> observer, String url_long) {
        mShortUrlService = mRetrofit.create(ShortUrlService.class);
        mShortUrlService.getShorten(access_token, url_long)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 将一个或多个短链接还原成原始的长链接。
     *
     * @param url_short 需要还原的短链接，最多不超过20个
     */
    public void expand(Observer<UrlList> observer, String url_short) {
        mShortUrlService = mRetrofit.create(ShortUrlService.class);
        mShortUrlService.getExpand(access_token, url_short)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取短链接的总点击数。
     *
     * @param url_short 需要取得点击数的短链接，最多不超过20个
     */
    public void clicks(String[] url_short) {
        // TODO"/clicks.json"
    }

    /**
     * 获取一个短链接点击的referer来源和数量。
     *
     * @param url_short 需要取得点击来源的短链接
     */
    public void referers(String url_short) {
        // TODO "/referers.json"
    }

    /**
     * 获取一个短链接点击的地区来源和数量。
     *
     * @param url_short 需要取得点击来源的短链接
     */
    public void locations(String url_short) {
        // TODO "/locations.json"
    }

    /**
     * 获取短链接在微博上的微博分享数。
     *
     * @param url_short 需要取得分享数的短链接，最多不超过20个
     */
    public void shareCounts(String[] url_short) {
        // TODO "/share/counts.json"
    }

    /**
     * 获取包含指定单个短链接的最新微博内容。
     *
     * @param url_short 需要取得关联微博内容的短链接
     * @param since_id  若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的微博，默认为0
     * @param count     单页返回的记录条数，默认为50，最多不超过200
     * @param page      返回结果的页码，默认为1
     */
    public void shareStatuses(String url_short, long since_id, long max_id, int count, int page) {
        // TODO "/share/statuses.json"
    }

    /**
     * 获取短链接在微博上的微博评论数
     *
     * @param url_short 需要取得分享数的短链接，最多不超过20个
     */
    public void commentCounts(String[] url_short) {
        // TODO "/comment/counts.json"
    }

    /**
     * 获取包含指定单个短链接的最新微博评论。
     *
     * @param url_short 需要取得关联微博评论内容的短链接
     * @param since_id  若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的评论，默认为0
     * @param count     单页返回的记录条数，默认为50，最多不超过200
     * @param page      返回结果的页码，默认为1
     */
    public void comments(String url_short, long since_id, long max_id, int count, int page) {
        // TODO "/comment/comments.json"
    }
}
