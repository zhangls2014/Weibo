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
 * 评论结构体
 */

public class Comment {

    /**
     * 评论创建时间
     */
    @SerializedName("created_at")
    private String created_at;
    /**
     * 评论的 ID
     */
    @SerializedName("id")
    private String id;
    /**
     * 评论的内容
     */
    @SerializedName("text")
    private String text;
    /**
     * 评论的来源
     */
    @SerializedName("source")
    private String source;
    /** 评论作者的用户信息字段 */
//    @SerializedName("user")
//    private User user;
    /**
     * 评论的 MID
     */
    @SerializedName("mid")
    private String mid;
    /**
     * 字符串型的评论 ID
     */
    @SerializedName("idstr")
    private String idstr;
    /** 评论的微博信息字段 */
//    @SerializedName("status")
//    private Status status;
    /**
     * 评论来源评论，当本评论属于对另一评论的回复时返回此字段
     */
    @SerializedName("reply_comment")
    private Comment reply_comment;

    public String getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }

    public String getIdstr() {
        return idstr;
    }

    public String getMid() {
        return mid;
    }

    public Comment getReply_comment() {
        return reply_comment;
    }

    public String getSource() {
        return source;
    }

    public String getText() {
        return text;
    }
}
