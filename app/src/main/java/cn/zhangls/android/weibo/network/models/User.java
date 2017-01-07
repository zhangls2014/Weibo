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

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 用户信息结构体
 */

public class User implements Parcelable {
    //用户UID
    @SerializedName("id")
    private long id;
    //字符串型的用户UID
    @SerializedName("idstr")
    private String idstr;
    //用户昵称
    @SerializedName("screen_name")
    private String screen_name;
    //友好显示名称
    @SerializedName("name")
    private String name;
    //用户所在省级ID
    @SerializedName("province")
    private int province;
    //用户所在城市ID
    @SerializedName("city")
    private int city;
    //用户所在地
    @SerializedName("location")
    private String location;
    //用户个人描述
    @SerializedName("description")
    private String description;
    //用户博客地址
    @SerializedName("url")
    private String url;
    //用户头像地址（中图），50×50像素
    @SerializedName("profile_image_url")
    private String profile_image_url;
    //用户的微博统一URL地址
    @SerializedName("profile_url")
    private String profile_url;
    //用户的个性化域名
    @SerializedName("domain")
    private String domain;
    //用户的微号
    @SerializedName("weihao")
    private String weihao;
    //性别，m：男、f：女、n：未知
    @SerializedName("gender")
    private String gender;
    //粉丝数
    @SerializedName("followers_count")
    private int followers_count;
    //关注数
    @SerializedName("friends_count")
    private int friends_count;
    //微博数
    @SerializedName("statuses_count")
    private int statuses_count;
    //收藏数
    @SerializedName("favourites_count")
    private int favourites_count;
    //用户创建（注册）时间
    @SerializedName("created_at")
    private String created_at;
    //暂未支持
    @SerializedName("following")
    private boolean following;
    //是否允许所有人给我发私信，true：是，false：否
    @SerializedName("allow_all_act_msg")
    private boolean allow_all_act_msg;
    //是否允许标识用户的地理位置，true：是，false：否
    @SerializedName("geo_enabled")
    private boolean geo_enabled;
    //是否是微博认证用户，即加V用户，true：是，false：否
    @SerializedName("verified")
    private String verified;
    //暂未支持
    @SerializedName("verified_type")
    private int verified_type;
    //用户备注信息，只有在查询用户关系时才返回此字段
    @SerializedName("remark")
    private String remark;
    //用户的最近一条微博信息字段
    @SerializedName("status")
    private Object status;
    //是否允许所有人对我的微博进行评论，true：是，false：否
    @SerializedName("allow_all_comment")
    private boolean allow_all_comment;
    //用户头像地址（大图），180×180像素
    @SerializedName("avatar_large")
    private String avatar_large;
    //用户头像地址（高清），高清头像原图
    @SerializedName("avatar_hd")
    private String avatar_hd;
    //认证原因
    @SerializedName("verified_reason")
    private String verified_reason;
    //该用户是否关注当前登录用户，true：是，false：否
    @SerializedName("follow_me")
    private boolean follow_me;
    //用户的在线状态，0：不在线、1：在线
    @SerializedName("online_status")
    private int online_status;
    //用户的互粉数
    @SerializedName("bi_followers_count")
    private int bi_followers_count;
    /**
     * 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
     */
    @SerializedName("lang")
    private String lang;

    protected User(Parcel in) {
        id = in.readLong();
        idstr = in.readString();
        screen_name = in.readString();
        name = in.readString();
        province = in.readInt();
        city = in.readInt();
        location = in.readString();
        description = in.readString();
        url = in.readString();
        profile_image_url = in.readString();
        profile_url = in.readString();
        domain = in.readString();
        weihao = in.readString();
        gender = in.readString();
        followers_count = in.readInt();
        friends_count = in.readInt();
        statuses_count = in.readInt();
        favourites_count = in.readInt();
        created_at = in.readString();
        following = in.readByte() != 0;
        allow_all_act_msg = in.readByte() != 0;
        geo_enabled = in.readByte() != 0;
        verified = in.readString();
        verified_type = in.readInt();
        remark = in.readString();
        allow_all_comment = in.readByte() != 0;
        avatar_large = in.readString();
        avatar_hd = in.readString();
        verified_reason = in.readString();
        follow_me = in.readByte() != 0;
        online_status = in.readInt();
        bi_followers_count = in.readInt();
        lang = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public boolean isAllow_all_act_msg() {
        return allow_all_act_msg;
    }

    public boolean isAllow_all_comment() {
        return allow_all_comment;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public int getBi_followers_count() {
        return bi_followers_count;
    }

    public int getCity() {
        return city;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDescription() {
        return description;
    }

    public String getDomain() {
        return domain;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public boolean isFollowing() {
        return following;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public String getGender() {
        return gender;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public long getId() {
        return id;
    }

    public String getIdstr() {
        return idstr;
    }

    public String getLang() {
        return lang;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getOnline_status() {
        return online_status;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public int getProvince() {
        return province;
    }

    public String getRemark() {
        return remark;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public Object getStatus() {
        return status;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public String getUrl() {
        return url;
    }

    public String getVerified() {
        return verified;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public int getVerified_type() {
        return verified_type;
    }

    public String getWeihao() {
        return weihao;
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
        dest.writeLong(id);
        dest.writeString(idstr);
        dest.writeString(screen_name);
        dest.writeString(name);
        dest.writeInt(province);
        dest.writeInt(city);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(profile_image_url);
        dest.writeString(profile_url);
        dest.writeString(domain);
        dest.writeString(weihao);
        dest.writeString(gender);
        dest.writeInt(followers_count);
        dest.writeInt(friends_count);
        dest.writeInt(statuses_count);
        dest.writeInt(favourites_count);
        dest.writeString(created_at);
        dest.writeByte((byte) (following ? 1 : 0));
        dest.writeByte((byte) (allow_all_act_msg ? 1 : 0));
        dest.writeByte((byte) (geo_enabled ? 1 : 0));
        dest.writeString(verified);
        dest.writeInt(verified_type);
        dest.writeString(remark);
        dest.writeByte((byte) (allow_all_comment ? 1 : 0));
        dest.writeString(avatar_large);
        dest.writeString(avatar_hd);
        dest.writeString(verified_reason);
        dest.writeByte((byte) (follow_me ? 1 : 0));
        dest.writeInt(online_status);
        dest.writeInt(bi_followers_count);
        dest.writeString(lang);
    }
}
