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

import java.util.ArrayList;

/**
 * Created by zhangls{github.com/zhangls2014} on 2017/3/14.
 * <p>
 * 话题结构体
 */

public class TrendHourly {
    @SerializedName("trends")
    private Trend trend;
    @SerializedName("as_if")
    private long asIf;

    public Trend getTrend() {
        return trend;
    }

    public long getAsIf() {
        return asIf;
    }

    public class Trend {
        @SerializedName("2011-05-31")
        private ArrayList<Item> time;

        public ArrayList<Item> getTime() {
            return time;
        }

        public class Item {
            @SerializedName("name")
            private String name;
            @SerializedName("query")
            private String query;

            public String getName() {
                return name;
            }

            public String getQuery() {
                return query;
            }
        }

    }
}