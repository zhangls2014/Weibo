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

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import com.google.gson.annotations.SerializedName;

import cn.zhangls.android.weibo.utils.TextUtil;

/**
 * Created by zhangls{github.com/zhangls2014} on 2016/12/30.
 * <p>
 * 评论结构体
 */

public class Comment implements Parcelable {

    /**
     * 评论创建时间
     */
    @SerializedName("created_at")
    private String created_at;
    /**
     * 评论的 ID
     */
    @SerializedName("id")
    private long id;
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
    @SerializedName("user")
    private User user;
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
    @SerializedName("status")
    private Status status;
    /**
     * 评论来源评论，当本评论属于对另一评论的回复时返回此字段
     */
    @SerializedName("reply_comment")
    private Comment reply_comment;

    protected Comment(Parcel in) {
        created_at = in.readString();
        id = in.readLong();
        text = in.readString();
        source = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        mid = in.readString();
        idstr = in.readString();
        status = in.readParcelable(Status.class.getClassLoader());
        reply_comment = in.readParcelable(Comment.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getCreated_at() {
        return created_at;
    }

    public long getId() {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(source).toString();
        }
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public String convertCreatedTime(Context context) {
        return TextUtil.convertCreateTime(context, getCreated_at());
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeLong(id);
        dest.writeString(text);
        dest.writeString(source);
        dest.writeParcelable(user, flags);
        dest.writeString(mid);
        dest.writeString(idstr);
        dest.writeParcelable(status, flags);
        dest.writeParcelable(reply_comment, flags);
    }
}
