package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangls on 2016/11/8.
 *
 * 多图id
 */

public class PicUrls {
    @SerializedName("thumbnail_pic")
    private String thumbnail_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }
}
