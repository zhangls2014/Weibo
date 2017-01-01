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

package cn.zhangls.android.weibo.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/27.
 * <p>
 * 使用 SharedPreference 保存信息
 */

public class SharedPreferenceInfo {
    /**
     * 上下文对象
     */
    private Context mContext;

    public SharedPreferenceInfo(Context context) {
        mContext = context;
    }

    private interface key {
        String USER_NAME = "user_name";
    }

    public void setUserName(@NonNull String userName) {
        SharedPreferencesUtil.put(mContext, key.USER_NAME, userName);
    }

    public String getUserName() {
        return (String) SharedPreferencesUtil.get(mContext, key.USER_NAME, "");
    }

}
