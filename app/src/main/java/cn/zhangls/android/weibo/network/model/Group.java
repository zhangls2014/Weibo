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
 * Created by zhangls{github.com/zhangls2014} on 2016/12/10.
 * <p>
 * 好友分组信息
 */

public class Group {

    /**
     * 微博分组ID
     */
    @SerializedName("id")
    private String id;
    /**
     * 微博分组字符串ID
     */
    @SerializedName("idstr")
    private String idStr;
    /**
     * 分组名称
     */
    @SerializedName("name")
    private String name;
    /**
     * 类型（不公开分组等）
     */
    @SerializedName("mode")
    private String mode;
    /**
     * 是否公开
     */
    @SerializedName("visible")
    private int visible;
    /**
     * 喜欢数
     */
    @SerializedName("like_count")
    private int like_count;
    /**
     * 分组成员数
     */
    @SerializedName("member_count")
    private int member_count;
    /**
     * 分组描述
     */
    @SerializedName("description")
    private String description;
    /**
     * 分组的Tag 信息
     */
    @SerializedName("tags")
    private ArrayList<Tag> tags;
    /**
     * 头像信息
     */
    @SerializedName("profile_image_url")
    private String profile_image_url;
    /**
     * 分组所属用户信息
     */
    @SerializedName("user")
    private User user;
    /**
     * 分组创建时间
     */
    @SerializedName("create_time")
    private String createAtTime;

    public String getCreateAtTime() {
        return createAtTime;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getIdStr() {
        return idStr;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getMember_count() {
        return member_count;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public User getUser() {
        return user;
    }

    public int getVisible() {
        return visible;
    }
}
