package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 用户
 */

public class User {
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
    //用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
    @SerializedName("lang")
    private String lang;

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
}
