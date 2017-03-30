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

package cn.zhangls.android.weibo.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import cn.zhangls.android.weibo.common.ParentPresenter;
import cn.zhangls.android.weibo.network.BaseObserver;
import cn.zhangls.android.weibo.network.api.SearchAPI;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/23.
 */

class SearchPresenter extends ParentPresenter<SearchContract.View> implements SearchContract.Presenter {

    /**
     * SearchAPI
     */
    private SearchAPI mSearchAPI;
    /**
     * 用户数据结构体
     */
    private ArrayList<User> mUsers;
    /**
     * 微博数据结构体
     */
    private ArrayList<Status> mStatuses;

    SearchPresenter(Context context, @NonNull SearchContract.View subView) {
        super(context, subView);
    }

    /**
     * Presenter的入口方法
     */
    @Override
    public void start() {
        mSearchAPI = new SearchAPI(mContext, mAccessToken);
    }

    /**
     * 搜索用户时的联想搜索建议
     *
     * @param query 搜索的关键字
     */
    @Override
    public void searchUser(String query) {
        BaseObserver<ArrayList<User>> observer = new BaseObserver<ArrayList<User>>(mContext) {
            @Override
            public void onNext(ArrayList<User> value) {
                mUsers = value;
            }

            @Override
            public void onComplete() {
                mSubView.loadUsers(mUsers);
            }
        };

        mSearchAPI.users(observer, query, 10);
    }

    /**
     * 搜索微博时的联想搜索建议
     *
     * @param query 搜索的关键字
     */
    @Override
    public void searchStatus(String query) {
        BaseObserver<ArrayList<Status>> observer = new BaseObserver<ArrayList<Status>>(mContext) {
            @Override
            public void onNext(ArrayList<Status> value) {
                mStatuses = value;
            }

            @Override
            public void onComplete() {
                mSubView.loadStatuses(mStatuses);
            }
        };

        mSearchAPI.statuses(observer, query, 10);
    }

    /**
     * 综合搜索的联想搜索建议
     *
     * @param query 搜索的关键字
     */
    @Override
    public void searchUserAndWeibo(String query) {
        searchUser(query);
        searchStatus(query);
    }
}
