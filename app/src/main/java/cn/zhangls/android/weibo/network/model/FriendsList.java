package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhangls on 2016/11/15.
 * <p>
 * 用户的关注列表
 */

public class FriendsList {
    /**
     * 用户信息
     */
    @SerializedName("users")
    private List<User> user;
    /**
     * 上一个数据游标
     */
    @SerializedName("previous_cursor")
    private String previous_cursor;
    /**
     * 下一个数据游标
     */
    @SerializedName("next_cursor")
    private String next_cursor;
    /**
     * 数据总数
     */
    @SerializedName("total_number")
    private int total_number;

    public String getNext_cursor() {
        return next_cursor;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public List<User> getUser() {
        return user;
    }
}
