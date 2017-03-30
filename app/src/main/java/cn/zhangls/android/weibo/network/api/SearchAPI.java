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

import java.util.ArrayList;

import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;
import cn.zhangls.android.weibo.network.service.SearchService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 该类封装了微博的搜索接口
 */
public class SearchAPI extends BaseAPI {

    /** 联想类型，0：关注、1：粉丝。 */
    public static final int FRIEND_TYPE_ATTENTIONS  = 0;
    public static final int FRIEND_TYPE_FELLOWS     = 1;

    /** 联想范围，0：只联想关注人、1：只联想关注人的备注、2：全部，默认为2。 */
    public static final int RANGE_ATTENTIONS     = 0;
    public static final int RANGE_ATTENTION_TAGS = 1;
    public static final int RANGE_ALL            = 2;

    /**
     * access_token
     */
    private String access_token;
    /**
     * SearchService
     */
    private SearchService mSearchService;

    public SearchAPI(@NonNull Context context, @NonNull Oauth2AccessToken accessToken) {
        super(context, accessToken);
        access_token = mAccessToken.getToken();
        mSearchService = mRetrofit.create(SearchService.class);
    }

    /**
     * 搜索用户时的联想搜索建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding
     * @param count     返回的记录条数，默认为10
     */
    public void users(Observer<ArrayList<User>> observer, String q, int count) {
        mSearchService.users(access_token, q, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 搜索微博时的联想搜索建议。
     * 
     * @param q         搜索的关键字，必须做URLencoding。
     * @param count     返回的记录条数，默认为10。
     */
    public void statuses(Observer<ArrayList<Status>> observer, String q, int count) {
        mSearchService.statuses(access_token, q, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 综合联想，包含用户、微群、应用等的联想建议
     *
     * @param query 搜索的关键字，必须进行URLencode
     * @param sort_user 用户排序，0：按专注人最多，默认为0
     * @param sort_app 应用排序，0：按用户数最多，默认为0
     * @param sort_grp 微群排序，0：按成员数最多，默认为0
     * @param user_count 返回的用户记录条数，默认为4
     * @param app_count 返回的应用记录条数，默认为1
     * @param grp_count 返回的微群记录条数，默认为1
     */
    public void integrate(Observer<ArrayList<Status>> observer, String access_token, String query, int sort_user, int sort_app,
                          int sort_grp, int user_count, int app_count, int grp_count) {
        mSearchService.integrate(access_token, query, sort_user, sort_app, sort_grp, user_count, app_count, grp_count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }
}
