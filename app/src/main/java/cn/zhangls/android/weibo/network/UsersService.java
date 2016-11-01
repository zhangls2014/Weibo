package cn.zhangls.android.weibo.network;

import android.support.annotation.NonNull;

import cn.zhangls.android.weibo.network.model.User;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangls on 2016/10/30.
 *
 * 用户读取请求类
 */

interface UsersService {

    /**
     * 获取用户信息
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param uid 需要查询的用户ID。
     * @return User 实体类
     */
    @GET("/2/users/show.json")
    Observable<User> getUser(
            @Query("access_token") String access_token,
            @Query("uid") long uid);
}
