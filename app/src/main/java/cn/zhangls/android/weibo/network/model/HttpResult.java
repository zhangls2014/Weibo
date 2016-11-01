package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 网络请求父类
 */

public class HttpResult<T> {
    @SerializedName("statuses")
    private List<T> statuses;
    @SerializedName("previous_cursor")
    private String previous_cursor;
    @SerializedName("next_cursor")
    private String next_cursor;
    @SerializedName("total_number")
    private int total_number;

    public String getNext_cursor() {
        return next_cursor;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public List<T> getStatuses() {
        return statuses;
    }

    public int getTotal_number() {
        return total_number;
    }
}
