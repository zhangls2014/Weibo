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

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 * <p>
 * 隐私结构体
 */

public class Privacy {
    /**
     * 是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户
     */
    @SerializedName("comment")
    private int comment;
    /**
     * 是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户
     */
    @SerializedName("message")
    private int message;
    /**
     * 是否可以通过真名搜索到我，0：不可以、1：可以
     */
    @SerializedName("realname")
    private int realname;
    /**
     * 是否开启地理信息，0：不开启、1：开启
     */
    @SerializedName("geo")
    private int geo;
    /**
     * 勋章是否可见，0：不可见、1：可见
     */
    @SerializedName("badge")
    private int badge;
    /**
     * 是否可以通过手机号码搜索到我，0：不可以、1：可以
     */
    @SerializedName("mobile")
    private int mobile;
    /**
     * 是否开启webim， 0：不开启、1：开启
     */
    @SerializedName("webim")
    private int webim;

    public int getBadge() {
        return badge;
    }

    public int getComment() {
        return comment;
    }

    public int getGeo() {
        return geo;
    }

    public int getMessage() {
        return message;
    }

    public int getMobile() {
        return mobile;
    }

    public int getRealname() {
        return realname;
    }

    public int getWebim() {
        return webim;
    }
}
