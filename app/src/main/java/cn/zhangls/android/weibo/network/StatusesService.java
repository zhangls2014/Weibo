package cn.zhangls.android.weibo.network;

import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.network.model.Status;
import cn.zhangls.android.weibo.network.model.StatusList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 微博读取请求类
 */

interface StatusesService {

    /**
     * 获取最新的公共微博
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @return Status 实体类
     */
    @GET("/2/statuses/public_timeline.json")
    Observable<StatusList<Status>> getPublicTimeline(
            @Query("access_token") @NonNull String access_token,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app);

    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     *
     * @param access_token 用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count 单页返回的记录条数，最大不超过100，默认为20。
     * @param page 返回结果的页码，默认为1。
     * @param base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trim_user 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     * @return Status 实体类
     */
    @GET("/2/statuses/friends_timeline.json")
    Observable<StatusList<Status>> getFriendsTimeline(
            @Query("access_token") @NonNull String access_token,
            @Query("since_id") long since_id,
            @Query("max_id") long max_id,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app,
            @Query("feature") int feature,
            @Query("trim_user") int trim_user);
}
