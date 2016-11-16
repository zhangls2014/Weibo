package cn.zhangls.android.weibo.ui.home.profile;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import cn.zhangls.android.weibo.AccessTokenKeeper;
import cn.zhangls.android.weibo.network.HttpMethods;
import cn.zhangls.android.weibo.network.model.User;
import cn.zhangls.android.weibo.utils.ToastUtil;
import rx.Subscriber;

/**
 * Created by zhangls on 2016/11/16.
 *
 *
 */

class ProfilePresenter implements ProfileContract.Presenter {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 视图对象
     */
    private ProfileContract.View mProfileView;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    /**
     * 用户信息
     */
    private User mUser;

    /**
     * 构造方法
     *
     * @param profileView 视图
     * @param mContext 上下文对象
     */
    ProfilePresenter(ProfileContract.View profileView, Context mContext) {
        this.mContext = mContext;
        mProfileView = profileView;
        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);

        mProfileView.setPresenter(this);
    }

    @Override
    public void start() {}

    @Override
    public void getUser() {
        if (mAccessToken.isSessionValid()) {
            getUser(mAccessToken.getToken(), Long.parseLong(mAccessToken.getUid()));
        }
    }

    /**
     * 获取用户信息
     *
     * @param access_token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param uid 需要查询的用户ID。
     */
    private void getUser(String access_token, long uid) {
        Subscriber<User> subscriber = new Subscriber<User>() {

            @Override
            public void onCompleted() {
                mProfileView.loadData(mUser);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(mContext, "获取用户信息失败");
            }

            @Override
            public void onNext(User user) {
                mUser = user;
            }
        };

        HttpMethods.getInstance().getUser(subscriber, access_token, uid);
    }
}
