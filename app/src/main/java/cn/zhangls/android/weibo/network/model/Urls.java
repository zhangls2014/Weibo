/*
 * MIT License
 *
 * Copyright (c) 2016 NickZhang https://github.com/zhangls2014
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

package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/16.
 * <p>
 * url
 */

public class Urls {

    /**
     * url 信息
     */
    @SerializedName("urls")
    private ArrayList<Url> urls;

    public ArrayList<Url> getUrls() {
        return urls;
    }

    private class Url {
        /**
         * 短链接
         */
        @SerializedName("url_short")
        private String url_short;
        /**
         * 长连接
         */
        @SerializedName("url_long")
        private String url_long;
        /**
         * 链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5、投票
         */
        @SerializedName("type")
        private int type;
        /**
         * 短链的可用状态，true：可用、false：不可用
         */
        @SerializedName("result")
        private boolean result;

        public boolean isResult() {
            return result;
        }

        public int getType() {
            return type;
        }

        public String getUrl_long() {
            return url_long;
        }

        public String getUrl_short() {
            return url_short;
        }
    }
}
