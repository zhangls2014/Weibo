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

import java.util.ArrayList;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.models.Status;
import cn.zhangls.android.weibo.network.models.User;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/23.
 */

interface SearchContract {
    interface Presenter extends BasePresenter {
        /**
         * 搜索用户时的联想搜索建议
         *
         * @param query 搜索的关键字
         */
        void searchUser(String query);

        /**
         * 搜索微博时的联想搜索建议
         *
         * @param query 搜索的关键字
         */
        void searchStatus(String query);

        /**
         * 综合搜索的联想搜索建议
         *
         * @param query 搜索的关键字
         */
        void searchUserAndWeibo(String query);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 显示推荐用户
         *
         * @param users 用户
         */
        void loadUsers(ArrayList<User> users);

        /**
         * 显示推荐微博
         *
         * @param statuses 微博
         */
        void loadStatuses(ArrayList<Status> statuses);

        /**
         * 显示推荐用户和推荐微博
         *
         * @param users    用户
         * @param statuses 微博
         */
        void loadUserAndWeibo(ArrayList<User> users, ArrayList<Status> statuses);
    }
}