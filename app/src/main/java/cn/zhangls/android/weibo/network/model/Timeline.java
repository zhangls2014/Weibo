package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 公共微博
 */

public class Timeline {
    //微博创建时间
    @SerializedName("created_at")
    private String created_at;
    //微博ID
    @SerializedName("id")
    private long id;
    //微博ID
    @SerializedName("mid")
    private long mid;
    //字符串型的微博ID
    @SerializedName("idstr")
    private String idstr;
    //微博信息内容
    @SerializedName("text")
    private String text;
    //微博来源
    @SerializedName("source")
    private String source;
    //是否已收藏，true：是，false：否
    @SerializedName("favorited")
    private boolean favorited;
    //是否被截断，true：是，false：否
    @SerializedName("truncated")
    private boolean truncated;
    //（暂未支持）回复ID
    @SerializedName("in_reply_to_status_id")
    private String in_reply_to_status_id;
    //（暂未支持）回复人UID
    @SerializedName("in_reply_to_user_id")
    private String in_reply_to_user_id;
    //（暂未支持）回复人昵称
    @SerializedName("in_reply_to_screen_name")
    private String in_reply_to_screen_name;
    //缩略图片地址，没有时不返回此字段
    @SerializedName("thumbnail_pic")
    private String thumbnail_pic;
    //中等尺寸图片地址，没有时不返回此字段
    @SerializedName("bmiddle_pic")
    private String bmiddle_pic;
    //原始图片地址，没有时不返回此字段
    @SerializedName("original_pic")
    private String original_pic;
    //地理信息字段
    @SerializedName("geo")
    private Object geo;
    //微博作者的用户信息字段
    @SerializedName("user")
    private User user;
    //被转发的原微博信息字段，当该微博为转发微博时返回
    @SerializedName("retweeted_status")
    private Object retweeted_status;
    //转发数
    @SerializedName("comments_count")
    private int comments_count;
    //评论数
    @SerializedName("attitudes_count")
    private int attitudes_count;
    //表态数
    @SerializedName("mlevel")
    private int mlevel;
    //暂未支持;
    @SerializedName("reposts_count")
    private int reposts_count;
    //微博的可见性及指定可见分组信息。该object中type取值：
    // 0：普通微博，
    // 1：私密微博，
    // 3：指定分组微博，
    // 4：密友微博；
    // list_id为分组的组号
    @SerializedName("visible")
    private Object visible;
    //微博配图ID。多图时返回多图ID，用来拼接图片url。
    // 用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。
    @SerializedName("pic_ids")
    private Object pic_ids;

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
        return created_at;
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

    public Object getPic_ids() {
        return pic_ids;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public Object getRetweeted_status() {
        return retweeted_status;
    }

    public String getSource() {
        return source;
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


}
