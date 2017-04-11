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

package cn.zhangls.android.weibo.ui.user;

import cn.zhangls.android.weibo.common.BasePresenter;
import cn.zhangls.android.weibo.common.BaseView;
import cn.zhangls.android.weibo.network.models.User;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/4/10.
 */

interface UserContract {
    interface Presenter extends BasePresenter {
        /**
         * 根据用户ID获取用户信息
         *
         * @param uid 需要查询的用户ID
         */
        void requestUserInfo(long uid);

        /**
         * 根据用户昵称获取用户信息
         *
         * @param screenName 需要查询的用户昵称
         */
        void requestUserInfo(String screenName);
    }

    interface UserView extends BaseView<Presenter> {
        /**
         * 加载用户信息
         *
         * @param user 用户信息数据结构体
         */
        void loadUserInfo(User user);
    }
}
