package cn.zhangls.android.weibo.network.service;

import cn.zhangls.android.weibo.network.model.FriendsList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangls on 2016/11/15.
 * <p>
 * 用户关系方法
 */

public interface FriendsService {
    /**
     * 获取用户的关注列表
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得
     * @param uid          需要查询的用户UID
     * @param screen_name  需要查询的用户昵称
     * @param count        单页返回的记录条数，默认为50，最大不超过200
     * @param cursor       返回结果的游标，
     *                     下一页用返回值里的next_cursor，
     *                     上一页用previous_cursor，
     *                     默认为0
     * @param trim_status  返回值中user字段中的status字段开关，
     *                     0：返回完整status字段、
     *                     1：status字段仅返回status_id，
     *                     默认为1
     * @return 用户的关注列表
     */
    @GET("/2/friendships/friends.json")
    Observable<FriendsList> getFriendsList(
            @Query("access_token") String access_token,
            @Query("uid") long uid,
            @Query("screen_name") String screen_name,
            @Query("count") int count,
            @Query("cursor") int cursor,
            @Query("trim_status") int trim_status
    );
}
