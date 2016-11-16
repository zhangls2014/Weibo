package cn.zhangls.android.weibo.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhangls on 2016/10/30.
 * <p>
 * 网络请求父类
 */

public class StatusList {
    /**
     * Status List
     */
    @SerializedName("statuses")
    private List<Status> statuses;
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

    public List<Status> getStatuses() {
        return statuses;
    }

    public int getTotal_number() {
        return total_number;
    }
}
