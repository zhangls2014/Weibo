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

import cn.zhangls.android.weibo.network.models.TrendHourly;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/16.
 * <p>
 * 话题接口
 */

public interface TrendsService {

    /**
     * 返回最近一小时内的热门话题
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param base_app     是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    @GET("/2/trends/hourly.json")
    Observable<TrendHourly> hourly(
            @Query("access_token") @NonNull String access_token,
            @Query("base_app") int base_app
    );
}
