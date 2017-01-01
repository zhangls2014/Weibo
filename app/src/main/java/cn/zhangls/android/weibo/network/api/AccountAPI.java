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
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;

import cn.zhangls.android.weibo.network.models.Privacy;
import cn.zhangls.android.weibo.network.models.School;
import cn.zhangls.android.weibo.network.models.Uid;
import cn.zhangls.android.weibo.network.service.AccountService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangls on 2016/10/21.
 *
 * 此类封装了账号的接口
 */
public class AccountAPI extends BaseAPI {
    /**
     * AccountService
     */
    private AccountService mAccountService;

    /** 学校类型，1：大学、2：高中、3：中专技校、4：初中、5：小学，默认为1。 */
    public static final int SCHOOL_TYPE_COLLEGE     = 1;
    public static final int SCHOOL_TYPE_SENIOR      = 2;
    public static final int SCHOOL_TYPE_TECHNICAL   = 3;
    public static final int SCHOOL_TYPE_JUNIOR      = 4;
    public static final int SCHOOL_TYPE_PRIMARY     = 5;

    /** 学校首字母，默认为A。 */
    public enum CAPITAL {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
    }


    public AccountAPI(Context context, Oauth2AccessToken accessToken) {
        super(context, accessToken);
    }

    /**
     * 获取当前登录用户的隐私设置。
     */
    public void getPrivacy(Observer<Privacy> observer) {
        mAccountService = mRetrofit.create(AccountService.class);
        mAccountService.getPrivacy(access_token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 获取所有的学校列表。
     * NOTE：参数keyword与capital二者必选其一，且只能选其一 按首字母capital查询时，必须提供province参数
     * 
     * @param province   省份范围，省份ID
     * @param city       城市范围，城市ID
     * @param area       区域范围，区ID
     * @param type       学校类型，可为以下几种： 1：大学，2：高中，3：中专技校，4：初中，5：小学
     *                   <li> {@link #SCHOOL_TYPE_COLLEGE}
     *                   <li> {@link #SCHOOL_TYPE_SENIOR}
     *                   <li> {@link #SCHOOL_TYPE_TECHNICAL}
     *                   <li> {@link #SCHOOL_TYPE_JUNIOR}
     *                   <li> {@link #SCHOOL_TYPE_PRIMARY}
     * @param keyword    学校名称关键字
     * @param count      返回的记录条数，默认为10
     */
    public void schoolListByKey(Observer<ArrayList<School>> observer, int province, int city, int area,
                                int type, String keyword, int count) {
        mAccountService = mRetrofit.create(AccountService.class);
        mAccountService.getSchoolListbyKey(access_token, province, city, area, type, keyword, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * 获取所有的学校列表。
     * NOTE：参数keyword与capital二者必选其一，且只能选其一 按首字母capital查询时，必须提供province参数
     *
     * @param province   省份范围，省份ID
     * @param city       城市范围，城市ID
     * @param area       区域范围，区ID
     * @param type       学校类型，可为以下几种： 1：大学，2：高中，3：中专技校，4：初中，5：小学
     *                   <li> {@link #SCHOOL_TYPE_COLLEGE}
     *                   <li> {@link #SCHOOL_TYPE_SENIOR}
     *                   <li> {@link #SCHOOL_TYPE_TECHNICAL}
     *                   <li> {@link #SCHOOL_TYPE_JUNIOR}
     *                   <li> {@link #SCHOOL_TYPE_PRIMARY}
     * @param capital    学校首字母，默认为A
     * @param count      返回的记录条数，默认为10
     */
    public void schoolListByCaptial(Observer<ArrayList<School>> observer, int province, int city,
                                    int area, int type, CAPITAL capital, int count) {
        mAccountService = mRetrofit.create(AccountService.class);
        mAccountService.getSchoolListByCapital(access_token, province, city, area, type,
                capital.toString(), count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }

    /**
     * OAuth授权之后，获取授权用户的UID。
     */
    public void getUid(Observer<Uid> observer) {
        mAccountService = mRetrofit.create(AccountService.class);
        mAccountService.getUid(access_token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//订阅
    }
}
