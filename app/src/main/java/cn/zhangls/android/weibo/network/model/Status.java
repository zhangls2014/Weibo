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

import android.content.Context;
import android.os.Build;
import android.text.Html;

import com.google.gson.annotations.SerializedName;
import com.sina.weibo.sdk.openapi.models.Geo;
import com.sina.weibo.sdk.openapi.models.Visible;

import java.util.ArrayList;

import cn.zhangls.android.weibo.utils.TextUtil;

/**
 * Created by zhangls on 2016/10/30.
 * <p>
 * 公共微博
 */

public class Status {

    /**
     * 微博ID
     */
    @SerializedName("id")
    public long id;
    /**
     * 微博信息内容
     */
    @SerializedName("text")
    public String text;
    /**
     * 微博来源
     */
    @SerializedName("source")
    public String source;
    /**
     * 微博作者的用户信息字段
     */
    @SerializedName("user")
    public User user;
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 微博创建时间
     */
    @SerializedName("created_at")
    private String created_at;
    /**
     * 微博MID
     */
    @SerializedName("mid")
    private long mid;
    /**
     * 字符串型的微博ID
     */
    @SerializedName("idstr")
    private String idstr;
    /**
     * 是否已收藏，true：是，false：否
     */
    @SerializedName("favorited")
    private boolean favorited;
    /**
     * 是否被截断，true：是，false：否
     */
    @SerializedName("truncated")
    private boolean truncated;
    /**
     * （暂未支持）回复ID
     */
    @SerializedName("in_reply_to_status_id")
    private String in_reply_to_status_id;
    /**
     * （暂未支持）回复人UID
     */
    @SerializedName("in_reply_to_user_id")
    private String in_reply_to_user_id;
    /**
     * （暂未支持）回复人昵称
     */
    @SerializedName("in_reply_to_screen_name")
    private String in_reply_to_screen_name;
    /**
     * 缩略图片地址（小图），没有时不返回此字段
     */
    @SerializedName("thumbnail_pic")
    private String thumbnail_pic;
    /**
     * 中等尺寸图片地址（中图），没有时不返回此字段
     */
    @SerializedName("bmiddle_pic")
    private String bmiddle_pic;
    /**
     * 原始图片地址（原图），没有时不返回此字段
     */
    @SerializedName("original_pic")
    private String original_pic;
    /**
     * 地理信息字段
     */
    @SerializedName("geo")
    private Geo geo;
    /**
     * 被转发的原微博信息字段，当该微博为转发微博时返回
     */
    @SerializedName("retweeted_status")
    private Status retweeted_status;
    /**
     * 转发数
     */
    @SerializedName("reposts_count")
    private int reposts_count;
    /**
     * 评论数
     */
    @SerializedName("comments_count")
    private int comments_count;
    /**
     * 表态数
     */
    @SerializedName("attitudes_count")
    private int attitudes_count;
    /**
     * 暂未支持
     */
    @SerializedName("mlevel")
    private int mlevel;
    /**
     * 微博的可见性及指定可见分组信息。该 object 中 type 取值，
     * 0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；
     * list_id为分组的组号
     */
    @SerializedName("visible")
    private Visible visible;
    /**
     * 微博配图地址。多图时返回多图链接。无配图返回"[]"
     */
    @SerializedName("pic_urls")
    private ArrayList<PicUrls> pic_urls;

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public int getComments_count() {
        return comments_count;
    }

    public String getCreated_at() {
        return TextUtil.convertCreateTime(created_at);
    }

    public boolean isFavorited() {
        return favorited;
    }

    public Object getGeo() {
        return geo;
    }

    public long getId() {
        return id;
    }

    public String getIdstr() {
        return idstr;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public long getMid() {
        return mid;
    }

    public int getMlevel() {
        return mlevel;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public ArrayList<PicUrls> getPic_urls() {
        return pic_urls;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public Status getRetweeted_status() {
        return retweeted_status;
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

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public User getUser() {
        return user;
    }

    public Object getVisible() {
        return visible;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
