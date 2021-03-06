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

package cn.zhangls.android.weibo.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhangls on 2016/11/15.
 * <p>
 * 用户的关注列表
 */

public class FriendsList {
    /**
     * 用户信息
     */
    @SerializedName("users")
    private List<User> user;
    /**
     * 上一个数据游标
     */
    @SerializedName("previous_cursor")
    private String previous_cursor;
    /**
     * 下一个数据游标
     */
    @SerializedName("next_cursor")
    private String next_cursor;
    /**
     * 数据总数
     */
    @SerializedName("total_number")
    private int total_number;

    public String getNext_cursor() {
        return next_cursor;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public List<User> getUser() {
        return user;
    }
}
