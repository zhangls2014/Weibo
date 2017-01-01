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

import cn.zhangls.android.weibo.network.models.UrlList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/16.
 * <p>
 * 短链操作方法
 */

public interface ShortUrlService {
    /**
     * 将一个或多个长链接转换成短链接
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param url_long     需要转换的长链接，需要URLencoded，最多不超过20个
     * @return 短链接
     */
    @GET("/2/short_url/shorten.json")
    Observable<UrlList> getShorten(
            @Query("access_token") String access_token,
            @Query("url_long") String url_long
    );

    /**
     * 将一个或多个短链接还原成原始的长链接
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param url_short    要还原的短链接，需要URLencoded，最多不超过20个
     * @return 短链接
     */
    @GET("/2/short_url/expand.json")
    Observable<UrlList> getExpand(
            @Query("access_token") String access_token,
            @Query("url_long") String url_short
    );
}
