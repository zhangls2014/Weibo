package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls on 2016/11/8.
 *
 * 多图id
 */

public class PicUrls {
    /**
     * 缩略图片地址（小图），没有时不返回此字段
     */
    @SerializedName("thumbnail_pic")
    private String thumbnail_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }
}
